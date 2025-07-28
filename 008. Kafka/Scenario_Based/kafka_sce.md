## 1. You have implemented a Spring-Kafka producer, but the records never appear in the topic. What are the most common root causes and how do you verify/fix them?

### **1. Broker connectivity / bootstrap-servers**

- Check the bootstrap-servers value in application.yml matches exactly what the broker advertises (hostname/IP and port).
- From inside the same Docker network / K8s Pod, run nc -vz <broker> 9092 or telnet <broker> 9092.
- Enable DEBUG in logback-spring.xml to see the initial metadata fetch:

```xml
<logger name="org.apache.kafka" level="DEBUG"/>
```

### **2. Topic does not exist and auto-create is off**

- Spring will not create the topic if auto.create.topics.enable=false on the broker.
- Verify topic exists

```bash
kubectl exec -it kafka-0 -- kafka-topics.sh --bootstrap-server localhost:9092 --list
```

- Create it explicitly or let Spring create it

```java
@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name("orders").partitions(3).replicas(1).build();
    }
}
```

### **3. Serializers / incompatible types**

- Sending a UUID key with the default StringSerializer throws an exception (swallowed unless you add an error handler).
- Declare serializers in YAML

```yaml
spring:
  kafka:
    producer:
      key-serializer: org.apache.kafka.common.serialization.UUIDSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
```

- Add a global error handler so nothing is silently dropped

```java
@Component
public class ProducerErrorHandler implements ProducerListener<Object,Object> {
    @Override
    public void onError(ProducerRecord<Object,Object> record, Exception ex) {
        log.error("Send failed for {}", record, ex);
    }
}
```

### **4. Missing or negative acknowledgment (acks)**

- With acks=0 or acks=1 and a single in-sync replica down, the message may be lost without an exception.
- Use idempotent/transactional defaults

```yaml
spring:
  kafka:
    producer:
      acks: all
      retries: 5
      properties:
        enable.idempotence: true
```

### **5. Network / firewall / TLS / mTLS**

- Typical in EKS with AWS MSK:
- Security-group rule must allow egress 9094 (TLS) or 9092 (PLAINTEXT).
- If TLS/mTLS is enabled on MSK, configure:

```yaml
spring:
  kafka:
    properties:
      security.protocol: SSL
      ssl.truststore.location: file:/certs/kafka.truststore.jks
      ssl.truststore.password: ${TRUSTSTORE_PASSWORD}
```

### **6.Message too large**

- Default max.request.size is 1 MB. A bigger DTO is silently dropped
- Increase at the producer

```yaml
spring:
  kafka:
    producer:
      properties:
        max.request.size: 5242880 # 5 MB
```

### **7. Producer not actually flushing / closing**

- When the JVM dies before the internal batch is sent, messages are lost.
- Either call kafkaTemplate.flush() or rely on the graceful shutdown hook (default in Spring Boot 3).

```java
@DynamicPropertySource
static void kafkaProps(DynamicPropertyRegistry r) {
    r.add("spring.kafka.producer.close-timeout", () -> "5s");
}
```

### **8. Transactional producer misconfiguration**

- If you start a @Transactional method but the producer is not transactional, the template will spin forever.
- Enable transactions

```yaml
spring:
  kafka:
    producer:
      transaction-id-prefix: tx-producer-
```

### **9. Partition assignment / key hash**

- When the key is null and DefaultPartitioner is used, all messages go to a single partition. If that partition’s leader is offline, nothing is produced.
- Verify leadership

```bash
kubectl exec -it kafka-0 -- kafka-topics.sh \
  --describe --bootstrap-server localhost:9092 --topic orders
```

### **10. Quick end-to-end smoke test**

- From inside the same K8s pod

```java
@SpringBootTest
@Testcontainers
class KafkaProducerIT {
    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.0"));

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry r) {
        r.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired KafkaTemplate<String,String> template;

    @Test
    void shouldSendAndReceive() {
        template.send("smoke", "ping").get(10, TimeUnit.SECONDS);
        assertThat(kafka).hasRecords("smoke", 1);
    }
}
```

---

## 2. Your Spring-Boot consumer receives the Kafka record, but nothing is persisted to the database. How do you systematically troubleshoot?

### **1. Verify the consumer is actually polling records**

- Enable DEBUG for the Kafka client and Spring listener container

```yaml
logging:
  level:
    org.apache.kafka: DEBUG
    org.springframework.kafka: DEBUG
```

Look for lines like:

```bash
o.s.k.l.KafkaMessageListenerContainer : Received: 1 records
```

- If you do not see these lines, jump to “Consumer configuration”

### **2. Consumer configuration checklist**

- Typical mistakes in application.yml

```yaml
spring:
  kafka:
    consumer:
      group-id: order-processor
      auto-offset-reset: earliest # or 'latest' depending on use-case
      enable-auto-commit: false # we will commit manually after DB success
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: "com.acme.dto"
```

- If group-id is new and auto-offset-reset=latest, you will never get old messages.
- If enable-auto-commit=true, offsets may be committed before the DB transaction, leading to silent data loss on crash.

### **3. Listener method & container visibility**

- Make sure the method is discovered by Spring

```java
@Component
class OrderListener {
    @KafkaListener(id = "orderListener",
                   topics = "orders",
                   containerFactory = "kafkaManualAckContainerFactory")
    public void listen(OrderDto dto, Acknowledgment ack) {
        log.info("Received {}", dto);
        orderService.save(dto);   // DB call
        ack.acknowledge();
    }
}
```

- If you accidentally place the @KafkaListener on a package-private class or forget @Component, the container never starts.

### **4. Check the in-JVM exception path**

- Add a SeekToCurrentErrorHandler so poison pills don’t stall the partition

```java
@Bean
public ConcurrentKafkaListenerContainerFactory<?,?> kafkaManualAckContainerFactory(
        ConsumerFactory<Object,Object> cf) {

    var factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(cf);
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

    var errorHandler = new DefaultErrorHandler(
            new FixedBackOff(2000L, 3)); // 3 retries every 2 s
    factory.setCommonErrorHandler(errorHandler);

    return factory;
}
```

- With DEBUG logging you will see:

```bash
ERROR o.s.k.l.DefaultErrorHandler : Record failed after 3 attempts, skipping...
```

### **5. Database layer verification**

#### **5.1 Connection & credentials**

- From inside the same K8s pod

```bash
kubectl exec -it order-service-xxx -- \
  java -cp /app/libs/hikari-*.jar com.zaxxer.hikari.util.HikariConfigDebugger
```

- If the DB health check is DOWN, fix the spring.datasource.\* properties.

#### **5.2 Transaction boundaries**

- If you use @Transactional on OrderService.save, make sure the transaction manager is Kafka-and-DB chained

```java
@Transactional("kafkaTransactionManager")   // ChainedKafkaTransactionManager
public void save(OrderDto dto) {
    repo.save(map(dto));
}
```

- Without the chained TM, the DB commit could succeed while Kafka offset commit fails, or vice-versa.

#### **5.3 SQL & constraint violations**

- Enable SQL logging

```yaml
spring:
  jpa:
    show-sql: true
logging:
  level:
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
```

- Look in the logs for:

```bash
Duplicate entry '123' for key 'order.uk_order_id'
```

### **6. Network & secrets (K8s / AWS)**

- Typical RDS + EKS issues
- ServiceAccount lacks IRSA annotation → cannot fetch IAM token.
- Secret mounted at /etc/db-secret/password but spring.datasource.password points to a non-existent key.

```yaml
env:
  - name: SPRING_DATASOURCE_PASSWORD
    valueFrom:
      secretKeyRef:
        name: db-secret
        key: password
```

- Check from inside the pod

```bash
echo $SPRING_DATASOURCE_PASSWORD
```

### **7. Integration test to reproduce**

- Use the embedded broker pattern

```java
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Testcontainers
class OrderListenerIT {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15");

    @Container
    static KafkaContainer kafka =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.0"));

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry r) {
        r.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        r.add("spring.datasource.url", postgres::getJdbcUrl);
        r.add("spring.datasource.username", postgres::getUsername);
        r.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired KafkaTemplate<String,OrderDto> template;
    @Autowired OrderRepository repo;

    @Test
    void shouldPersistOrder() throws Exception {
        OrderDto dto = new OrderDto("ORD-1", BigDecimal.TEN);
        template.send("orders", dto.getId(), dto).get();
        await().atMost(10, TimeUnit.SECONDS)
               .untilAsserted(() -> assertThat(repo.findById("ORD-1")).isPresent());
    }
}
```

- If this test passes but production fails, the issue is environmental (network, secrets, IAM).
- If the test also fails, the bug is in code or SQL.

---

## 3. How do I introduce a configurable delay between “poll” and “process” in a Spring-Boot 3 / Java 21 Kafka consumer without blocking the listener thread?

### **1. Quick but naïve (works for dev only)**

- Sleep inside the listener; not recommended for prod because it keeps the partition thread busy and can trigger a rebalance.

```java
@KafkaListener(topics = "orders")
public void listen(OrderDto dto) throws InterruptedException {
    Thread.sleep(Duration.ofSeconds(5));   // blocks!
    orderService.save(dto);
}
```

### **2. Scalable pattern — Async hand-off + delayed executor**

- Poll immediately, enqueue the record, and let a ScheduledExecutorService or Spring’s TaskScheduler run the real work later.
- This (ScheduledExecutorService with manual ack) gives the best balance between simplicity and scalability.

#### **2.1. Configuration**

```java
@Configuration
class DelayConfig {

    @Bean
    public ScheduledExecutorService delayedExecutor() {
        // virtual threads → 1 scheduler per core
        return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(),
                                                Thread.ofVirtual().factory());
    }
}
```

#### **2.2 Listener (manual ack to avoid commit before processing)**

```java
@Component
class DelayedConsumer {

    private final ScheduledExecutorService delayedExecutor;
    private final OrderService orderService;

    @KafkaListener(topics = "orders", containerFactory = "manualAckFactory")
    public void listen(OrderDto dto, Acknowledgment ack) {
        delayedExecutor.schedule(
            () -> {
                try {
                    orderService.save(dto);
                } finally {
                    ack.acknowledge();   // commit offset after the delay
                }
            },
            5, TimeUnit.SECONDS);        // configurable via @Value
    }
}
```

- Container factory

```java
@Bean
public ConcurrentKafkaListenerContainerFactory<String,OrderDto> manualAckFactory(
        ConsumerFactory<String,OrderDto> cf) {
    var factory = new ConcurrentKafkaListenerContainerFactory<String,OrderDto>();
    factory.setConsumerFactory(cf);
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
    return factory;
}
```

### **3. Spring-native alternative — TaskScheduler + @Async**

```java
@Component
class DelayedConsumer {

    private final TaskScheduler scheduler = new ConcurrentTaskScheduler(
            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()));

    @KafkaListener(topics = "orders", containerFactory = "manualAckFactory")
    public void listen(OrderDto dto, Acknowledgment ack) {
        scheduler.schedule(
            () -> {
                orderService.save(dto);
                ack.acknowledge();
            },
            Instant.now().plusSeconds(5));
    }
}
```

### **4. Push to an in-memory delayed queue (Zero blocking)**

- For heavy loads, decouple completely:
- Listener puts the record into a DelayQueue.
- A separate @Scheduled(fixedDelay = 100) method drains the queue and processes items whose delay has expired.

```java
record DelayedRecord(OrderDto dto, long fireAfterNanos) implements Delayed {
    public long getDelay(TimeUnit unit) {
        return unit.convert(fireAfterNanos - System.nanoTime(), TimeUnit.NANOSECONDS);
    }
    public int compareTo(Delayed o) {
        return Long.compare(getDelay(TimeUnit.NANOSECONDS), o.getDelay(TimeUnit.NANOSECONDS));
    }
}

@Component
class DelayedQueueWorker {

    private final DelayQueue<DelayedRecord> queue = new DelayQueue<>();

    @KafkaListener(topics = "orders")
    public void enqueue(OrderDto dto) {
        queue.put(new DelayedRecord(dto, System.nanoTime() + Duration.ofSeconds(5).toNanos()));
    }

    @Scheduled(fixedDelay = 100)
    public void drain() {
        DelayedRecord r;
        while ((r = queue.poll()) != null) {
            orderService.save(r.dto());
        }
    }
}
```

### **5. Cloud-ready variant — external delayed topic**

- If the delay must survive pod restarts, publish the original message to a retry topic with a TTL (e.g., AWS MSK + SQS DLQ) or use Kafka Streams’ punctuate logic.

### **6. Configuration snippet (application.yml)**

```yaml
app:
  kafka:
    processing-delay: 5s # centralised delay
```

- Use it in the scheduled call

```java
@Value("${app.kafka.processing-delay}")
private Duration delay;
```

---

## 4. Your Spring-Boot consumers run clustered (many pods). How do you guarantee exactly-once semantics end-to-end (no duplicates, no loss) and what are the minimal, concrete changes in application.yml and code?

### **1. Exactly-once in Kafka – what it really means**

- Kafka provides at-least-once delivery semantics by default.
- This means a message is delivered at least once but may be delivered more than once (duplicates can occur).
- If a consumer crashes before committing an offset, the same message may be reprocessed after restart.
- To achieve exactly-once semantics (EOS), we need to configure both Kafka and Spring Boot correctly.

### **2. Steps to Implement Exactly-Once Processing**

#### **2.1 Kafka Producer Configuration**

- Enable idempotence to avoid duplicate records

```yaml
spring:
  kafka:
    producer:
      transaction-id-prefix: tx-producer- # enables transactions & idempotence
      acks: all # default when idempotence is on
      retries: 5 # default when idempotence is on
      properties:
        enable.idempotence: true # redudant but explicit
```

- This ensures that retries by the producer do not result in duplicate messages on the Kafka topic.

#### **2.2 Kafka Consumer Configuration**

- Use manual offset management and commit offsets only after successful processing

```yaml
spring.kafka.consumer.enable-auto-commit=false
spring.kafka.listener.ack-mode=MANUAL
```

- No manual ack

```yaml
spring:
  kafka:
    consumer:
      group-id: order-processor
      enable-auto-commit: false # mandatory for transactions
      isolation-level: read_committed
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer

    listener:
      ack-mode: RECORD # or BATCH, but NOT MANUAL
```

### **3. Container factory – enable exactly-once**

#### **3.1 Manual Ack**

- Transactions guarantee atomicity between:
- Consuming a record
- Processing the message
- Producing results (or committing offsets)
- This prevents committing offsets for unprocessed messages:

```java
@KafkaListener(topics = "orders", groupId = "order-group")
@Transactional("kafkaTransactionManager")
public void consume(String message) {
    processMessage(message);      // Business logic
    // Offsets are committed as part of the same Kafka transaction
}
```

#### **3.2 No Manual Ack**

```java
@Configuration
class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?,?> kafkaListenerContainerFactory(
            ConsumerFactory<Object,Object> cf,
            KafkaTransactionManager<?,?> kafkaTm,
            JpaTransactionManager jpaTm) {

        var factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);

        // one chained TX manager: offsets + DB
        var chained = new ChainedKafkaTransactionManager<>(kafkaTm, jpaTm);
        factory.setTransactionManager(chained);

        // EOS mode: pause/resume on rebalance, no manual ack
        factory.setContainerCustomizer(c -> c.setEOSMode(ContainerProperties.EOSMode.V2));
        return factory;
    }
}
```

### **4. Listener method – just annotate with Spring TX**

```java
@Component
class OrderListener {

    private final OrderRepository repo;

    @Transactional("kafkaTransactionManager")   // same name as chained bean
    @KafkaListener(topics = "orders")
    public void listen(OrderDto dto) {
        repo.save(map(dto));     // DB operation
        // offset commit happens automatically in the same transaction
    }
}
```

- If repo.save throws any RuntimeException the entire transaction rolls back:
- DB row is not persisted.
- Consumer offset is not committed.
- Next re-balance will re-deliver the same record.

### **5. Clustered Environment Coordination**

- Kafka ensures each partition is consumed by only one consumer in a consumer group.
- With EOS enabled, messages from a partition will be processed exactly once per consumer group.
- Use sticky partition assignment to reduce rebalances

```bash
spring.kafka.consumer.properties.partition.assignment.strategy=org.apache.kafka.clients.consumer.StickyAssignor
```

### **1. Pods / instances → All use same group-id → partitions are load-balanced**

- In Kafka, a consumer group ensures that each partition is consumed by only one instance at a time.
- If you deploy multiple pods (Spring Boot Kafka consumers) with the same group-id, Kafka will split the topic partitions among them.
- This avoids duplicate processing and distributes the workload.
- If each pod had a different group-id, every pod would get all messages → duplicate work.

### **2. transaction-id-prefix → Unique per instance: Kubernetes appends pod name via env var**

- Kafka transactions require a unique transaction ID per producer instance.
- In Kubernetes, multiple pods run the same code. If they all use the same transaction-id-prefix, Kafka will see them as the same producer → conflicts, fencing errors, or aborted transactions.
- Solution: Generate a unique prefix for each pod.

```yaml
spring.kafka.producer.transaction-id-prefix: tx-${HOSTNAME}-
```

- HOSTNAME is a Kubernetes environment variable that contains the pod name → ensures each pod gets its own transaction ID.

### **3. Retries → Let the container retry (DLQ after max attempts) instead of naked @Retryable**

- If processing fails, you should not use just @Retryable in the method because:
- It retries on the same thread, and if it still fails, Kafka may commit offsets incorrectly.
- Instead let the KafkaListenerContainer manage retries.
- Use Spring Kafka error handlers (e.g., SeekToCurrentErrorHandler) that can retry a configurable number of times.
- After maximum retries → the failed message is sent to a Dead Letter Queue (DLQ).

### **4. Exactly-once to downstream → If you produce to another topic, use the same transaction**

- If your consumer reads from one Kafka topic and then produces messages to another topic (or DB), you need to do it inside one Kafka transaction:
- Consume message → process → produce result → commit offset → all as one atomic unit.
- This ensures exactly-once semantics
- If the consumer crashes, neither the message offset nor the produced message will be committed.
- When restarted, Kafka will redeliver the original message → no data loss, no duplicates.

```java
@KafkaListener(topics = "input-topic", groupId = "group1")
@Transactional("kafkaTransactionManager")
public void consumeAndForward(String message) {
    String processed = process(message);
    kafkaTemplate.send("output-topic", processed); // part of same transaction
    // Offset commit is also transactional
}
```

---

## 5. you need to implemennt a Springboot Kafka Producer that needs to send messages to multiple topics based on some dynamic criteria. Your producer must decide at runtime which topic a record belongs to?

### **1. One KafkaTemplate, many destinations**

- We keep a single template and compute the topic string just before send.

### **2. Define Topics as Variables in application.yml**

```yaml
app:
  kafka:
    topics:
      order: order-topic
      payment: payment-topic
      default: default-topic
```

### **3. Use a Map Binding for Flexible Access**

- Routing strategy

```java
@Component
public class TopicRouter {

    private final Map<EventType, String> map;

    public TopicRouter(@Value("${app.kafka.topics}") Map<String, String> cfg) {
        this.map = cfg.entrySet()
                      .stream()
                      .collect(Collectors.toMap(
                               e -> EventType.valueOf(e.getKey()),
                               Map.Entry::getValue));
    }

    public String topicFor(EventType type) {
        return map.getOrDefault(type, "fallback.events");
    }
}
```

- Sender Service

```java
@Service
public class EventPublisher {

    private final KafkaTemplate<String, Object> kafka;
    private final TopicRouter router;

    public EventPublisher(KafkaTemplate<String, Object> kafka, TopicRouter router) {
        this.kafka = kafka;
        this.router = router;
    }

    public CompletableFuture<SendResult<String, Object>> publish(Event event) {
        String topic = router.topicFor(event.type());
        ProducerRecord<String, Object> record =
                new ProducerRecord<>(topic, event.key(), event.body());
        return kafka.send(record).completable();
    }
}
```

- Java record for contract (immutable)

```java
public record Event(String key, EventType type, Object body) { }

public enum EventType {
    ORDER_CREATED, ORDER_SHIPPED, PAYMENT_SUCCESS
}
```

- Unit test with embedded broker

```java
@SpringBootTest
@EmbeddedKafka(partitions = 1,
               topics = {"orders.created", "orders.shipped", "payments.success"})
class DynamicTopicIT {

    @Autowired KafkaTemplate<String, Object> kafka;
    @Autowired EventPublisher publisher;
    @Autowired EmbeddedKafkaBroker broker;

    @Test
    void shouldRouteToCorrectTopic() throws Exception {
        Event e = new Event("123", EventType.ORDER_SHIPPED, Map.of("id", 123));
        publisher.publish(e).get(5, TimeUnit.SECONDS);

        ConsumerRecord<String, Object> rec = KafkaTestUtils.getSingleRecord(
                new DefaultKafkaConsumerFactory<>(KafkaTestUtils.consumerProps("test", "true", broker)),
                "orders.shipped");
        assertThat(rec.value()).isEqualTo(e.body());
    }
}
```

### **4. If you need per-topic producer configs (different acks, SSL, …) use Spring’s RoutingKafkaTemplate**

```java
@Bean
public RoutingKafkaTemplate routingTemplate(Map<String, Object> commonProps) {
    Map<String, ProducerFactory<Object,Object>> map = Map.of(
        "orders.created",  new DefaultKafkaProducerFactory<>(commonProps, new StringSerializer(), new JsonSerializer<>()),
        "orders.shipped",  new DefaultKafkaProducerFactory<>(commonProps, new StringSerializer(), new JsonSerializer<>())
    );
    return new RoutingKafkaTemplate(map);
}
```

### **5. Kafka Streams dynamic routing (TopicNameExtractor)**

- When you build a Streams topology, route inside the topology instead of producing from outside.

### **5.1Streams topology**

```java
@Bean
public KStream<String, Event> topology(StreamsBuilder builder) {
    KStream<String, Event> source = builder.stream("events.in",
                                                   Consumed.with(Serdes.String(), new JsonSerde<>(Event.class)));

    TopicNameExtractor<String, Event> extractor = (key, value, ctx) ->
            "events." + value.type().name().toLowerCase();

    source.to(extractor);   // topic decided per record
    return source;
}
```

- Topics must exist or be auto-created (auto.create.topics.enable=true).

### **5.2 Fine-grained branching**

```java
source.split()
      .branch((k, v) -> v.type() == EventType.ORDER_CREATED,
              Branched.withConsumer(ks -> ks.to("orders.created")))
      .branch((k, v) -> v.type() == EventType.PAYMENT_SUCCESS,
              Branched.withConsumer(ks -> ks.to("payments.success")))
      .noDefaultBranch();
```

### **6. Consumer-side dynamic routing (subscribing at runtime)**

#### **6.1 Adding topics on-the-fly to an existing listener**

```java
@Component
public class DynamicListener {

    @Autowired KafkaListenerEndpointRegistry registry;

    public void addTopic(String topic) {
        var factory = (ConcurrentKafkaListenerContainerFactory<?,?>) registry.getListenerContainer("dynamicGroup").getContainerProperties().getConsumerFactory();
        registry.getListenerContainer("dynamicGroup").addTopics(topic);
    }
}
```

#### **6.2 Programmatic listener container (no @KafkaListener)**

```java
@Bean
public ConcurrentMessageListenerContainer<String, Object> dynamicConsumer(ConsumerFactory<String,Object> cf) {
    var props = new ContainerProperties(Pattern.compile("orders\\..*")); // regex
    props.setMessageListener((MessageListener<String,Object>) record -> process(record));
    return new ConcurrentMessageListenerContainer<>(cf, props);
}
```

---

### Solution 2:

- To send messages to different topics based on dynamic criteria
- Inject KafkaTemplate
- Implement routing logic
- Handle message sending with dynamic topics

- **Basic Configuration**

```java
@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
```

- **Messgae Routing Service**

```java
@Service
public class DynamicTopicProducerService {

    private static final Logger log = LoggerFactory.getLogger(DynamicTopicProducerService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendToDynamicTopic(MessageDTO message) {
        String topic = determineTopic(message);
        ListenableFuture<SendResult<String, String>> future =
            kafkaTemplate.send(topic, message.getKey(), message.getContent());

        future.addCallback(
            result -> handleSuccess(message, topic, result),
            ex -> handleFailure(message, topic, ex)
        );
    }

    private String determineTopic(MessageDTO message) {
        // Implement your dynamic routing logic here
        switch(message.getType()) {
            case "ORDER":
                return message.isPriority() ? "priority-orders" : "normal-orders";
            case "PAYMENT":
                return "payments";
            case "NOTIFICATION":
                return "notifications";
            default:
                return "dead-letter";
        }
    }

    private void handleSuccess(MessageDTO message, String topic, SendResult<String, String> result) {
        log.info("Sent message=[{}] to topic=[{}] with offset=[{}]",
            message.getContent(),
            topic,
            result.getRecordMetadata().offset());
    }

    private void handleFailure(MessageDTO message, String topic, Throwable ex) {
        log.error("Unable to send message=[{}] to topic=[{}] due to: {}",
            message.getContent(),
            topic,
            ex.getMessage());
        // Implement retry or dead-letter queue logic here
    }
}

# DTO Class
public class MessageDTO {
    private String type;
    private String key;
    private String content;
    private boolean priority;

    // Constructors, getters and setters
}
```

- **Topic Creation Verification**

```java
@Autowired
private KafkaAdmin kafkaAdmin;

private void validateTopicExists(String topicName) {
    try {
        Map<String, Object> configs = kafkaAdmin.getConfigurationProperties();
        AdminClient adminClient = AdminClient.create(configs);
        boolean exists = adminClient.listTopics().names().get().contains(topicName);
        if (!exists) {
            log.warn("Topic {} does not exist, creating it", topicName);
            adminClient.createTopics(Collections.singleton(new NewTopic(topicName, 3, (short) 1)));
        }
    } catch (Exception e) {
        log.error("Error verifying/creating topic", e);
    }
}
```

- **Using KafkaHeaders for Routing**

```java
public void sendWithHeaders(MessageDTO message) {
    String topic = determineTopic(message);

    Message<String> kafkaMessage = MessageBuilder
        .withPayload(message.getContent())
        .setHeader(KafkaHeaders.TOPIC, topic)
        .setHeader(KafkaHeaders.KEY, message.getKey())
        .setHeader("X-Custom-Header", message.getCustomProperty())
        .build();

    kafkaTemplate.send(kafkaMessage);
}
```

- **Transactional support**

```yaml
# application.yml
spring:
  kafka:
    producer:
      transaction-id-prefix: tx-
```

```java
@Transactional
public void sendTransactional(MessageDTO message) {
    String topic1 = determinePrimaryTopic(message);
    String topic2 = determineSecondaryTopic(message);

    kafkaTemplate.send(topic1, message.getKey(), message.getContent());
    kafkaTemplate.send(topic2, message.getKey(), message.getContent());
    // Both sends will commit or rollback together
}
```

- Topic Naming Convention: {domain}.{entity}.{eventType}

## 6. How do we guarantee zero message loss when a Kafka broker (or the entire cluster) disappears. How will you ensure that message are not lost in case of a kafka broker failure.?

### **1. Producer-Side Configuration**

```yaml
spring:
  kafka:
    producer:
      bootstrap-servers: kafka1:9092,kafka2:9092,kafka3:9092
      acks: all # Wait for all in-sync replicas to acknowledge
      retries: 5 # Number of retries for transient failures
      properties:
        enable.idempotence: true # Prevent duplicate messages
        max.in.flight.requests.per.connection: 1 # Ensure ordering
        delivery.timeout.ms: 120000 # 2 minutes
        request.timeout.ms: 30000 # 30 seconds
```

- Producer Implementation

```java
@Service
public class ReliableKafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendDurableMessage(String topic, String key, String message) {
        kafkaTemplate.executeInTransaction(t -> {
            try {
                ListenableFuture<SendResult<String, String>> future =
                    t.send(topic, key, message);

                return future.addCallback(
                    result -> log.info("Sent durable message to {}: {}",
                        topic, result.getRecordMetadata()),
                    ex -> {
                        log.error("Failed to send message", ex);
                        throw new RuntimeException("Send failed", ex);
                    }
                );
            } catch (Exception e) {
                log.error("Transaction failed", e);
                throw e;
            }
        });
    }
}
```

### **2. Broker-side durability**

- Replication factor ≥ 3 per topic

```bash
kafka-topics.sh --create --topic orders --replication-factor 3 --partitions 6 ...
```

- unclean.leader.election=false (never elect an out-of-sync replica).
- log.flush.interval.messages=10000 or log.flush.interval.ms=1000 for sync-to-disk frequency.

### **3. Consumer-side durability (no data loss on crash)**

```yaml
spring:
  kafka:
    consumer:
      bootstrap-servers: kafka1:9092,kafka2:9092,kafka3:9092
      enable-auto-commit: false # Manual offset commits
      auto-offset-reset: earliest # Start from beginning if no offset
      group-id: durable-consumer-group
      properties:
        isolation.level: read_committed # Only read committed messages
    listener:
      ack-mode: RECORD
```

- Consumer Implementation

```java
@Service
public class ReliableKafkaConsumer {

    @KafkaListener(topics = "${app.kafka.topic}")
    public void consume(ConsumerRecord<String, String> record,
                       Acknowledgment acknowledgment) {
        try {
            // Process message
            processMessage(record.value());

            // Manual commit only after successful processing
            acknowledgment.acknowledge();
            log.info("Processed and committed offset: {}", record.offset());
        } catch (Exception e) {
            log.error("Failed to process message, will retry", e);
            // Offset won't be committed - will be reprocessed
            throw e;
        }
    }

    @RetryableTopic(
        attempts = "3",
        backoff = @Backoff(delay = 1000, multiplier = 2.0),
        dltTopicSuffix = "-dlt")
    @KafkaListener(topics = "${app.kafka.topic}")
    public void processWithRetry(ConsumerRecord<String, String> record) {
        // Your processing logic
    }
}
```

- Kafka Topic Configuration

```bash
# Create topics with high replication factor
kafka-topics --create \
  --topic durable-topic \
  --partitions 3 \
  --replication-factor 3 \
  --config min.insync.replicas=2 \
  --bootstrap-server kafka:9092

```

- Monitoring and Alerts

```java
@Bean
public MicrometerProducerListener<String, String> producerListener(MeterRegistry registry) {
    return new MicrometerProducerListener<>(registry);
}

@Bean
public MicrometerConsumerListener<String, String> consumerListener(MeterRegistry registry) {
    return new MicrometerConsumerListener<>(registry);
}
```

- Dead Letter Queue Set up

```java
@Bean
public DeadLetterPublishingRecoverer dlqRecoverer(KafkaTemplate<String, String> template) {
    return new DeadLetterPublishingRecoverer(template,
        (record, ex) -> new TopicPartition(record.topic() + "-dlt", -1));
}

@Bean
public DefaultErrorHandler errorHandler(DeadLetterPublishingRecoverer recoverer) {
    return new DefaultErrorHandler(recoverer,
        new FixedBackOff(1000L, 3L));  // 3 retries with 1s interval
}
```

### **4. Best Practices for Maximum Durability**

- **Cluster Configuration:**
- Minimum 3 brokers in production
- Set unclean.leader.election.enable=false on brokers
- Configure log.flush.interval.messages and log.flush.interval.ms appropriately

- **Producer Best Practices:**
- Use synchronous sends for critical messages
- Implement callback handlers for all sends
- Monitor producer metrics (record-error-rate, request-latency)

- **Consumer Best Practices:**
- Set appropriate session.timeout.ms and heartbeat.interval.ms
- Implement proper error handling and retry logic
- Monitor consumer lag and offset commits

- **Disaster Recovery:**
- Set up mirroring to another cluster
- Regular backups of critical topics
- Test failover procedures regularly

## 7. Your Spring-Boot consumer is lagging. What concrete steps (config, code, infra) do you take to scale out & speed up processing? kafka consumer with a high volume of messages. The consumer is falling behind in processing the messages and you need to scale the consumer application to handle the load?

- To address a Kafka consumer lagging behind due to high message volume, you can scale the consumer application by leveraging Kafka’s partitioning, increasing consumer instances, enabling concurrency within consumers, and optimizing message processing with batching.

- Rule of thumb: target partitions = expected peak pods × 3.

| Step                   | Command / Config                                         |
| ---------------------- | -------------------------------------------------------- |
| Re-partition the topic | `kafka-topics.sh --alter --topic orders --partitions 18` |
| Scale the Deployment   | `kubectl scale deploy order-service --replicas=6`        |

### **1. Horizontal scale — the golden rule**

- Kafka’s unit of parallelism is the partition.
- Add more partitions → add more pods.
- **Increase Consumer Instances**
- Kafka’s consumer group mechanism allows multiple consumers in the same group to process different partitions in parallel.
- To scale horizontally, deploy additional instances of your Spring Boot application (e.g., by increasing the number of pods in Kubernetes).
- Each consumer instance will be assigned a subset of the topic’s partitions, distributing the load ensure the number of consumer instances does not exceed the number of partitions, as extra consumers will remain idle.
- **Increase Topic Partitions**
- The number of partitions in a Kafka topic determines the maximum parallelism for consumers in a consumer group, as each partition is consumed by only one consumer at a time.
- If the topic has too few partitions, increase the partition count to allow more consumer instances to process messages in parallel. Note that increasing partitions requires careful consideration, as it cannot be undone without data migration, and it may affect message ordering if keys are used.
- **Enable Concurrency in @KafkaListener**
- Use the concurrency property in the @KafkaListener annotation or configure the ConcurrentKafkaListenerContainerFactory to create multiple listener threads within a single consumer instance.
  -This allows a single consumer to process messages from multiple partitions concurrently, improving throughput without deploying additional instances.
  -Alternatively, enable Spring’s @EnableAsync to process messages asynchronously.

- **Concurrent Consumer Configuration**
```java
@Configuration
@EnableAsync
public class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> 
      kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
        
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(5);  # 5 threads per instance
        factory.getContainerProperties().setPollTimeout(3000);
        factory.setBatchListener(true);  # Enable batch processing
        return factory;
    }
}
```
- **Batch Processing Implementation**
```java
@Service
public class HighVolumeConsumer {

    @KafkaListener(topics = "high-volume-topic", 
                  containerFactory = "kafkaListenerContainerFactory")
    public void processBatch(List<ConsumerRecord<String, String>> records) {
        // Bulk database insert example
        jdbcTemplate.batchUpdate(
            "INSERT INTO messages(id, content) VALUES(?, ?)",
            new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, records.get(i).key());
                    ps.setString(2, records.get(i).value());
                }
                public int getBatchSize() {
                    return records.size();
                }
            });
    }
}
```
- **Dynamic Scaling Controller**
```java
@Scheduled(fixedRate = 30000)  # Every 30 seconds
public void checkConsumerLag() {
    Map<TopicPartition, Long> lags = kafkaConsumerLagChecker.getLag();
    long totalLag = lags.values().stream().mapToLong(Long::longValue).sum();
    
    if (totalLag > 100000) {
        scalingService.scaleOut();
    } else if (totalLag < 10000) {
        scalingService.scaleIn();
    }
}
```

- **Performance Optimization**
```java
@Bean
public ConsumerFactory<String, String> consumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1048576);  # 1MB
    props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 500);    # 500ms
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
    return new DefaultKafkaConsumerFactory<>(props);
}
```

- **Partition Assignment Strategy**
```yaml
spring:
  kafka:
    consumer:
      properties:
        partition.assignment.strategy: org.apache.kafka.clients.consumer.RoundRobinAssignor
```
- **Monitoring and Metrics**
```java
@Bean
public MicrometerConsumerListener<String, String> consumerMetrics(MeterRegistry registry) {
    MicrometerConsumerListener<String, String> listener = 
        new MicrometerConsumerListener<>(registry);
    listener.setConsumerTimersEnabled(true);
    return listener;
}
```
- **Key Metrics to Monitor**
- Consumer lag (records behind)
- Poll rate and duration
- Batch processing time
- Error rates

#### Best Practices
- **Consumer Group Design**
- Keep consumers in the same group homogeneous
- Maintain 1:1 ratio between partitions and consumers for maximum throughput
- **Error Handling**
```java
@Bean
public DefaultErrorHandler errorHandler() {
    return new DefaultErrorHandler(
        (record, exception) -> {
            // Dead letter queue logic
        },
        new FixedBackOff(1000, 3)  # 3 retries with 1s interval
    );
}
```
- Resource Allocation:
- Ensure adequate CPU/Memory for each consumer instance
- Configure JVM for large heaps if processing large batches
- Consider SSD storage for stateful consumers


### **2. Use virtual threads (Java 21) to raise per-pod concurrency**

- Scaling Kafka Consumers with Java 21 Virtual Threads
- Virtual Threads Configuration
```java
@Configuration
public class KafkaVirtualThreadConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> 
        kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
        
        var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(consumerFactory);
        
        // Optimal concurrency setup
        int concurrency = Runtime.getRuntime().availableProcessors() * 2;
        factory.setConcurrency(concurrency);
        
        // Virtual threads configuration
        factory.setContainerCustomizer(container -> {
            container.setThreadFactory(Thread.ofVirtual().name("kafka-vthread-", 0).factory());
            container.getContainerProperties().setPollTimeout(Duration.ofSeconds(3));
        });
        
        // Enable batch processing
        factory.setBatchListener(true);
        
        return factory;
    }
}
```
- Consumer Implementation with Virtual Threads
```java
@Service
public class VirtualThreadConsumer {

    private static final Logger log = LoggerFactory.getLogger(VirtualThreadConsumer.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @KafkaListener(topics = "${kafka.topic}", 
                  groupId = "${kafka.group}", 
                  containerFactory = "kafkaListenerContainerFactory")
    public void processBatch(List<ConsumerRecord<String, String>> records) {
        log.debug("Processing batch of {} records on virtual thread {}", 
                 records.size(), Thread.currentThread());
        
        // Batch database insert
        jdbcTemplate.batchUpdate(
            "INSERT INTO messages (id, content) VALUES (?, ?)",
            new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, records.get(i).key());
                    ps.setString(2, records.get(i).value());
                }
                
                @Override
                public int getBatchSize() {
                    return records.size();
                }
            });
    }
}
```
- Performance Optimization Settings
```yaml
# application.yml
spring:
  kafka:
    consumer:
      bootstrap-servers: ${KAFKA_BROKERS:localhost:9092}
      group-id: virtual-thread-group
      auto-offset-reset: earliest
      enable-auto-commit: false
      max-poll-records: 1000
      fetch-max-wait-ms: 500
      fetch-min-size: 1048576 # 1MB
      properties:
        max.partition.fetch.bytes: 1048576 # 1MB
        receive.buffer.bytes: 65536
```
- Error Handling with Virtual Threads
```java
@Bean
public DefaultErrorHandler errorHandler() {
    var exponentialBackOff = new ExponentialBackOff(1000, 2);
    exponentialBackOff.setMaxInterval(16000);
    exponentialBackOff.setMaxElapsedTime(60000);
    
    return new DefaultErrorHandler(
        (consumerRecord, exception) -> {
            log.error("Failed to process record: {}", consumerRecord, exception);
            // Dead letter queue logic here
        },
        exponentialBackOff
    );
}
```
- Monitoring Virtual Thread Performance
```java
@Bean
public MeterRegistryCustomizer<MeterRegistry> metrics() {
    return registry -> {
        registry.config().commonTags("application", "kafka-virtual-thread-consumer");
        
        // Track virtual thread creation
        Metrics.addRegistry(new SimpleMeterRegistry());
        ThreadGauge.monitor("kafka.virtual.threads", 
            Thread.ofVirtual().factory(),
            new ArrayList<>());
    };
}
```
### **3. Kafka Consumer Performance Tuning Configuration**
- **Optimal Consumer Settings**
```yaml
# application.yml
spring:
  kafka:
    consumer:
      # Polling behavior
      max-poll-records: 1000                 # Maximum records per poll
      max-poll-interval-ms: 300000           # 5 minute grace period (default: 5m)
      fetch-min-bytes: 16384                 # 16KB minimum fetch size
      fetch-max-wait-ms: 100                 # Wait up to 100ms for fetch.min.bytes
      
      # Network/performance
      fetch-max-bytes: 52428800              # 50MB max partition fetch
      connections-max-idle-ms: 540000        # 9 minutes (match AWS ELB timeout)
      request-timeout-ms: 40000              # 40s request timeout
      
      # Heartbeat settings to prevent rebalances
      heartbeat-interval-ms: 3000            # 3s heartbeat
      session-timeout-ms: 45000              # 45s session timeout (must be >3x heartbeat)
      
      # Memory management
      receive-buffer-bytes: 65536            # 64KB socket buffer
      max-partition-fetch-bytes: 1048576     # 1MB per partition
```
- Container Factory Configuration
```java
@Bean
public ConcurrentKafkaListenerContainerFactory<String, String> 
    kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
    
    var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
    factory.setConsumerFactory(consumerFactory);
    
    // Critical performance settings
    factory.getContainerProperties().setPollTimeout(3000);  // 3s poll timeout
    factory.getContainerProperties().setAckMode(AckMode.BATCH);  // Commit after batch
    
    // Pause/resume instead of rebalance
    factory.getContainerProperties().setPauseEnabled(true);
    factory.setPauseAfterOOMError(true);
    
    return factory;
}
```
- Listener Implementation with Flow Control
```java
@KafkaListener(topics = "high-volume", containerFactory = "kafkaListenerContainerFactory")
public void processBatch(List<ConsumerRecord<String, String>> records) {
    try {
        // Batch processing with timeout
        CompletableFuture.runAsync(() -> processRecords(records))
            .get(250, TimeUnit.MILLISECONDS);  // Timeout per batch
    } catch (TimeoutException e) {
        log.warn("Batch processing timeout, pausing consumer");
        // Implement pause/resume logic
    }
}

private void processRecords(List<ConsumerRecord<String, String>> records) {
    // Your processing logic
    records.forEach(record -> {
        if (System.currentTimeMillis() - record.timestamp() > 60000) {
            log.warn("Processing stale message: {}", record.offset());
        }
    });
}
```
- **Rebalance Prevention Strategies**
- Dynamic Poll Interval Adjustment
```java
@Scheduled(fixedRate = 30000)
public void adjustPolling() {
    long lag = getConsumerLag();
    if (lag > 10000) {
        // Increase throughput under load
        kafkaTemplate.executeInTransaction(t -> {
            t.setConsumerProperty(ConsumerConfig.FETCH_MAX_WAIT_MS, "10");
            return null;
        });
    }
}
```
- Health Check Endpoint
```java
@RestController
public class HealthController {
    
    @Autowired
    private KafkaListenerEndpointRegistry registry;
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        registry.getListenerContainers().forEach(container -> {
            if (!container.isRunning()) {
                container.start();
            }
        });
        return ResponseEntity.ok("OK");
    }
}
```
- Monitor Configuration
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,kafka,metrics
  metrics:
    tags:
      application: ${spring.application.name}
    distribution:
      percentiles-histogram:
        kafka.consumer.fetch.latency: true
```

## 8. Your Spring Boot application processes sensitive data from a Kafka topic. How would you implement encryption and decryption for messages in Kafka to ensure data security?

- To secure sensitive data in Kafka, you can implement end-to-end encryption by encrypting messages before sending them to Kafka and decrypting them upon consumption. 
- Additionally, use SSL/TLS for secure communication between producers, consumers, and Kafka brokers. The Java Cryptography Extension (JCE) can be used for message-level encryption and decryption.

- **Steps to Implement Encryption and Decryption**
- **Message-Level Encryption (Producer Side)**
- Encrypt sensitive data before sending it to Kafka using a symmetric encryption algorithm like AES (available in JCE). This ensures that the message payload is encrypted and unreadable by unauthorized parties, even if intercepted.
- **Message-Level Decryption (Consumer Side)**
Decrypt the messages on the consumer side using the same symmetric key and algorithm. Store the encryption key securely (e.g., in AWS Secrets Manager or a Kubernetes secret).
- **SSL/TLS for Transport Security**
Configure Kafka to use SSL/TLS for secure communication between producers, consumers, and brokers. This protects data in transit and ensures authentication of the Kafka brokers.
- **Key Management**
Use a secure key management system (e.g., AWS Secrets Manager, HashiCorp Vault, or Kubernetes secrets) to store and retrieve encryption keys. Avoid hardcoding keys in the application code.
- **Error Handling and Logging**
Implement robust error handling for encryption/decryption failures and avoid logging sensitive data. Use logging to track encryption-related issues without exposing plaintext.

Implementation:
- Below is an example of implementing message-level encryption/decryption using AES with JCE and configuring SSL/TLS for Kafka in a Spring Boot application.

### **1.Encryption/Decryption Utility**
- Create a utility class for AES encryption and decryption:
```java
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CryptoUtil {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String SECRET_KEY = "my-secret-key-123"; // Replace with secure key management

    public static String encrypt(String data) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
```
- In a production environment, replace the hardcoded SECRET_KEY with a key retrieved from a secure key management system (e.g., AWS Secrets Manager). 

```java
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

public class CryptoUtil {
    private static String getSecretKey() {
        SecretsManagerClient client = SecretsManagerClient.create();
        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId("my-kafka-encryption-key")
                .build();
        return client.getSecretValue(request).secretString();
    }
}
```
### **2.Producer Configuration with SSL**
- Configure the Kafka producer with SSL and integrate encryption
```java
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-broker1:9093,kafka-broker2:9093");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
        // SSL configuration
        configProps.put("security.protocol", "SSL");
        configProps.put("ssl.truststore.location", "/path/to/truststore.jks");
        configProps.put("ssl.truststore.password", "truststore-password");
        configProps.put("ssl.keystore.location", "/path/to/keystore.jks");
        configProps.put("ssl.keystore.password", "keystore-password");
        configProps.put("ssl.key.password", "key-password");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
```
### **3. Producer Service with Encryption**
- Implement a service to encrypt and send messages
```java
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        try {
            // Encrypt the message
            String encryptedMessage = CryptoUtil.encrypt(message);
            kafkaTemplate.send(topic, encryptedMessage)
                    .addCallback(
                            result -> System.out.println("Sent encrypted message to topic " + topic),
                            ex -> System.err.println("Failed to send message: " + ex.getMessage())
                    );
        } catch (Exception e) {
            System.err.println("Encryption failed: " + e.getMessage());
            throw new RuntimeException("Failed to encrypt message", e);
        }
    }
}
```
-

### **4. Consumer Configuration with SSL**
- Configure the consumer with SSL and integrate decryption
```java
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-broker1:9093,kafka-broker2:9093");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // SSL configuration
        configProps.put("security.protocol", "SSL");
        configProps.put("ssl.truststore.location", "/path/to/truststore.jks");
        configProps.put("ssl.truststore.password", "truststore-password");
        configProps.put("ssl.keystore.location", "/path/to/keystore.jks");
        configProps.put("ssl.keystore.password", "keystore-password");
        configProps.put("ssl.key.password", "key-password");
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}
```

### **5. Consumer Implementation with Decryption**
- Implement a @KafkaListener to decrypt and process messages
```java
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KafkaConsumerService {

    private final DatabaseService databaseService;

    public KafkaConsumerService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @KafkaListener(topics = "my-topic", groupId = "my-consumer-group")
    @Transactional
    public void consumeMessage(String encryptedMessage, Acknowledgment acknowledgment) {
        try {
            // Decrypt the message
            String decryptedMessage = CryptoUtil.decrypt(encryptedMessage);
            // Process the decrypted message (e.g., save to database)
            MyEntity entity = new MyEntity(decryptedMessage);
            databaseService.save(entity);
            // Commit offset after successful processing
            acknowledgment.acknowledge();
        } catch (Exception e) {
            System.err.println("Decryption or processing failed: " + e.getMessage());
            throw new RuntimeException("Failed to process message", e);
        }
    }
}
```
- A simple database service for saving processed messages
```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatabaseService {

    private final MyEntityRepository repository;

    public DatabaseService(MyEntityRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void save(MyEntity entity) {
        repository.save(entity);
    }
}

interface MyEntityRepository extends JpaRepository<MyEntity, Long> {
}
```
- **Application Properties**
- Configure Kafka (with SSL) and database settings in application.yml

```yaml
spring:
  kafka:
    bootstrap-servers: <kafka-broker-endpoint>:9093
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
      acks: all
      retries: 3
      properties:
        security.protocol: SSL
        ssl.truststore.location: /path/to/truststore.jks
        ssl.truststore.password: truststore-password
        ssl.keystore.location: /path/to/keystore.jks
        ssl.keystore.password: keystore-password
        ssl.key.password: key-password
    consumer:
      group-id: my-consumer-group
      auto-offset-reset: earliest
      enable-auto-commit: false
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      properties:
        security.protocol: SSL
        ssl.truststore.location: /path/to/truststore.jks
        ssl.truststore.password: truststore-password
        ssl.keystore.location: /path/to/keystore.jks
        ssl.keystore.password: keystore-password
        ssl.key.password: key-password
  datasource:
    url: jdbc:postgresql://<rds-endpoint>:5432/mydb
    username: myuser
    password: mypassword
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### **6. Kafka Cluster SSL Configuration**
- To enable SSL in the Kafka cluster:
- Generate Certificates: Create a Java Keystore (JKS) for the truststore and keystore using tools like keytool or OpenSSL.
- Configure Brokers: Update the Kafka broker configuration (server.properties) with SSL settings
```properties
security.protocol=SSL
ssl.keystore.location=/path/to/kafka.keystore.jks
ssl.keystore.password=keystore-password
ssl.key.password=key-password
ssl.truststore.location=/path/to/kafka.truststore.jks
ssl.truststore.password=truststore-password
```

- Kubernetes: Store SSL certificates and encryption keys as Kubernetes secrets and mount them to the application pods:
```yaml
apiVersion: v1
kind: Secret
metadata:
  name: kafka-ssl-secrets
type: Opaque
data:
  truststore.jks: <base64-encoded-truststore>
  keystore.jks: <base64-encoded-keystore>
  truststore-password: <base64-encoded-password>
  keystore-password: <base64-encoded-password>
  key-password: <base64-encoded-password>
  ```

  ## 9. Kafka guarantees order only within a single partition. How do you ensure global order or per-key order when the topic has many partitions and the app runs clustered?

  ### **1. Decide the real ordering requirement**
| Scope                      | Design                                                               |
| -------------------------- | -------------------------------------------------------------------- |
| **Global order** (rare)    | Single partition (limits throughput)                                 |
| **Per-key order** (common) | Same key → same partition via **key-hash** or **custom partitioner** |

### **2. Per-key strict ordering – the canonical pattern**
#### **2.1 Producer – use key as partition key**
```java
kafkaTemplate.send("orders", order.getCustomerId(), order);
```
- Kafka’s DefaultPartitioner hashes customerId → deterministic partition.
- Same customer always lands on the same partition.
#### **2.2 Consumer – one thread per partition**
```yaml
spring:
  kafka:
    consumer:
      group-id: order-processor
    listener:
      concurrency: 6   # must match number of partitions
```
- Each pod instance gets N partitions, each handled by exactly one thread → per-key order preserved.
### **3. When key-space is too large → partition key extraction
- Create a partition key from the natural key
| Business key | Partition key       | Example                       |
| ------------ | ------------------- | ----------------------------- |
| `customerId` | `customerId % 1000` | `"cust-42"` → partition 42    |
| `orderId`    | first 4 chars       | `"ORD-1234"` → partition 1234 |

#### **3.1 Custom partitioner**
```java
public class CustomerPartitioner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes,
                         Object value, byte[] valueBytes, Cluster cluster) {
        int partitions = cluster.partitionCountForTopic(topic);
        return Math.abs(key.hashCode()) % partitions;
    }
}
```

```yaml
spring:
  kafka:
    producer:
      properties:
        partitioner.class: com.acme.kafka.CustomerPartitioner
```


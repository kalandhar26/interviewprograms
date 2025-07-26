## You have implemented a Spring-Kafka producer, but the records never appear in the topic. What are the most common root causes and how do you verify/fix them?

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

## Your Spring-Boot consumer receives the Kafka record, but nothing is persisted to the database. How do you systematically troubleshoot?

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

## How do I introduce a configurable delay between “poll” and “process” in a Spring-Boot 3 / Java 21 Kafka consumer without blocking the listener thread?

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

## Your Spring-Boot consumers run clustered (many pods). How do you guarantee exactly-once semantics end-to-end (no duplicates, no loss) and what are the minimal, concrete changes in application.yml and code?

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

## **4. Listener method – just annotate with Spring TX**

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

## **5. Clustered Environment Coordination**

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

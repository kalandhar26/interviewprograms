# How does kafka ensure message delivery (atleast once, atmost once and exacly once)?

- **At-Most-Once:** A message is delivered zero or one time; it may be lost but never duplicated.
- **How Kafka Achieves It:**
  1. Producers send messages without waiting for broker acknowledgment (acks=0).
  2. Consumers commit offsets immediately after receiving messages, before processing.
- **Mechanism:** No retries on producer failure, and early offset commits on the consumer side risk message loss if
  processing fails.
- **Use Case:** Suitable for scenarios where losing a message is acceptable (e.g., non-critical logs).

- **At-Least-Once:** A message is delivered one or more times; it’s never lost but may be duplicated.
- **How Kafka Achieves It:**
  1. Producers wait for broker acknowledgment (acks=1 or acks=all) and retry on failure.
  2. Consumers process messages first, then commit offsets, ensuring no message is missed.
  3. If a consumer fails after processing but before committing, it may reprocess the message, leading to duplicates.
- **Mechanism:** Producer retries and delayed offset commits ensure delivery but risk duplicates.
- **Use Case:** Used when message loss is unacceptable (e.g., financial transactions).

- **Exactly-Once:**A message is delivered exactly once—no loss, no duplicates.
- **How Kafka Achieves It:**

  1. Introduced in Kafka 0.11 with idempotent producers and transactional messaging.
  2. Idempotent Producer: Assigns a unique Producer ID (PID) and sequence number to each message. Brokers detect and
     eliminate duplicates based on PID and sequence.
  3. Transactional Consumer: Uses transactions to ensure atomic writes across multiple partitions. Consumers read only
     committed messages (isolation.level=read_committed).

- **Mechanism:** Producers use enable.idempotence=true and transactional APIs (transactional.id). Consumers commit
  offsets transactionally, ensuring processing and offset commits are atomic.
- **Use Case:** Critical for applications requiring strict consistency (e.g., payment systems).

**Summary:**

- at-most-once by skipping retries and committing early.
- at-least-once with retries and delayed commits.
- exactly-once using idempotent producers and transactional APIs to prevent loss and duplicates.

# How do you test Kakfa consumers in a real time scenario?

- Embedded Kafka (for unit/integration tests):

1. Use @EmbeddedKafka (Spring Kafka) to spin up a local Kafka broker.
2. Test consumer logic without external dependencies.

- Mocking with KafkaTemplate (for unit tests):

1. Mock KafkaTemplate to simulate message production.
2. Verify consumer behavior using @SpyBean or Mockito.

- Test Containers (for near-real integration tests):

1. Use Dockerized Kafka (Testcontainers) for a lightweight, real broker.
2. Test end-to-end flow with actual Kafka networking.

- Manual Testing (Real Kafka Cluster):

1. Use tools like kafkacat or scripts to produce test messages.
2. Monitor consumer logs/offsets (consumer-groups CLI).

## How do we guarantee that only committed (i.e., fully acknowledged) records ever reach our listener when brokers or producers use transactions?

- Use the consumer isolation-level setting

```yaml
spring:
  kafka:
    consumer:
      isolation-level: read_committed # default = read_uncommitted
```

---

## Question 1: What is Kafka and How Does It Work?

### Solution

Apache Kafka is a distributed streaming platform designed for high-throughput, fault-tolerant, and scalable processing of real-time data feeds, functioning as a publish-subscribe messaging system.

- **Distributed System**: Kafka runs as a cluster of brokers, storing messages in a durable, replicated log.
- **Publish-Subscribe**: Producers send messages to topics; consumers subscribe to read them.
- **Topics and Partitions**: Messages are stored in topics, split into partitions for parallelism.
- **Durability**: Messages are persisted to disk and replicated across brokers.
- **Scalability**: Scales by adding brokers or partitions to handle increased load.
- **Workflow**:
  - Producers publish messages to topics, stored in partitions with unique offsets.
  - Consumers read messages, tracking progress via offsets.
  - ZooKeeper or KRaft manages cluster coordination (e.g., leader election).
- **Use Cases**: Log aggregation, event streaming, real-time analytics.

```java
// Simple Kafka Producer
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

public class SimpleProducer {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>("my-topic", "key", "Hello, Kafka!")).get();
        }
    }
}
```

## Question 2: Explain Kafka Architecture (Brokers, ZooKeeper, Topics, Partitions, Consumers)

### Solution

Kafka’s architecture supports scalability and fault tolerance through distributed components.

- **Brokers**: Servers storing topic partitions, handling read/write requests. One broker is the leader per partition; others are followers.
- **ZooKeeper/KRaft**: ZooKeeper (pre-3.3) or KRaft (3.3+) manages metadata, leader election, and consumer group coordination.
- **Topics**: Logical channels for messages, split into partitions.
- **Partitions**: Ordered, immutable logs for scalability and parallelism, replicated across brokers.
- **Consumers**: Read messages from topics, either standalone or in consumer groups for load balancing.
- **Producers**: Send messages to topics, optionally specifying partitions or keys.
- **Replication**: Each partition has a leader and follower replicas for fault tolerance.
- **Workflow**: Producers write to leader partitions; consumers read from assigned partitions, with offsets tracked in `__consumer_offsets`.

```java
// Simple Kafka Consumer
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class SimpleConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "my-consumer-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("enable.auto.commit", "true");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList("my-topic"));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> System.out.printf("offset=%d, value=%s%n", record.offset(), record.value()));
            }
        }
    }
}
```

## Question 3: Difference Between Kafka and Traditional Message Queues (e.g., RabbitMQ)

### Solution

Kafka differs from traditional message queues like RabbitMQ in design and use cases.

- **Architecture**:
  - Kafka: Distributed log with persistent storage, partitioned topics for scalability.
  - RabbitMQ: Queue-based system with messages typically removed after consumption.
- **Message Retention**:
  - Kafka: Retains messages for a configurable period (e.g., days), allowing replay.
  - RabbitMQ: Messages are deleted after acknowledgment unless explicitly persisted.
- **Scalability**:
  - Kafka: Scales horizontally via partitions and brokers, handling high throughput.
  - RabbitMQ: Scales via clustering but is less suited for massive data volumes.
- **Delivery Semantics**:
  - Kafka: At-least-once by default, supports exactly-once with transactions.
  - RabbitMQ: Supports at-most-once, at-least-once, or exactly-once with AMQP.
- **Use Cases**:
  - Kafka: Event streaming, log aggregation, real-time analytics.
  - RabbitMQ: Task queues, point-to-point messaging, workflow orchestration.
- **Consumer Model**:
  - Kafka: Consumer groups for parallel processing.
  - RabbitMQ: Work queues with competing consumers.

## Question 4: How Does Kafka Ensure Message Delivery?

### Solution

Kafka ensures message delivery through replication, acknowledgments, and offset management.

- **Replication**: Partitions are replicated across brokers (controlled by `replication.factor`), ensuring data availability if a broker fails.
- **Producer Acks**:
  - `acks=0`: No acknowledgment; risk of loss.
  - `acks=1`: Leader acknowledges; risk of loss if leader fails before replication.
  - `acks=all`: All in-sync replicas acknowledge, ensuring durability.
- **Retries**: Producers retry failed sends (configurable via `retries`).
- **Consumer Offset Commits**: Consumers commit offsets to track processed messages, resuming from the last offset on restart.
- **Idempotence**: Prevents duplicates by assigning unique sequence numbers to messages.
- **Transactions**: Enable exactly-once delivery by coordinating writes and offset commits.

```java
// Producer with Reliable Delivery
Properties props = new Properties();
props.put("bootstrap.servers", "localhost:9092");
props.put("acks", "all");
props.put("retries", 3);
props.put("enable.idempotence", true);
```

## Question 5: What is the Role of `acks`, `retries`, and `linger.ms` in a Producer Config?

### Solution

These configurations control producer reliability and performance.

- **acks**:
  - Specifies acknowledgment requirements.
  - `acks=0`: No acknowledgment; fastest but risks loss.
  - `acks=1`: Leader acknowledges; balances speed and reliability.
  - `acks=all`: All in-sync replicas acknowledge; maximizes durability.
- **retries**:
  - Number of retry attempts for transient failures (e.g., network issues).
  - Set to a positive integer (e.g., 3) to handle temporary broker unavailability.
- **linger.ms**:
  - Time (in milliseconds) the producer buffers messages before sending.
  - Higher values increase latency but improve throughput by batching messages.
  - Example: `linger.ms=5` waits 5ms to accumulate messages.

```java
// Producer Config Example
Properties props = new Properties();
props.put("bootstrap.servers", "localhost:9092");
props.put("acks", "all");
props.put("retries", 3);
props.put("linger.ms", 5);
props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
```

## Question 6: Explain Consumer Groups and Offset Management

### Solution

Consumer groups and offset management enable scalable and fault-tolerant message processing.

- **Consumer Groups**:
  - A group of consumers that share the same `group.id` to process a topic’s partitions in parallel.
  - Kafka assigns each partition to exactly one consumer in the group.
  - Rebalancing occurs when consumers join/leave, redistributing partitions.
- **Offset Management**:
  - Each message in a partition has a unique offset, tracking consumer progress.
  - Offsets are stored in Kafka’s `__consumer_offsets` topic or committed manually.
  - `enable.auto.commit=true`: Automatically commits offsets periodically.
  - `enable.auto.commit=false`: Manual commits via `Acknowledgment.acknowledge()` for precise control.
- **Use Cases**: Load balancing, fault tolerance (e.g., resuming after crashes).

```java
// Spring Kafka Consumer with Manual Offset Commit
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "my-topic", groupId = "my-consumer-group")
    public void consume(String message, Acknowledgment acknowledgment) {
        System.out.println("Received: " + message);
        acknowledgment.acknowledge(); // Manual commit
    }
}
```

## Question 7: How Do You Tune Kafka for High Throughput?

### Solution

Tuning Kafka for high throughput involves optimizing producer, consumer, and broker settings.

- **Producer Tuning**:
  - Increase `batch.size` (e.g., 16384) to send larger batches.
  - Set `linger.ms` (e.g., 5) to batch messages before sending.
  - Enable compressio an (`compression.type=gzip`) to reduce network load.
- **Consumer Tuning**:
  - Increase `max.poll.records` (e.g., 500) to fetch more messages per poll.
  - Set `fetch.min.bytes` (e.g., 1024) to wait for more data before polling.
- **Broker Tuning**:
  - Increase `num.io.threads` and `num.network.threads` for better I/O handling.
  - Set `replication.factor=3` and `min.insync.replicas=2` for durability.
- **Topic Configuration**:
  - Increase partitions (e.g., 10) for parallelism.
  - Optimize `segment.bytes` and `segment.ms` for efficient log management.
- **Infrastructure**: Use high-performance disks (e.g., SSDs) and sufficient brokers in AWS MSK or Kubernetes.

```yaml
# application.yml
spring:
  kafka:
    producer:
      batch-size: 16384
      linger-ms: 5
      compression-type: gzip
    consumer:
      max-poll-records: 500
      fetch-min-bytes: 1024
```

## Question 8: What is Idempotence in Kafka Producer?

### Solution

Idempotence ensures that a producer does not create duplicate messages during retries.

- **Definition**: Prevents duplicates by assigning unique sequence numbers to messages.
- **Enablement**: Set `enable.idempotence=true` in producer config.
- **Mechanism**:
  - Producer assigns a `Producer ID` (PID) and sequence number to each message.
  - Kafka brokers track sequence numbers to deduplicate messages.
- **Benefits**: Guarantees at-least-once delivery without duplicates, enabling exactly-once semantics when combined with transactions.
- **Requirements**: `acks=all`, `retries>0`, and `max.in.flight.requests.per.connection=1` (default for idempotence).

```java
// Idempotent Producer Config
Properties props = new Properties();
props.put("bootstrap.servers", "localhost:9092");
props.put("enable.idempotence", true);
props.put("acks", "all");
props.put("retries", 3);
```

## Question 9: How Does Kafka Achieve Fault Tolerance?

### Solution

Kafka ensures fault tolerance through replication and distributed architecture.

- **Replication**:
  - Each partition is replicated across multiple brokers (`replication.factor`).
  - One broker is the leader; others are followers, syncing data.
- **Leader Election**: If a leader fails, a follower is promoted via ZooKeeper/KRaft.
- **In-Sync Replicas (ISR)**: Only ISRs can become leaders, ensuring data consistency.
- **Broker Redundancy**: Multiple brokers in the cluster prevent single-point failures.
- **Consumer Group Rebalancing**: Reassigns partitions if a consumer fails.
- **Offset Management**: Consumers resume from last committed offset after crashes.

```bash
# Create topic with replication
kafka-topics.sh --create --topic my-topic --bootstrap-server localhost:9092 --replication-factor 3 --partitions 4
```

## Question 10: How Would You Design a Log Aggregation Pipeline Using Kafka?

### Solution

A log aggregation pipeline collects logs from multiple sources, processes them, and stores or analyzes them.

- **Producers**: Applications or agents (e.g., Fluentd, Logstash) send logs to a Kafka topic.
- **Topic Design**: Use a topic (e.g., `logs`) with multiple partitions for scalability and a high `replication.factor` (e.g., 3).
- **Consumers**: Spring Boot application consumes logs, processes them (e.g., parse, enrich), and stores them in a database (e.g., Elasticsearch on AWS).
- **Scaling**: Increase partitions and consumer instances to handle high log volumes.
- **Monitoring**: Use Prometheus to track consumer lag and log ingestion rate.
- **Durability**: Set `acks=all` and manual offset commits to prevent loss.

```java
// Spring Boot Log Producer
@Service
public class LogProducerService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendLog(String log) {
        kafkaTemplate.send("logs", log);
    }
}
```

## Question 11: What Happens if a Kafka Consumer Crashes? How Does It Resume?

### Solution

Kafka handles consumer crashes via consumer groups and offset management.

- **Crash Impact**: The consumer group rebalances, reassigning the crashed consumer’s partitions to other instances.
- **Offset Management**:
  - If `enable.auto.commit=true`, offsets are periodically committed, and the new consumer resumes from the last committed offset.
  - If `enable.auto.commit=false`, manual commits ensure no offsets are committed until processing completes.
- **Resume Process**: The new consumer starts reading from the last committed offset in `__consumer_offsets`.
- **Configuration**: Set `auto.offset.reset=earliest` to start from the beginning if no offsets exist.

```java
// Manual Offset Commit
@KafkaListener(topics = "my-topic", groupId = "my-consumer-group")
public void consume(String message, Acknowledgment acknowledgment) {
    processMessage(message);
    acknowledgment.acknowledge();
}
```

## Question 12: How Do You Handle Backpressure in Kafka?

### Solution

Backpressure occurs when consumers cannot keep up with message production.

- **Increase Consumers**: Add more consumer instances in the same group to process partitions in parallel.
- **Increase Partitions**: Add partitions to the topic to allow more parallelism.
- **Batch Processing**: Configure consumers to process messages in batches (`max.poll.records`).
- **Throttle Producers**: Implement rate limiting on producers to reduce message rate.
- **Dead-Letter Topic (DLT)**: Route unprocessable messages to a DLT for later analysis.
- **Monitoring**: Track consumer lag to detect backpressure early.

```yaml
spring:
  kafka:
    consumer:
      max-poll-records: 500
```

## Question 13: How Do You Monitor Kafka in Production?

### Solution

Monitoring Kafka ensures performance and reliability.

- **Metrics**:
  - Use JMX metrics (e.g., `kafka.server:type=BrokerTopicMetrics,name=MessagesInPerSec`).
  - Monitor consumer lag (`kafka_consumergroup_lag`).
- **Tools**:
  - Prometheus + Grafana for visualizing metrics.
  - AWS CloudWatch for MSK clusters.
  - Kafka Manager or Confluent Control Center for cluster health.
- **Logs**: Enable debug logging for `org.apache.kafka` and `org.springframework.kafka`.
- **Alerts**: Set alerts for high consumer lag, broker failures, or low disk space.
- **Key Metrics**: Message throughput, partition latency, broker CPU/disk usage.

```yaml
# Prometheus Config for Kafka
scrape_configs:
  - job_name: "kafka"
    static_configs:
      - targets: ["kafka-broker:9999"]
```

## Question 14: How Would You Deploy Kafka in Kubernetes?

### Solution

Deploying Kafka in Kubernetes ensures scalability and manageability.

- **StatefulSet**: Use a `StatefulSet` for Kafka brokers to ensure stable network identities and persistent storage.
- **ZooKeeper/KRaft**: Deploy ZooKeeper or use KRaft for cluster coordination.
- **Storage**: Use PersistentVolumeClaims (PVCs) for broker data (e.g., EBS volumes in AWS EKS).
- **Service**: Expose brokers via a `ClusterIP` or `LoadBalancer` service for client access.
- **Scaling**: Increase `replicas` in the `StatefulSet` to add brokers.
- **Monitoring**: Integrate Prometheus for metrics and logging.

```yaml
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: kafka
spec:
  replicas: 3
  serviceName: kafka
  selector:
    matchLabels:
      app: kafka
  template:
    metadata:
      labels:
        app: kafka
    spec:
      containers:
        - name: kafka
          image: confluentinc/cp-kafka:latest
          env:
            - name: KAFKA_BROKER_ID
              valueFrom:
                fieldRef:
                  fieldPath: metadata.name
            - name: KAFKA_ZOOKEEPER_CONNECT
              value: "zookeeper:2181"
          volumeMounts:
            - name: data
              mountPath: /var/lib/kafka/data
  volumeClaimTemplates:
    - metadata:
        name: data
      spec:
        accessModes: ["ReadWriteOnce"]
        resources:
          requests:
            storage: 10Gi
```

## Question 15: What are Kafka Retention Policies?

### Solution

Retention policies control how long Kafka retains messages and logs.

- **Retention Period**:
  - `retention.ms`: Time messages are kept (e.g., 7 days = 604800000ms).
  - `retention.bytes`: Maximum size per partition before old messages are deleted.
- **Log Cleanup Policies**:
  - `delete`: Removes old messages after retention period/size.
  - `compact`: Removes duplicate keys, keeping the latest value (log compaction).
- **Configuration**: Set at the topic level or broker-wide (`log.retention.hours`).
- **Use Case**: Long retention for replayable logs; compaction for key-based data.

```bash
# Set retention period
kafka-topics.sh --alter --topic my-topic --bootstrap-server localhost:9092 --config retention.ms=604800000
```

## Question 16: What is Kafka Streams? When Would You Use It Over Apache Spark?

### Solution

Kafka Streams is a lightweight stream processing library for building real-time applications.

- **Kafka Streams**:
  - Java library for processing Kafka topic data (e.g., filtering, aggregation).
  - Integrates natively with Kafka, no external cluster needed.
  - Supports stateful (e.g., joins) and stateless operations.
- **Use Cases**: Real-time event processing, data transformation, microservices.
- **Kafka Streams vs. Spark**:
  - Kafka Streams: Lightweight, low-latency, tightly integrated with Kafka, ideal for simple stream processing in Spring Boot.
  - Spark: Heavyweight, supports batch and stream processing, better for complex analytics or large-scale data.
- **Choose Kafka Streams**:
  - When processing is Kafka-centric and lightweight.
  - For low-latency, real-time applications.
  - When avoiding external cluster management (e.g., Spark cluster).

```java
// Kafka Streams Example
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;

public class StreamsExample {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("application.id", "streams-app");
        props.put("bootstrap.servers", "localhost:9092");

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> stream = builder.stream("input-topic");
        stream.filter((key, value) -> value.contains("important"))
              .to("output-topic");

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}
```

## Question 17: How Does Kafka Handle Message Reprocessing?

### Solution

Kafka supports message reprocessing by allowing consumers to rewind offsets.

- **Offset Rewind**:
  - Consumers can reset offsets to an earlier point using `seek` or `auto.offset.reset=earliest`.
  - Useful for reprocessing historical data or recovering from errors.
- **Consumer Group Reset**:
  - Use `kafka-consumer-groups.sh --reset-offsets` to reset offsets for a group.
  - Options: `--to-earliest`, `--to-offset`, `--to-datetime`.
- **Retention Period**: Messages must be within the topic’s retention period (`retention.ms`).
- **Log Compaction**: For compacted topics, only the latest message per key is available for reprocessing.

```bash
# Reset consumer group offsets
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-consumer-group --reset-offsets --to-earliest --topic my-topic --execute
```

## Question 18: What is Exactly-Once Semantics in Kafka?

### Solution

Exactly-once semantics (EOS) ensures each message is processed exactly once, avoiding duplicates or loss.

- **Mechanism**:
  - Enabled via idempotent producers (`enable.idempotence=true`) to prevent duplicates.
  - Uses transactions to coordinate message production and offset commits atomically.
- **Producer**: Assigns unique sequence numbers and uses `transactional.id` for atomic writes.
- **Consumer**: Sets `isolation.level=read_committed` to read only committed messages.
- **Use Case**: Critical applications requiring no duplicates (e.g., financial transactions).
- **Spring Boot Example**:
  - Configure `transactional.id` and manual offset commits.

```java
// Transactional Producer
Properties props = new Properties();
props.put("bootstrap.servers", "localhost:9092");
props.put("transactional.id", "tx-producer");
props.put("enable.idempotence", true);
KafkaProducer<String, String> producer = new KafkaProducer<>(props);
producer.initTransactions();
producer.beginTransaction();
producer.send(new ProducerRecord<>("my-topic", "key", "value"));
producer.commitTransaction();
```

## Question 19: How Do You Secure Kafka in a Spring Boot Application?

### Solution

Securing Kafka involves encryption, authentication, and authorization.

- **SSL/TLS**:
  - Enable `security.protocol=SSL` for encrypted communication.
  - Configure truststore and keystore for brokers and clients.
- **Authentication**:
  - Use SASL (e.g., SASL/SCRAM, SASL/PLAIN) or IAM with AWS MSK.
  - Example: `sasl.mechanism=SCRAM-SHA-512`.
- **Authorization**:
  - Enable ACLs to restrict topic access (`authorizer.class.name=kafka.security.authorizer.AclAuthorizer`).
- **Spring Boot Config**:
  - Set SSL and SASL properties in `application.yml`.
- **Key Management**: Store certificates and credentials in AWS Secrets Manager or Kubernetes secrets.

```yaml
spring:
  kafka:
    bootstrap-servers: <broker>:9093
    properties:
      security.protocol: SSL
      ssl.truststore.location: /path/to/truststore.jks
      ssl.truststore.password: truststore-password
```

## Question 20: How Do You Handle Schema Evolution in Kafka?

### Solution

Schema evolution ensures compatibility when message formats change.

- **Schema Registry**: Use Confluent Schema Registry to manage Avro, JSON, or Protobuf schemas.
- **Compatibility Types**:
  - Backward: New consumers can read old messages.
  - Forward: Old consumers can read new messages.
  - Full: Both backward and forward compatible.
- **Spring Boot Integration**:
  - Use `spring-kafka` with `avro-serde` for schema-aware serialization.
- **Best Practices**: Version schemas, test compatibility, and update consumers/producers incrementally.

```java
// Avro Producer with Schema Registry
@Bean
public ProducerFactory<String, MyAvroRecord> producerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://schema-registry:8081");
    return new DefaultKafkaProducerFactory<>(props);
}
```

## Question 21: How Do You Handle Dead-Letter Topics (DLTs) in Spring Boot?

### Solution

DLTs store messages that fail processing for later analysis.

- **Configuration**:
  - Define a DLT topic (e.g., `my-topic-dlt`).
  - Use `DeadLetterPublishingRecoverer` to route failed messages.
- **Error Handling**: Catch exceptions in `@KafkaListener` and publish to DLT.
- **Monitoring**: Monitor DLT for failed messages and process them manually or automatically.

```java
@Bean
public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer(KafkaTemplate<String, String> template) {
    return new DeadLetterPublishingRecoverer(template);
}

@KafkaListener(topics = "my-topic")
public void consume(String message) {
    try {
        processMessage(message);
    } catch (Exception e) {
        deadLetterPublishingRecoverer.accept(record, e);
    }
}
```

## Question 22: How Do You Optimize Kafka Performance in AWS MSK?

### Solution

Optimizing Kafka in AWS MSK enhances throughput and reliability.

- **Broker Sizing**: Use appropriately sized instances (e.g., `kafka.m5.large`).
- **Storage**: Configure EBS volumes for high IOPS (e.g., `gp3`).
- **Multi-AZ Deployment**: Deploy across multiple availability zones for fault tolerance.
- **Monitoring**: Use CloudWatch for metrics (e.g., `BytesInPerSec`, `ConsumerLag`).
- **Tuning**:
  - Set `num.io.threads=8` and `num.network.threads=5` for brokers.
  - Use `compression.type=snappy` for producers.
- **IAM Authentication**: Enable IAM for secure access without managing credentials.

```yaml
spring:
  kafka:
    bootstrap-servers: <msk-broker>:9098
    properties:
      security.protocol: SASL_SSL
      sasl.mechanism: AWS_MSK_IAM
      sasl.jaas.config: software.amazon.msk.auth.iam.IAMLoginModule required;
```

## Question 23: If There are 2 consumer groups which consume from same topic. Will the message is polled twice? or this is not possible?

- Kafka keeps one offset per (partition, consumer-group).
- Both groups read the same physical records independently; nothing prevents the broker from sending the same message to both.
- Each consumer group maintains its own independent offset for every partition of a topic.
- The broker does not block one group just because another group is already reading that partition.
  Only within the same consumer group is a partition assigned to exactly one consumer at a time; across different groups, simultaneous access is allowed.

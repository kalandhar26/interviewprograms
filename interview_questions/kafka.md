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
# 1. **How did you handled transactions in this project?**

** In our microservices-based ING Bank Web Application, we handled transactions at two levels**

1. Within a single service (local transactions)
2. Across multiple services (distributed transactions)

## Local Transactions – Using **Spring’s @Transactional**

For atomic operations within a single service-

- debiting and crediting accounts in the Account Management Module
- updating account balances during a fund transfer. This was particularly important for database operations in the
  Account Management Module, where we used Spring Data JPA with MySQL to store account details and transaction records.

### Example

- when a customer initiated a transfer between two accounts, we needed to debit one account and credit another in a
  single atomic operation. If either step failed, the entire transaction would roll back to maintain consistency.

```java

@Transactional(rollbackOn = Exception.class)
public void transferFunds(Account source, Account destination, BigDecimal amount) {
    source.debit(amount);
    destination.credit(amount);
    accountRepository.save(source);
    accountRepository.save(destination);
}
```

-This guarantees that both operations happen in the same DB transaction, and if any exception occurs, the transaction is
rolled back.

### My Contributions

- I was involved in implementing this logic and optimizing the database queries to minimize locking issues, especially
  during high transaction volumes.
- To handle concurrency, we used optimistic locking with JPA’s @Version attribute on entities. This prevented race
  conditions when multiple transactions tried to update the same account simultaneously, ensuring data integrity without
  heavy locking. JPA will automatically check the version during flush/commit. If versions changed, it throws
  OptimisticLockException.
- If two users tried to update the same record, only one would succeed; the other would get a concurrency exception,
  which we could retry.
- I contributed to designing the event schema for these transactions and implementing retry logic for transient
  failures, such as network issues when publishing events to Kafka. This ensured robust coordination across services.

```java

@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private BigDecimal balance;

    @Version
    private Long version; // This enables optimistic locking

    // Constructors, getters, and setters
}
```

## Distributed Transactions – Using Saga Pattern (Choreography)

- Since this was a microservices architecture, we didn’t use traditional 2-phase commit (2PC) due to performance and
  complexity concerns. Instead, we implemented the Saga Pattern, specifically the Choreography-based Saga using Kafka.
- We often needed to coordinate transactions across multiple services, like the Payment Service, Account Management
  Service, and Credit Service.
- Each service performs its local transaction and then emits an event. Other services listen and react accordingly.each
  service performs its local transaction and publishes an event to trigger the next step. If a step fails, compensating
  events are published to undo previous actions.

### Example

- The Payment Service initiates the transaction and publishes a **PaymentInitiated** event.
- The Account Management Service listens for this event, debits the source account, and publishes an **AccountDebited**
  event.
- The destination account is credited, and an AccountCredited event is published.
- If any step fails (e.g., insufficient funds), a compensating event like RefundProcessed is triggered to roll back
  earlier actions.
- I contributed to designing the event schema for these transactions and implementing retry logic for transient
  failures, such as
  network issues when publishing events to Kafka. This ensured robust coordination across services.

### Payment Service (Initiator)

```java

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void initiatePayment(PaymentRequest request) {
        // Create payment record
        Payment payment = new Payment(request.getAmount(), request.getFromAccount(), request.getToAccount());
        paymentRepository.save(payment);

        // Publish PaymentInitiated event
        PaymentInitiatedEvent event = new PaymentInitiatedEvent(
                payment.getId(),
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount(),
                Instant.now()
        );

        kafkaTemplate.send("payment-initiated", event)
                .addCallback(
                        result -> log.info("PaymentInitiated event published"),
                        ex -> {
                            log.error("Failed to publish PaymentInitiated event", ex);
                            throw new EventPublishException("Failed to publish payment event");
                        }
                );
    }
}

// Event Class
public record PaymentInitiatedEvent(
        String paymentId,
        String fromAccount,
        String toAccount,
        BigDecimal amount,
        Instant timestamp
) {
}
```

### Account Service (Debit Handler)

```java

@Service
@RequiredArgsConstructor
public class AccountDebitHandler {
    private final AccountRepository accountRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "payment-initiated")
    @Transactional
    public void handlePaymentInitiated(PaymentInitiatedEvent event) {
        try {
            Account account = accountRepository.findByAccountNumber(event.fromAccount())
                    .orElseThrow(() -> new AccountNotFoundException(event.fromAccount()));

            if (account.getBalance().compareTo(event.amount()) < 0) {
                throw new InsufficientFundsException();
            }

            account.debit(event.amount());
            accountRepository.save(account);

            // Publish AccountDebited event
            AccountDebitedEvent debitedEvent = new AccountDebitedEvent(
                    event.paymentId(),
                    event.fromAccount(),
                    event.toAccount(),
                    event.amount(),
                    Instant.now()
            );

            kafkaTemplate.send("account-debited", debitedEvent);

        } catch (InsufficientFundsException e) {
            // Publish compensation event
            kafkaTemplate.send("payment-failed", new PaymentFailedEvent(
                    event.paymentId(),
                    "INSUFFICIENT_FUNDS",
                    Instant.now()
            ));
        }
    }
}

```

### Account Service (Credit Handler)

```java

@Service
@RequiredArgsConstructor
public class AccountCreditHandler {
    private final AccountRepository accountRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "account-debited")
    @Transactional
    public void handleAccountDebited(AccountDebitedEvent event) {
        try {
            Account account = accountRepository.findByAccountNumber(event.toAccount())
                    .orElseThrow(() -> new AccountNotFoundException(event.toAccount()));

            account.credit(event.amount());
            accountRepository.save(account);

            // Publish AccountCredited event
            AccountCreditedEvent creditedEvent = new AccountCreditedEvent(
                    event.paymentId(),
                    event.fromAccount(),
                    event.toAccount(),
                    event.amount(),
                    Instant.now()
            );

            kafkaTemplate.send("account-credited", creditedEvent);

        } catch (Exception e) {
            // Publish compensation event to trigger refund
            kafkaTemplate.send("refund-initiated", new RefundInitiatedEvent(
                    event.paymentId(),
                    event.fromAccount(),
                    event.amount(),
                    "CREDIT_FAILED",
                    Instant.now()
            ));
        }
    }
}

```

### Compensation Handler (Refund Logic)

```java

@Service
@RequiredArgsConstructor
public class RefundHandler {
    private final AccountRepository accountRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = {"payment-failed", "refund-initiated"})
    @Transactional
    public void handleRefund(Object event) {
        if (event instanceof PaymentFailedEvent failedEvent) {
            // Handle initial failure (no debit occurred)
            log.warn("Payment {} failed early: {}", failedEvent.paymentId(), failedEvent.reason());
            return;
        }

        if (event instanceof RefundInitiatedEvent refundEvent) {
            try {
                Account account = accountRepository.findByAccountNumber(refundEvent.accountNumber())
                        .orElseThrow(() -> new AccountNotFoundException(refundEvent.accountNumber()));

                account.credit(refundEvent.amount());
                accountRepository.save(account);

                // Publish RefundProcessed event
                kafkaTemplate.send("refund-processed", new RefundProcessedEvent(
                        refundEvent.paymentId(),
                        refundEvent.accountNumber(),
                        refundEvent.amount(),
                        Instant.now()
                ));
            } catch (Exception e) {
                // Implement retry logic with exponential backoff
                log.error("Failed to process refund for payment {}", refundEvent.paymentId(), e);
                throw e;
            }
        }
    }
}
```

### Retry Configuration (Spring Kafka)

```java

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        // Configure retry with exponential backoff
        factory.setRetryTemplate(retryTemplate());
        factory.setRecoveryCallback(context -> {
            log.error("Retries exhausted for message: {}", context.getLastThrowable().getMessage());
            // Optionally publish to dead letter topic
            return null;
        });

        return factory;
    }

    private RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000);
        backOffPolicy.setMultiplier(2.0);
        backOffPolicy.setMaxInterval(10000);

        retryTemplate.setBackOffPolicy(backOffPolicy);
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3));

        return retryTemplate;
    }
}

```
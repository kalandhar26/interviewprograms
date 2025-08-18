## Describe a time you faced a conflict at work. How did you resolve it?

- Situation: At my previous job, two teammates argued over how to handle a client project. One wanted to follow a
  faster, simpler approach, while the other insisted on a detailed plan, causing delays.
- Task: As the project coordinator, I needed to resolve the conflict so work could move forward without hurting team
  morale.
- Action: I set up a meeting to hear both sides. I suggested combining their ideas—starting with a basic plan (to save
  time) but adding key details the client cared about. Both agreed to compromise.
- Result: The project was completed on time, the client was happy, and the team learned to collaborate better. Arguments
  reduced in future projects.

## Tell me about a time you failed. How did you handle it?

- Situation:
    - While developing a RESTful API in a Spring Boot microservice, I introduced a circular dependency between two
      services—OrderService and PaymentService—where each depended on the other for transaction processing. This caused
      a BeanCurrentlyInCreationException during startup, breaking the application.
- Task:
    - I needed to refactor the architecture to remove the circular dependency without disrupting the existing business
      logic.

- Action:
    - Identified the Issue – Used Spring’s dependency injection logs to trace the circular reference.

- Applied a Fix – Instead of direct @Autowired dependencies, I:
    - Introduced an event-driven approach using Spring’s ApplicationEventPublisher.
    - Made PaymentService emit an OrderPaidEvent after payment, which OrderService consumed asynchronously.
    - Used @Async and @TransactionalEventListener to decouple the services.
    - Tested the Solution – Verified with JUnit & Mockito to ensure no side effects.
- Result:
- The microservice started successfully with no circular dependencies.
- Improved scalability since events allowed loose coupling.
- Later applied the event-driven pattern in other services to prevent similar issues.

## Give an example of a goal you reached and how you achieved it.

- In a recent project, our Spring Boot microservice handling user authentication was experiencing high latency during
  peak loads. My goal was to reduce response times by 50% within three weeks. I started by profiling the application
  with Spring Actuator and identified inefficient database queries as the main bottleneck. I optimized the JPA
  repository methods, added proper indexing, and implemented Redis caching for frequently accessed user data. After load
  testing with JMeter, we achieved a 60% reduction in response times, from 800ms to 320ms average. This improvement
  significantly enhanced the user experience during high-traffic periods and reduced server load by 35%. The success of
  this optimization led to adopting the same caching strategy across other microservices in our system.

## How do you handle working under pressure?

- During a major production outage where our payment microservice started failing, I led the troubleshooting under a
  tight 1-hour SLA. With transactions declining, I:
- Triaged by checking metrics (Prometheus), logs (ELK), and traces (Jaeger) to pinpoint a database connection pool
  exhaustion
- Prioritized a hotfix—increasing HikariCP pool size and adding circuit breakers—over investigating root cause
- Delegated tasks: My teammate handled rollback checks while I prepared the patch
- Communicated updates every 15 mins to stakeholders via Slack
- The service recovered in 45 minutes. Later, we implemented automated pool scaling to prevent recurrence.

## How do you motivate a team when morale is low?

- I know the past few days/weeks have been tough, with long hours, tight deadlines, and challenges that tested our
  patience. I want to take a moment to recognize the incredible effort each of you has put in. Even when things felt
  stuck, you kept pushing, collaborating, and supporting each other — and that shows true strength.

- Remember this: every challenge we overcome makes us stronger, sharper, and more resilient as a team. We’ve solved
  tough issues before, and I have no doubt we’ll get through this one too.

- Let’s focus on small wins, one step at a time. Every fix, every test, every idea you contribute brings us closer to
  the solution. And as always, I’ve got your back — I’ll shield you from unnecessary pressure so you can do what you do
  
- What we build impacts thousands of customers who rely on us every day. We’re not just fixing bugs — we’re protecting trust
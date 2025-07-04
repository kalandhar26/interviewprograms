1. **How did you handled transaction when failed?**
- We use Saga Pattern to manage data consistency across microservices in distributed transaction scenarios.

2. **How do you handle failures?**
 - We use Circuit Breaker Pattern to manage failures by using Resilience4j

3. **How do you manage centralized configuration?**
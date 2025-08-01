## **1. Why Constructor Injection is preferred over Setter Injection?**

- Construction Injection is enables Immutability and ThreadSafe as it enables final fields. objects become immutable
  after construction. prevents accidental modification after initilization.
- Constructor guarantees all dependencies are provided. Constructor injection fails fast if circular dependencies exist

## **2. Why Spring Boot Over Spring?**

**Simplified Configuration:**

- Spring requires extensive XML or Java-based configuration
- pring Boot uses convention over configuration. Auto-configuration based on classpath dependencies, reducing
  boilerplate code.

**Embedded Servers:**

- Spring applications typically require an external server (e.g., Tomcat, Jetty) to be configured and deployed.
- Spring Boot includes embedded servers (Tomcat, Jetty, or Undertow by default), allowing applications to run
  standalone with a simple java -jar command.

**Starter Dependencies:**

- Spring requires manual dependency management, which can lead to version conflicts.
- Spring Boot provides starter POMs (e.g., spring-boot-starter-web, spring-boot-starter-data-jpa) that bundle
  compatible dependencies, simplifying dependency management.

**Production-Ready Features:**

- Traditional Spring requires manual setup for such features.
    - Spring Boot includes Spring Boot Actuator, offering built-in monitoring, health checks, and metrics for production
      environments.

**Rapid Development:**

- Spring’s manual configuration slows down initial development.
- Spring Boot’s auto-configuration and starters enable faster setup and prototyping.

**Microservices Support:**

- Traditional Spring requires additional configuration for microservices patterns.
- Spring Boot is designed with microservices in mind, offering tools like Spring Cloud for distributed systems.

## Springboot 2.x vs Springboot 3.x?

**Upgrading from Spring Boot 2.x to 3.x requires careful planning:**

- Java 17 Upgrade: Ensure your codebase is compatible with Java 17.
- Jakarta EE Migration: Update imports from javax.* to jakarta.*.
- Dependency Updates: Use compatible versions of libraries (e.g., Hibernate, Spring Data).
- AOT and Native Images: Refactor code to support AOT compilation if targeting GraalVM.
- Testing: Update tests for new APIs and Testcontainers integration.
- Micrometer 1.10+ & Observability API (replaced Sleuth, supports OpenTelemetry).
- Declarative HTTP Interface (replaces RestTemplate/Feign).

## Define Spring Dependency Injection?

- Dependency Injection (DI) is a design pattern used in object-oriented programming to implement Inversion of Control (
  IoC), where the control of creating and managing dependencies is transferred from the class to an external framework
  or container.
- A container (e.g., Spring’s IoC container) manages the creation, configuration, and lifecycle of objects (beans).
- The container injects dependencies into a class at runtime, based on configuration (e.g., annotations, XML, or Java
  config).

## What is Inversion Of Control?

- Instead of a class creating its own dependencies (e.g., using the new keyword), the dependencies are provided ("
  injected") by an external entity, typically a framework like Spring.

## How are HTTP methods Categorized?

- **Safe Methods (e.g., GET, HEAD)** – Only retrieve data, no server changes.
- **Idempotent Methods (e.g., PUT, DELETE)** – Repeated requests have the same effect as one.
- **Non-Idempotent Methods (e.g., POST, PATCH)** – Each request may cause different effects.

## How are HTTP Status Categorized?

-### **HTTP Status Code Categories**

1. **1xx (Informational)** – Request received, continuing process.
2. **2xx (Success)** – Request successfully processed.
3. **3xx (Redirection)** – Further action needed to complete the request.
4. **4xx (Client Error)** – Request contains an error (e.g., invalid data).
5. **5xx (Server Error)** – Server failed to fulfill a valid request.

## What is Statelessness in RESTful Web Services?

- Statelessness means the server does not store any client state between requests. Each request from the client must
  contain all necessary information for the server to process it.
- Key Points:
  ✅ No Server-Side Sessions – The server does not maintain client data (e.g., session state).
  ✅ Client Provides Context – Every request must include all required data (e.g., tokens, IDs).
  ✅ Scalability & Reliability – Servers can handle requests independently, improving performance.
- Instead of server-stored sessions, the client sends an authentication token (JWT) in each request.
- The server validates the token without storing session data.

## What is Addressing in RESTful Web Services?

- Addressing refers to how resources are located and identified on the server using URIs (Uniform Resource Identifiers).

## What are core Components of an HTTP Request?

- **HTTP Method (Verb)**: Defines the action (e.g., GET, POST, PUT, DELETE).
- **URI (Uniform Resource Identifier)**: Identifies the resource (e.g., /api/users/123).
- **HTTP Version**:Specifies protocol version (e.g., HTTP/1.1 or HTTP/2).
- **Request Headers**: Metadata in key-value pairs (e.g., Content-Type: application/json, Authorization: Bearer token).
- **Request Body (Optional)**:Contains data sent to the server (e.g., JSON payload for POST/PUT).

## What are Core Components of an HTTP Response

- **Status Code**: Indicates request success/failure (e.g., 200 OK, 404 Not Found, 500 Server Error).
- **HTTP Version**: Protocol version (e.g., HTTP/1.1).
- **Response Headers**: Metadata (e.g., Content-Type: application/json, Cache-Control: no-store).
- **Response Body (Optional)**:Contains the requested data or error details (e.g., JSON response).

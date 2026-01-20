# ING Bank Web Application Project

## ✨ Overview

The ING Bank Web Application is a robust digital banking platform that supports multiple banking operations through
various modules. It empowers customers to manage their accounts, apply for loans, and view analytics with ease, while
enabling the bank to mitigate risks effectively.

---

## 🔹 Core Modules

### 🏦 1. Account Management Module

**Responsibilities:**

- Handles creation and management of accounts (Savings, Current, Investment, Checking).

- Manages account opening, balance tracking, account closing, and statements.

- Provides transaction history and supports account preferences (e.g., overdrafts, auto-transfers).

**Key Functionalities:**

- **Account Opening:** Validates customer data and performs KYC before account creation.
- **Account Updates:** Enables updates to account details, beneficiary management, and preferences.
- **Real-time Balance:** Tracks balances live and fetches data from DB.
- **Statements:** Generates downloadable account statements.

**Communicates with:**

- Credit and Risk Management Module (to share customer and account details for loan processing).

### 💳 2. Credit and Risk Management Module

**Responsibilities:**

- Manages loan applications, credit scoring, and risk evaluation.

- Makes credit decisions and manages credit limits.

- Monitors credit behavior and initiates corrective actions.

**Key Functionalities:**

- Loan Origination: Collects income, employment, and credit data.

- Credit Scoring: Assesses risk and generates approval/rejection decisions.

- Loan Management: Generates agreements, repayment schedules, and disburses funds.

- Credit Monitoring: Tracks repayments, restructures loans, and flags defaults.

**Communicates with:**

- Account Management Module (for account data and initiation of loan process).
- External Scoring Services (for credit score and risk metrics).

### 📊 3. Reporting and Analytics Module

**Responsibilities:**

- Provides actionable insights using customer and transaction data.

- Offers dashboards, visualizations, performance metrics, and trends.

**Key Functionalities:**

- **Data Reporting:** Generates financial reports.
- **Analytics:** Tracks performance, transaction volumes, and customer behavior.
- **Trend Monitoring:** Helps banks forecast and make strategic decisions.

# 🔄 Module Flow

## Customer Onboarding

- Customers interact with Account Management Module to open savings, current, or investment accounts.
- The system performs KYC and stores account info.

## Account Management

- Customers can update details, view balances, and generate statements.
- The system tracks account usage and stores transaction history.

## Loan Application

- The Account Module forwards required account info to Credit and Risk Management Module.
- The credit service collects income, employment, and credit history from the user.

## Loan Processing

- Credit scoring and risk assessment are performed.
- Based on the evaluation, the system may approve, reject, or request additional info.
- If approved, the service handles origination, repayment schedule, and fund disbursement.

## Credit Monitoring

- Customer behavior is monitored continuously.
- Risk levels are re-evaluated, credit limits adjusted, and early signs of default are flagged.

## Reports & Insights

- All transaction and loan data are processed by the Reporting and Analytics Module.
- Insights and metrics are visualized for better decision-making.

## 🔧 Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA (MySQL + MongoDB)
- Spring Security (JWT + OAuth2)
- Eureka Server (Service Registry)
- API Gateway (Spring Cloud Gateway)
- Config Server (Centralized Configuration)
- Feign Client, Kafka, RabbitMQ (Inter-Service Communication)
- Resilience4j (Circuit Breaker)
- Sleuth + Zipkin (Distributed Tracing)
- Prometheus + Grafana (Metrics)
- Splunk (Logging & Monitoring)

## 📆 Communication Summary

| Module                       | Communicates With                     | Purpose                                    |
|------------------------------|---------------------------------------|--------------------------------------------|
| **Account Management**       | Credit & Risk Module                  | Create/Manages merchant accounts           |
| **Credit & Risk Management** | Account Module, External Scoring APIs | Evaluate creditworthiness, track repayment |
| **Reporting & Analytics**    | Account Module, Credit Module         | Generate financial reports and insights    |

---

*This document outlines the features, architecture, and workflow of the ING Bank Web Application Project.*

## Biggest Challenge

### Scenario: Payment Service Latency Issue

*In a bank's microservices architecture, there is a payment service responsible for processing financial transactions.
One day, the team starts receiving complaints from customers about delays in processing payments. As the lead developer
of the payment service, you need to investigate and resolve this issue.*

**Technical Details:**

**Microservices Architecture:**

- The application is designed using a microservices architecture, where different functionalities are divided into
  separate services to achieve modularity and scalability. The payment service is one of the microservices that
  interacts with other services like user authentication, transaction history, and account balance.

**Service-to-Service Communication:**

- The payment service communicates with other services through RESTful APIs or messaging systems like Kafka. It relies
  on these interactions to complete payment transactions successfully.

**Distributed Tracing:**

- The application utilizes distributed tracing to monitor the flow of requests between microservices. This helps in
  identifying performance bottlenecks and tracking the entire transaction journey across services.

**Critical Scenario:**

- Upon investigation, you find that the payment service's latency has increased significantly, leading to delays in
  processing customer payments. You also observe that this issue is not consistent and occurs intermittently, making it
  harder to identify the root cause.

### Troubleshooting and Resolution:

**Log Analysis:** You start by analyzing the application logs and tracing the request flow to understand which part of
the payment service is causing the latency. The logs might reveal errors, long-running database queries, or
network-related issues.

**Distributed Tracing:**

- Since the application uses distributed tracing, you follow the transaction flow across different microservices. This
  helps you identify any delays in communication between services or pinpoint which service is causing the delay.

**Performance Monitoring:**

- You set up performance monitoring tools to track the response times of different microservices. This helps you
  identify patterns and narrow down the problematic service.

**Load Testing:**

- To simulate real-world scenarios, you perform load testing on the payment service and other related services. This
  helps you identify if the latency is caused by increased traffic or insufficient resources.

**Database Optimization:**

- Upon analyzing the database queries, you notice that one particular query in the payment service is taking longer than
  usual. You optimize the query by adding indexes or restructuring the data model to improve its performance.

**Caching:**

- Since payment service data might not change frequently, you implement caching mechanisms to store and retrieve common
  data, reducing the need for frequent database queries.

**Circuit Breaker Pattern:**

- To handle intermittent failures in service-to-service communication, you implement the Circuit Breaker pattern. This
  prevents cascading failures and provides fallback mechanisms when a service is unavailable.

**Scalability:**

- You analyze the system's scalability and ensure that it can handle increased traffic during peak hours. This might
  involve horizontal scaling of services or using container orchestration tools like Kubernetes.

**Error Handling and Retry Mechanism:**

- You enhance the error handling and retry mechanisms in the payment service to handle temporary failures gracefully and
  automatically retry failed requests.

**Continuous Monitoring and Alerting:**

- Finally, you set up continuous monitoring and alerting for critical metrics. This ensures that you are proactively
  notified of any potential issues, allowing you to take quick action before they impact customers.

*By implementing these solutions and continuously monitoring the application, you can effectively resolve the latency
issue in the payment service and ensure smooth payment processing for bank customers. As a result of your exceptional
work and problem-solving skills, you receive an award for your contributions to the successful resolution of this
critical scenario in the bank domain project.*
̥

# Global Clearing Connector (GC2) – Middleware Payment Gateway

## ✨ Overview

The **Global Clearing Connector (GC2)** is a stateless, high-throughput middleware application designed to enable
seamless integration between payment processors (like PLUTUS) and clearing systems. It supports diverse protocols and
message formats—JSON over Kafka and XML/SWIFT/ISO 20022 over Message Queues.

GC2 performs:

- Protocol mediation
- Data transformation
- Schema validation
- Flow-based routing

It ensures reliable, low-latency communication, while preserving auditability and traceability through Oracle DB
persistence.

---

## 🔹 Key Modules

### ⚙️ 1. Core Module

**Responsibilities:**

- Handles message processing logic using **action classes**
- Implements transformation, enrichment, validation, and error handling
- Uses **Chain of Responsibility** pattern with dynamic JSON configurations

**Communicates with:**

- **Orchestration Module** (to load configurations)
- Kafka and MQ (to transmit transformed messages)

---

### 📝 2. End-to-End (E2E) Testing Module

**Responsibilities:**

- Simulates production flows using embedded Kafka, MQ, and HSQLDB
- Uses **Cucumber** with Gherkin feature files for scenario testing
- Validates full message journeys (from ingress to delivery)

**Includes:**

- SQL scripts for DB state management
- Step definitions for each payment flow (IRCT, RRCT, BRDT)

---

### 🌐 3. Orchestration Module

**Responsibilities:**

- Stores and manages configuration rules for routing, system connectivity, and flow behavior
- Defines how different message types are processed and routed based on origin and destination

**Communicates with:**

- Core Module (supplies configurations and flow mappings)

---

## 🌐 Supported Payment Flows

### ✉️ Inward Real-Time Credit Transfer (IRCT)

- JSON message from **PLUTUS** via Kafka
- Transformed to XML using **C24 mapping jars**
- Routed to appropriate clearing body via MQ

### ↩️ Return Real-Time Credit Transfer (RRCT)

- XML return message from Clearing Body via MQ
- Transformed, enriched, validated
- Sent to **PLUTUS** via Kafka

### 📊 Batch Report Distribution Transfer (BRDT)

- Non-payment flow
- Batch reports/data sent by clearing to external systems
- GC2 transforms and routes to respective systems

---

## 💡 Daily Responsibilities

1. Coordinate with BAs to gather field-level mapping specs
2. Create new **C24 transformation projects** using input/output XSDs
3. Map fields and add Java logic as per business rules
4. Generate and test JAR with sample data
5. Upload transformation JAR to **JFrog Artifactory**, integrate via Maven
6. Use **Connect Aide** to generate boilerplate XML config for brokers and DB
7. Configure or reuse action classes based on flow logic
8. Write and validate Cucumber test cases using HSQLDB and simulators
9. Perform end-to-end testing in test environments
10. Debug schema/validation issues with BAs and apply necessary fixes

---

## ⚠️ Challenges Faced

- Handling schema failures due to unexpected input formats from external systems
- Frequent mapping and logic rework due to evolving business requirements

---

## 🔧 Tech Stack

- **Java**
- **Spring Boot**
- **Kafka (Producer/Consumer)**
- **ActiveMQ / Message Queues**
- **C24 Integration** (for XML/ISO20022 mapping)
- **Oracle DB**
- **Maven + JFrog Artifactory**
- **Connect Aide Tool**
- **JUnit / Mockito**
- **Cucumber + Gherkin + HSQLDB** (for testing)

---

## 📆 Communication Summary

| Component         | Communicates With                | Purpose                                                   |
|-------------------|----------------------------------|-----------------------------------------------------------|
| **Core Module**   | Orchestration, Kafka, MQ         | Processes messages using action chains                    |
| **Orchestration** | Core Module                      | Supplies dynamic configurations                           |
| **E2E Module**    | Kafka, MQ, HSQLDB                | Simulates real flows for full end-to-end testing          |
| **GC2 (Overall)** | PLUTUS, Clearing Bodies, Ext Sys | Bridges message exchange via protocol and format handling |

---

*This document summarizes the architecture, workflow, and development activities involved in the GC2 middleware
application.*

# Overview of the Project:

- At J.P. Morgan, I worked on the Global Clearing Connector (GC2) application, a *
  *<span style="background-color: #FFFF00">middleware payment gateway</span>** designed to
  facilitate seamless integration between internal **<span style="background-color: #FFFF00">payment processors</span>
  ** (such as PLUTUS) and external **<span style="background-color: #FFFF00">clearing systems</span>**.
- The application supports multiple protocols and message formats—handling JSON over Kafka for internal communication
  and XML, SWIFT, or ISO 20022 formats over Message Queues for external systems.

## Core Responsibilities of the Application:

**GC2 is responsible for:**

- **<span style="background-color: #FFFF00">Protocol Mediation</span>**: Bridging communication between Kafka and
  traditional MQ-based systems.
- **<span style="background-color: #FFFF00">Data Transformation</span>**: Converting messages between formats (e.g.,
  JSON to XML).
- **<span style="background-color: #FFFF00">Schema Validation</span>**: Ensuring incoming and outgoing messages adhere
  to the expected structure.
- **<span style="background-color: #FFFF00">Flow-Based Routing</span>**: Determining message paths based on business
  rules and message metadata.

All message activity is persisted in Oracle DB to maintain full auditability and traceability, ensuring operational
transparency and compliance.

## Modules I Contributed To:

**1. Core Module:**

- This module handles the **<span style="background-color: #FFFF00">primary message processing workflow</span>**.
- It uses **<span style="background-color: #FFFF00">configurable action classes</span>** to
  perform **<span style="background-color: #FFFF00">T</span>**ransformation, **<span style="background-color: #FFFF00">
  E</span>**nrichment, **<span style="background-color: #FFFF00">V</span>**alidation,
  and **<span style="background-color: #FFFF00">E</span>**rror handling steps.
- The design follows a **<span style="background-color: #FFFF00">Chain of Responsibility</span>** pattern driven by
  external **<span style="background-color: #FFFF00">JSON-based configurations</span>**.
- The Core Module **<span style="background-color: #FFFF00">interacts</span>** with Kafka, MQ systems, and the
  Orchestration Module to carry out end-to-end processing.

**2. Orchestration Module:**

- This component **<span style="background-color: #FFFF00">manages all configurations</span>** required
  for **<span style="background-color: #FFFF00">R</span>**outing, **<span style="background-color: #FFFF00">C</span>**
  onnectivity, and **<span style="background-color: #FFFF00">F</span>**low behavior.
- It defines how **<span style="background-color: #FFFF00">each message type</span>** should be handled, based on
  source, destination, and business rules.
- The Core Module relies on this service for **<span style="background-color: #FFFF00">retrieving processing
  logic</span>** dynamically at runtime.

**3. End-to-End (E2E) Testing Module:**

- This module provides an environment to simulate production-like message flows using embedded Kafka, MQ, and HSQLDB.
- It is implemented using Cucumber with Gherkin feature files to define **<span style="background-color: #FFFF00">test
  scenarios</span>**.
- The framework supports end-to-end testing for **<span style="background-color: #FFFF00">payment flows and DB state
  management</span>** through **<span style="background-color: #FFFF00">SQL scripts and customized step
  definitions</span>**.

## Supported Payment Flows:

**IRCT (Inward Real-Time Credit Transfer):**

- Receives a JSON message from PLUTUS via Kafka, transforms it into XML using C24 mapping libraries, and routes it to
  the appropriate clearing house via MQ.

**RRCT (Return Real-Time Credit Transfer):**

- Receives an XML message from the clearing body via MQ, processes it (transformation, validation, enrichment), and
  sends a JSON response to PLUTUS via Kafka.

**BRDT (Batch Report Distribution Transfer):**

- Handles non-payment related batch data or reports from clearing systems. The data is processed and routed to external
  systems as per defined configurations.

## Challenges Faced:

**Schema Validation Failures:**

- Occasionally, external systems sent unexpected or invalid message formats, which led to schema validation issues. We
  implemented enhanced error handling and logging mechanisms to capture and isolate these problems quickly.

**Frequent Mapping and Logic Updates:**

- As business requirements evolved, there were regular changes to transformation mappings and routing logic. This
  required agile development practices and close collaboration with business analysts to ensure accurate and timely
  updates.

## Biggest Challenge:

### Biggest Challenge in GC2 Project

- One of the biggest challenges I faced in the GC2 project was a critical performance degradation during the processing
  of IRCT (Instant Retail Credit Transfer) messages. Under normal conditions, each message was processed in around 50ms,
  but during peak hours, this spiked to 500ms or more, causing a massive message backlog and pushing us dangerously
  close to
  SLA violations for real-time payments.

### 🔍 Root Cause Analysis:

- We started by analyzing metrics on Grafana and Kibana, and saw unusually high CPU usage, Kafka consumer lag, and
  frequent full garbage collections.

Using **JProfiler,** we discovered that nearly 45% of CPU time was spent just on XML Schema validations.
It turned out that:

- XML schemas were being compiled on every message instead of being cached.
- A recent config change had disabled schema caching in production.
- Also, the schema validators were not thread-safe, leading to thread contention under load.

### 🛠️ Resolution:

*To address the issue quickly, we:*

- Enabled schema caching via a hotfix.
- Scaled the validation thread pool temporarily to reduce system pressure.
  *Then, for the long-term fix, we:*
- Built a thread-safe schema caching layer using ConcurrentHashMap to store compiled schemas.
- Refactored the validation logic to use ThreadLocal validators, preventing contention.
- Preloaded all required schemas at application startup.
- Added automated tests and monitoring for schema cache hit/miss ratios and compilation events.

### ✅ Outcome:

- Latency dropped from 500ms to around 40ms — an 87% improvement.
- We were able to handle over 800+ transactions per second without lag.
- Kafka backlogs cleared in under 2 hours post-deployment.
- Schema compilation operations reduced from 1000/sec to just 5/sec during warm-up.

### 📌 Key Takeaway:

- This incident was a turning point in how we approach performance tuning. It highlighted the importance of proper
  caching strategies, thread safety, and configuration validation in production environments. We’ve since added
  performance gates in our CI/CD pipeline and established clear runbooks for handling similar schema-related issues.

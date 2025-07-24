# IQ WorldPay Project

## ✨ Overview

IQ WorldPay is an online, web-based platform developed by FIS that allows merchants and consumers to manage
payment-related operations efficiently. It supports user registration, merchant onboarding, card management, payment
processing, transaction logging, and notifications.

It enables:

- Seamless access to transaction data
- Adaptable reporting
- Proactive financial notifications to merchants

IQ is a Payment Service Provider.

---

## 🔹 Core Services

### ✉️ 1. User Service

**Responsibilities:**

- Manages user registration and login
- Allows profile updates and settings changes
- Handles roles and permissions (merchant/consumer)

**Communicates with:**

- Account Management Service (to create merchant accounts)

---

### 📄 2. Account Management Service (AMS)

**Responsibilities:**

- Manages merchant accounts and onboarding
- Creates, updates, and stores merchant account information

**Communicates with:**

- User Service (for user validation)
- Transaction History Service (to log account activities)

---

### 📞 3. Cards Service

**Responsibilities:**

- Manages card lifecycle (issue, activate, update, deactivate)
- Handles lost/stolen cards and fraud detection
- Supports payments using cards

**Communicates with:**

- Payment Service (for card-based transaction processing)

---

### 💳 4. Payment Service

**Responsibilities:**

- Processes all types of payment transactions
- Supports multiple payment methods (credit/debit cards)
- Ensures secure transaction processing and compliance

**Communicates with:**

- Cards Service (for card payments)
- Transaction History Service (for logging payments)

---

### 📊 5. Transaction History Service

**Responsibilities:**

- Maintains a record of all transactions
- Provides reporting and analytics features
- Triggers notifications (e.g., large/failed transactions, statements)

**Communicates with:**

- Payment Service (for transaction logs)
- Account Management Service (for account event logs)

---

## 🔄 Service Flow

1. **User Registration**
    - User registers via **User Service**
    - Info is validated and stored

2. **Merchant Onboarding**
    - If the user is a merchant, **User Service** contacts **AMS**
    - AMS onboards and manages merchant account

3. **Card Management**
    - Cards requested via **Cards Service**
    - Cards are issued and managed securely

4. **Payment Processing**
    - Payments initiated through **Payment Service**
    - If cards are used, **Cards Service** is involved
    - Payment Service handles authorization and settlement

5. **Transaction Logging**
    - **Payment Service** sends transaction data to **Transaction History Service**
    - Transaction History logs, reports, and notifies merchants

---

## 🔧 Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Microservices Architecture
- RestTemplate
- Servlets / Spring MVC
- IDM DB2.
- JUnit / Mockito

---

## 📆 Communication Summary

| Service                 | Communicates With                  | Purpose                                |
|-------------------------|------------------------------------|----------------------------------------|
| **User Service**        | Account Management Service         | Create merchant accounts               |
| **AMS**                 | User Service, Transaction History  | Validate users, log account activities |
| **Cards Service**       | Payment Service                    | Process card payments                  |
| **Payment Service**     | Cards Service, Transaction History | Process and log transactions           |
| **Transaction History** | Payment Service, AMS               | Log and report transactions            |

---

*This document summarizes the key components and workflows of the IQ WorldPay project.*


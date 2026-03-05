# 🎫 Booking Service System

A microservices-based booking system built with Spring Boot that manages event bookings, orders, and ticket inventory with real-time capacity tracking.

## 📋 Features

- **Booking Management**: Create bookings for events with real-time capacity validation
- **Order Processing**: Asynchronous order processing via Kafka messaging
- **Inventory Management**: Real-time event capacity tracking and reduction
- **Microservices Architecture**: Three independent services communicating via Kafka
- **PostgreSQL Database**: Persistent data storage for customers, orders, and events
- **RESTful APIs**: Clean REST endpoints for booking and inventory management

## 🏗️ Architecture

The system consists of three microservices:

```
┌─────────────────────────────────────────────────────────────────┐
│                                                                 │
│  Booking Service (8081)                                        │
│  ├─ Validates customer & event capacity                        │
│  └─ Sends booking events to Kafka                             │
│           │                                                    │
│           ├──→ Kafka Topic "booking"                          │
│                 │                                              │
│                 └──→ Order Service (8082)                     │
│                      ├─ Creates orders in database            │
│                      └─ Calls Inventory Service               │
│                           │                                    │
│                           └──→ Tickets System (8080)          │
│                               ├─ Reduces capacity             │
│                               └─ Updates event inventory      │
│                                                                │
│  Database: PostgreSQL (localhost:5432)                        │
│  Message Broker: Kafka (localhost:9092)                       │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## 📦 Services

| Service | Port | Description |
|---------|------|-------------|
| **Booking Service** | 8081 | Handles booking requests and events |
| **Order Service** | 8082 | Processes bookings and creates orders |
| **Tickets System** | 8080 | Manages event inventory and capacity |

## 🚀 Quick Start

### Prerequisites

- Java 21+
- PostgreSQL 12+
- Kafka (locally running)
- Gradle 7.0+


Services should be running on:
- Booking Service: http://localhost:8081
- Order Service: http://localhost:8082
- Tickets System: http://localhost:8080

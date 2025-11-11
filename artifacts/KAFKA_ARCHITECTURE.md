# Kafka Architecture & SeismicDataPipeline Integration

A comprehensive guide to understanding how Apache Kafka works and how the SeismicDataPipeline project integrates with a Kafka cluster running in Podman/Docker.

---

## Table of Contents

1. [Kafka Architecture Overview](#kafka-architecture-overview)
2. [Your Docker Compose Setup](#your-docker-compose-setup)
3. [How Your Code Works](#how-your-code-works)
4. [Topics Explained](#topics-explained)
5. [Brokers Explained](#brokers-explained)
6. [Where Is Data Stored](#where-is-data-stored)
7. [Data Flow Diagram](#data-flow-diagram)
8. [Step-by-Step Example](#step-by-step-example)
9. [Quick Reference](#quick-reference)
10. [Next Steps & Improvements](#next-steps--improvements)

---

## Kafka Architecture Overview

### The Big Picture (Simplified)

Think of Kafka as a **distributed message queue**:

- **Broker** — a Kafka server that stores and serves messages.
- **Topic** — a category/channel for messages (e.g., `"earthquake_data"`).
- **Partition** — a shard of a topic that enables parallel processing and scalability.
- **Offset** — a position/sequence number for messages within a partition.
- **Consumer Group** — multiple consumers reading from the same topic, coordinating who reads what.
- **Replica** — a copy of partition data distributed across brokers for fault tolerance.

### Core Concepts

#### Topics
- A named stream of messages.
- Multiple producers can write to the same topic.
- Multiple consumers can read from the same topic.
- Data is organized by **partitions** within a topic.

(NOT persisted unless Docker volume used)

---

## Diagrams & Visuals

Below are the key diagrams that show how data flows between the producer, the Kafka broker, and the consumer. 

### Producer → Topic → Consumer

```
┌──────────────────────────────────────┐
│        ProducerApp (Java)            │
│  1. Connect to localhost:9092        │
│  2. Fetch earthquake JSON from USGS  │
│  3. Send to topic "earthquake_data"  │
│  4. Record appended at offset 0      │
└──────────────────┬───────────────────┘
                   │
                   ↓
        ┌──────────────────────┐
        │  Kafka Broker        │
        │                      │
        │  Topic: earthquake_data
        │  Partition 0:        │
        │  [msg@offset=0] ◄────┼─ ProducerApp sends
        │  [msg@offset=1]      │
        │  ...                 │
        │                      │
        │  Log file stored in  │
        │  /tmp/kraft-combined-logs
        └──────────┬───────────┘
                   │
                   ↓
┌──────────────────────────────────────┐
│        ConsumerApp (Java)            │
│  1. Connect to localhost:9092        │
│  2. Join group "sdp-consumer"        │
│  3. Subscribe to "earthquake_data"   │
│  4. Poll and read message at offset 0
│  5. Print message                    │
└──────────────────────────────────────┘
```

Notes about the diagram:
- The producer writes a message with a topic, key and JSON value. With `KAFKA_AUTO_CREATE_TOPICS_ENABLE=true`, the topic will be created automatically on first write.
- The broker appends messages to partition log files stored at the path defined by `KAFKA_LOG_DIRS` (`/tmp/kraft-combined-logs` inside the container).
- The consumer polls Kafka and reads messages starting at the offset indicated by its committed offset or the `auto.offset.reset` policy.

### Data Flow (Containerized view)

```
┌────────────────────────────────────────────────────────────────┐
│              Your Local Machine (macOS)                        │
│                                                                │
│  ┌────────────────────────────────────────────────────────┐    │
│  │          Docker Container (Kafka Broker)               │    │
│  │                                                        │    │
│  │  ┌──────────────────────────────────────────────┐      │    │
│  │  │  Kafka Broker (Node ID 1, KRaft Mode)        │      │    │
│  │  │  listening on :9092 (PLAINTEXT)              │      │    │
│  │  │            and :9093 (CONTROLLER)            │      │    │
│  │  │                                              │      │    │
│  │  │  ┌────────────────────────────────────────┐  │      │    │
│  │  │  │ Topic: earthquake_data                 │  │      │    │
│  │  │  │ Partition 0:                           │  │      │    │
│  │  │  │ [msg@0] [msg@1] [msg@2] ... [msg@N]    │  │      │    │
│  │  │  │ ↑                                      │  │      │    │
│  │  │  │ (Appended in order)                    │  │      │    │
│  │  │  └────────────────────────────────────────┘  │      │    │
│  │  │                                              │      │    │
│  │  │  Log files stored in:                        │      │    │
│  │  │  /tmp/kraft-combined-logs                    │      │    │
│  │  │  (NOT persisted unless Docker volume used)   │      │    │
│  │  └──────────────────────────────────────────────┘      │    │
│  └────────────────────────────────────────────────────────┘    │
│                ↑                          ↑                    │
│           ProducerApp                ConsumerApp               │
│        (Java process)              (Java process)              │
│        localhost:9092              localhost:9092              │
│        "Send earthquakes"          "Read earthquakes"          │
└────────────────────────────────────────────────────────────────┘
```

Relevant information:
- The container path shown (`/tmp/kraft-combined-logs`) is where messages are written inside the Kafka container. If you want data to survive container removal, mount this path to a Docker volume (see `STORAGE_PERSISTENCE.md`).
- Ports `9092` (client) and `9093` (controller) are mapped to the host; clients use `localhost:9092` to connect.

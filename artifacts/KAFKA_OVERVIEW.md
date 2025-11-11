# Kafka Overview

This short document explains core Kafka concepts and how they map to the SeismicDataPipeline project.

## Core Concepts

- Broker: a Kafka server process that stores and serves messages.
- Topic: a named stream of messages (e.g., `earthquake_data`).
- Partition: a shard of a topic enabling parallel processing.
- Offset: a position number for messages within a partition.
- Consumer Group: a set of consumers coordinating consumption of a topic.

## How these map to the project

- Topic used: `earthquake_data` — where ProducerApp writes and ConsumerApp reads.
- Single broker (Node ID 1) — no replication; simple development setup.
- KRaft mode (no ZooKeeper) — broker and controller run in one container.

## Single-responsibility guidance

For maintainability, production code should keep small, focused classes (fetcher, publisher, runner). See `PRODUCER_CONSUMER.md` for suggested refactor ideas and tests.

---

Generated: November 11, 2025

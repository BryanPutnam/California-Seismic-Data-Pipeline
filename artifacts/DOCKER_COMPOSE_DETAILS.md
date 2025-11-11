# Docker Compose — Kafka Configuration Details

This file explains the `docker/docker-compose.yml` Kafka service and its important environment variables used by this project.

## Key settings (from docker-compose.yml)

- image: `confluentinc/cp-kafka:7.4.0` — Confluent Kafka image.
- ports:
  - `9092:9092` — PLAINTEXT listener for clients (producers/consumers).
  - `9093:9093` — CONTROLLER listener (internal, cluster coordination).

### Important environment variables

- `KAFKA_KRAFT_MODE: 'true'`
  - Use KRaft controller (no ZooKeeper required).

- `KAFKA_PROCESS_ROLES: 'broker,controller'`
  - This node acts as both a broker and controller.

- `KAFKA_NODE_ID: 1`
  - Unique broker id for this node.

- `KAFKA_CONTROLLER_QUORUM_VOTERS: '1@localhost:9093'`
  - KRaft cluster voters (single-node cluster here).

- `KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092`
  - Address advertised to clients; your code uses `localhost:9092`.

- `KAFKA_LOG_DIRS: /tmp/kraft-combined-logs`
  - Where Kafka stores its log segments *inside the container*.

- `KAFKA_AUTO_CREATE_TOPICS_ENABLE: 'true'`
  - Kafka will create topics on first write.

### Notes & recommendations

- In development, KRaft single-node is simple. For production, run multiple broker nodes and persistent volumes.
- By default the log directory is ephemeral unless you mount a Docker volume. To persist data across restarts, add a volume mapping to `/tmp/kraft-combined-logs`.

---

See `STORAGE_PERSISTENCE.md` for volume examples and persistence guidance.

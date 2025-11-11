# Storage & Persistence for Kafka Data

This file explains where Kafka stores data in your setup and how to make it persistent across container restarts.

## Where data is stored now

- Kafka writes messages to segment log files in the directory set by `KAFKA_LOG_DIRS`.
- In your compose file that is `/tmp/kraft-combined-logs` inside the container.
- Without a Docker volume, the directory lives in the container filesystem (ephemeral):
  - `docker-compose down` or container removal deletes data.

## Make data persistent (recommended for dev)

Add a Docker volume mapping in `docker-compose.yml` for the `kafka` service:

```yaml
services:
  kafka:
    # ... existing config ...
    volumes:
      - kafka-data:/tmp/kraft-combined-logs

volumes:
  kafka-data:
```

This will create a named Docker volume `kafka-data` and store the Kafka logs there. Data will survive container restarts and `docker-compose down` (unless you `docker volume rm kafka-data`).

## Retention & housekeeping

You can control how long Kafka keeps data using environment variables:

- `KAFKA_LOG_RETENTION_HOURS` — number of hours to keep messages (e.g., 168 for 7 days).
- `KAFKA_LOG_RETENTION_BYTES` — maximum total size for logs (per topic/partition config also possible).
- `KAFKA_LOG_SEGMENT_BYTES` — size before rotating to a new segment file.

Example:
```yaml
environment:
  KAFKA_LOG_RETENTION_HOURS: 168
  KAFKA_LOG_SEGMENT_BYTES: 1073741824  # 1 GB
```

## Backups

- For production, back up the broker data store or use external replication/mirroring.
- For development, Docker volumes are usually sufficient.

---

See `DOCKER_COMPOSE_DETAILS.md` for the exact compose variables used in this project.

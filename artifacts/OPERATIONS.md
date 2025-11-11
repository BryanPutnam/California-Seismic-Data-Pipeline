# Operations & Commands — Kafka (SeismicDataPipeline)

Quick commands and operational tips for running and troubleshooting the Kafka service and your Java clients.

## Docker commands

Start Kafka (detached):

```bash
cd /path/to/SeismicDataPipeline
docker-compose -f docker/docker-compose.yml up -d
```

View the broker logs:

```bash
docker logs -f kafka
```

Stop and remove containers:

```bash
docker-compose -f docker/docker-compose.yml down
```

Open a shell inside the Kafka container:

```bash
docker exec -it kafka bash
```

## Useful Kafka CLI commands (run inside container or use confluent tools)

List topics:

```bash
kafka-topics --bootstrap-server localhost:9092 --list
```

Describe a topic (partitions, replicas):

```bash
kafka-topics --bootstrap-server localhost:9092 --describe --topic earthquake_data
```

Read messages from the beginning:

```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic earthquake_data --from-beginning
```

Delete a topic:

```bash
kafka-topics --bootstrap-server localhost:9092 --delete --topic earthquake_data
```

Check consumer group offsets:

```bash
kafka-consumer-groups --bootstrap-server localhost:9092 --group sdp-consumer --describe
```

## Maven commands (run your apps locally)

Run ProducerApp:

```bash
mvn exec:java -Dexec.mainClass="com.sdp.producer.ProducerApp"
```

Run ConsumerApp:

```bash
mvn exec:java -Dexec.mainClass="com.sdp.consumer.ConsumerApp"
```

Run tests:

```bash
mvn -U clean test
```

## Monitoring & UI

Optional: add Kafka UI (e.g., [Kafka UI](https://github.com/provectus/kafka-ui)) to visualize topics, messages and consumer groups. See `DOCKER_COMPOSE_DETAILS.md` for a sample snippet.

## Next steps checklist

- [ ] Add Docker volume for data persistence (see `STORAGE_PERSISTENCE.md`).
- [ ] Consider enabling `auto.offset.reset=earliest` in test consumers to read historical messages.
- [ ] Add schema registry and Avro serializers for schema validation.
- [ ] Add monitoring (Prometheus + Grafana).

---

Generated: November 11, 2025

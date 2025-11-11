# Producer & Consumer: How the Code Interacts with Kafka

This document describes the flow inside `ProducerApp` and `ConsumerApp`, what they do, and suggested refactors to improve testability and SRP.

## ProducerApp (high level)

Flow:
1. Configure KafkaProducer with `bootstrap.servers = localhost:9092`.
2. Fetch earthquake JSON from USGS.
3. Create `ProducerRecord(topic="earthquake_data", key="quake-1", value=json)`.
4. Call `producer.send(record).get()` to synchronously send and wait for ack.
5. Print metadata (partition, offset). Close producer.

Notes:
- `producer.send(...).get()` blocks until the broker acknowledges. Consider async with callbacks or retries for production.
- Topic auto-creation is enabled; producing to a non-existent topic will create it.

### Suggested refactor (minimal, testable)

- Extract responsibilities into small classes:
  - `EarthquakeFetcher` interface + `HttpEarthquakeFetcher` implementation
  - `KafkaPublisher` interface + `KafkaPublisherImpl`
  - `ProducerRunner` class that coordinates fetcher → publisher
  - `Config` class for wiring (topic, bootstrap servers, feed URL)

- Keep `ProducerApp.main()` as thin wiring code.
- Use dependency injection for easier unit testing (swap real implementations with fakes/mocks).

## ConsumerApp (high level)

Flow:
1. Configure KafkaConsumer with `bootstrap.servers = localhost:9092`, `group.id = sdp-consumer`.
2. `consumer.subscribe(Collections.singletonList("earthquake_data"))`.
3. Loop and call `consumer.poll(Duration.ofMillis(1000))`.
4. Print each message `record.value()`.

Notes:
- By default, `auto.offset.reset=latest`, so a new consumer group will start at the end and miss past messages. Use `earliest` to read from the beginning.
- Consumers should handle rebalance and graceful shutdown for production use.

## Testing suggestions

- `ProducerRunner` unit tests: supply fake `EarthquakeFetcher` (deterministic JSON) and fake `KafkaPublisher` (capture send calls) and assert interactions.
- `KafkaPublisherImpl` tests: use Mockito to mock `KafkaProducer` or run an embedded Kafka (Testcontainers) for integration tests.
- `ConsumerApp` integration tests: use `--from-beginning` or set `auto.offset.reset=earliest` and a short-running consumer in test harness.

---

See `KAFKA_OVERVIEW.md` and `DOCKER_COMPOSE_DETAILS.md` for architectural context and runtime config.

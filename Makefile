# =========================================================
# Kafka Data Pipeline - Makefile
# =========================================================

PROJECT_NAME = kafka-project
SRC_DIR = src/main/java
BIN_DIR = bin
PACKAGE = com.sdp
TOPIC = water_data

DOCKER_COMPOSE = docker compose

# =========================================================
# BUILD TARGETS
# =========================================================

# Compile all Java files into bin/
build:
	@echo ">> Building Java source files..."
	@mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) $(shell find $(SRC_DIR) -name "*.java")

# Clean compiled files
clean:
	@echo ">> Cleaning build artifacts..."
	rm -rf $(BIN_DIR)

# =========================================================
# DOCKER / KAFKA MANAGEMENT
# =========================================================

# Start Kafka via Docker Compose
up:
	@echo ">> Starting Kafka cluster..."
	$(DOCKER_COMPOSE) up -d

# Stop Kafka containers
down:
	@echo ">> Stopping Kafka cluster..."
	$(DOCKER_COMPOSE) down

# Restart Kafka
restart:
	@$(MAKE) down
	@$(MAKE) up

# =========================================================
# KAFKA TOPIC MANAGEMENT
# =========================================================

create-topic:
	@echo ">> Creating topic: $(TOPIC)"
	$(DOCKER_COMPOSE) exec kafka kafka-topics --create \
		--topic $(TOPIC) \
		--bootstrap-server localhost:9092 \
		--partitions 1 \
		--replication-factor 1 || true

list-topics:
	$(DOCKER_COMPOSE) exec kafka kafka-topics --list --bootstrap-server localhost:9092

delete-topic:
	@echo ">> Deleting topic: $(TOPIC)"
	$(DOCKER_COMPOSE) exec kafka kafka-topics --delete --topic $(TOPIC) --bootstrap-server localhost:9092 || true

# =========================================================
# PRODUCER / CONSUMER RUNNERS
# =========================================================

run-producer:
	@echo ">> Running Kafka producer..."
	java -cp $(BIN_DIR) $(PACKAGE).producer.ProducerApp

run-consumer:
	@echo ">> Running Kafka consumer..."
	java -cp $(BIN_DIR) $(PACKAGE).consumer.ConsumerApp

# =========================================================
# TESTS
# =========================================================

test:
	@echo ">> Running all tests..."
	@javac -cp $(BIN_DIR):lib/* -d $(BIN_DIR) $(shell find tests -name "*.java")
	@java -cp $(BIN_DIR):lib/* org.junit.runner.JUnitCore com.sdp.tests.AllTests

.PHONY: build clean up down restart create-topic list-topics delete-topic run-producer run-consumer test

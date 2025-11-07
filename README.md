# California Seismic Data Pipeline

A real-time earthquake data processing pipeline that will stream data from the USGS Earthquake API using Apache Kafka.

## Project Goals
- Stream real-time earthquake data from USGS API
- Process and filter California-specific seismic events
- Demonstrate a scalable data pipeline using Apache Kafka
- Provide real-time analytics capabilities for seismic data

## Current Status: Work in Progress
This project is in early development. Currently implemented:
- Basic Kafka producer/consumer setup
- Local Docker environment for Kafka
- Initial test framework with JUnit

Coming soon:
- USGS API integration
- Real-time data streaming
- Enhanced data processing
- Analytics dashboard
- Expanded test coverage

## Tech Stack
- Java
- Apache Kafka
- Docker
- Maven
- JUnit (testing)

## Prerequisites
- Java 11 or later
- Docker/Podman and Docker Compose
- Maven

## Project Structure
```
├── docker/               # Docker compose files for Kafka
├── schemas/             # Avro schemas for earthquake data
├── src/
│   └── main/java/com/sdp/
│       ├── consumer/    # Kafka consumer implementation
│       ├── producer/    # Kafka producer implementation
│       └── utils/       # Utility classes
└── tests/              # Test files
```

## Development Workflow
1. Producer polls USGS API for earthquake data (coming soon)
2. Data is serialized and published to Kafka topic
3. Consumer processes messages and prepares for analytics (future)

## Resources
- [USGS Earthquake API](https://earthquake.usgs.gov/fdsnws/event/1/)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Confluent Kafka Docker Images](https://docs.confluent.io/platform/current/installation/docker/image-reference.html)

## License
This project is MIT licensed.

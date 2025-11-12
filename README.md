# California Seismic Data Pipeline

A real-time earthquake data processing pipeline streams data from the USGS Earthquake API using Apache Kafka.

## Project Goals
- Stream real-time earthquake data from USGS API
- Process and filter California-specific seismic events
- Demonstrate a scalable data pipeline using Apache Kafka
- Provide real-time analytics capabilities for seismic data

## Current Status: Work in Progress
This project is in early development. Currently implemented:
- USGS API Integration using Apache Kafka
- Local Docker environment for Apache Kafka
- Initial test framework with JUnit

Coming soon:
- Real-time data streaming
- Enhanced data processing
- Analytics dashboard
- Expanded test coverage

## Tech Stack
- Java
- Apache Kafka
- Docker
- Podman
- Maven
- JUnit
- GeoJSON

## Prerequisites
- Java 11 or later
- Docker/Podman and Docker Compose
- Maven

## Project Structure
```
├── artifacts/               # Project artifacts
├── docker/                  # Docker compose files for Kafka
├── schemas/                 # Schemas for earthquake data
├── src/main/
│    ├── java/com/sdp/  
│    │     ├── consumer/     # Kafka consumer implementation
│    │     ├── producer/     # Kafka producer implementation
│    │     ├── client/       # API Clients (Utility Classes)
│    │     └── filter/       # Data Filters
│    └── resources/          # Resources 
│        └── geojson/        # State Boundry code
└── tests/                   # JUnit test files
```

## Development Workflow
1. Producer polls USGS API for earthquake data
2. Data is serialized and published to Kafka topic
3. Consumer processes messages and prepares for analytics

## Resources
- [USGS Earthquake API](https://earthquake.usgs.gov/fdsnws/event/1/)
- [USGS API Format Summary](https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Confluent Kafka Docker Images](https://docs.confluent.io/platform/current/installation/docker/image-reference.html)

## License
This project is MIT licensed.

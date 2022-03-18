![build.yml](https://github.com/kliarist/reactive/actions/workflows/build.yml/badge.svg?branch=main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=reactive&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=kliarist_reactive)

# Reactive

## Description
A SpringBoot POC using:
* Reactive Functions
* MongoDB
* Reactive repositories  
* QueryDSL

## Getting Started

### Build
Build using `./gradlew clean build`.

### Dockerise  
Docker build using `./gradlew bootBuildImage`.

### Development  
For local development start Mongo by using `docker-compose up -d reactive-mongo` and then run the 
  shared idea configuration `ReactiveApplication`.

### Testing
For running all tests run the shared idea configuration `AllTests`.

### Run using Docker Compose

After you have Dockerised the `reactive-server` you can run in Docker using: 
```
docker-compose up -d reactive-mongo
docker-compose up -d reactive-server
```

### TODO (Consider adding a PR)
* Explore Kafka streams/KStream
* JWT
* Spring security
* Global error handling
* Open API

## Useful links

✔ [Sonarcloud](https://sonarcloud.io/project/overview?id=reactive)

✔ [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)

## License

Reactive is [MIT licensed](LICENSE.md).




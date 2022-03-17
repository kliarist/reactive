![build.yml](https://github.com/kliarist/reactive/actions/workflows/build.yml/badge.svg?branch=main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=reactive&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=kliarist_reactive)

### SpringBoot POC using:
* Reactive Functions
* MongoDB
* Reactive repositories  
* QueryDSL

### //TODO
* Explore Kafka streams/KStream

### Build
Build using `./gradlew clean build`.

### Dockerise  
Docker build using `./gradlew bootBuildImage`.

### Development  
For local development start Mongo using `docker-compose up -d reactive-mongo` and then the 
  shared idea configuration `ReactiveApplication`.

### Run using Docker Compose

After you have Dockerised the `reactive-server` you can run in Docker using `docker-compose up 
-d reactive-mongo` and `docker-compose up -d reactive-server`.

### Useful links

✔ [Sonarcloud](https://sonarcloud.io/project/overview?id=reactive)

✔ [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)




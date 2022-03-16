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

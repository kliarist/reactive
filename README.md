![build.yml](https://github.com/kliarist/reactive/actions/workflows/build.yml/badge.svg?branch=main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=reactive&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=reactive)

# Reactive

## Description
A SpringBoot POC using:
* Reactive Functions
* MongoDB
* Reactive repositories  
* QueryDSL

## Getting Started

### Code style

All developers should import and use [checkstyle.xml](config/checkstyle/checkstyle.xml) code style 
to their IDE. For Intellij it can be imported using the [CheckStyle-IDEA](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea) plugin. 

### Git setup

The project is using Git.

After cloning the project navigate to the root directory of the project and execute (if you havent
done so):

```
git config user.name "FIRST_NAME LAST_NAME"
git config user.email "MY_NAME@example.com"
```

also PLEASE install commit-msg hook:

```
git config core.hooksPath .githooks
```

This will handle the consistency across commit messages. 

The git commit standard followed is [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) 


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

### Open API

After you have started the reactive-server the Open API documentation can be accessed on

* [/api/reactive/swagger-ui.html](http://localhost:8080/api/reactive/swagger-ui.html) 
* [/api/reactive/api-docs](http://localhost:8080/api/reactive/api-docs)

### TODO (Consider adding a PR)
* Explore Kafka streams/KStream
* JWT
* Spring security
* Global error handling

## Useful links

✔ [Sonarcloud](https://sonarcloud.io/project/overview?id=reactive)

✔ [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)

## License

Reactive is [MIT licensed](LICENSE.md).




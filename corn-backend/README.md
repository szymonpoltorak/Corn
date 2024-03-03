# Corn Backend

Application has been made following the best clean code patterns and tried to maintain high code test coverage. 
It is a simple backend application that provides REST API for managing users and their roles. 
It is secured with KeyCloak. Application brings high level of abstraction, security and is easy to extend.

## How to run it

1. You can run application using gradle:

```shell
./gradlew bootRun
```

2. To build a docker image and run it, use the `docker-compose.prod.yml` or `docker-compose.dev.yml` file in parent directory.

## Technology stack

* Java 17,
* Spring Boot 3.1.5,
* Spring Data Jpa,
* Spring Security,
* Spring Boot Validation,
* Hibernate,
* Postgres,
* OAuth2 Resource Server,
* Mockito,
* JUnit,
* H2 Database,
* KeyCloak,
* Mapstruct,
* Lombok.

FROM gradle:8.2-jdk17-alpine AS builder

WORKDIR /keycloak-configurer

COPY build.gradle .
COPY settings.gradle .

RUN gradle dependencies

COPY src/ src/

CMD ["gradle", "run"]

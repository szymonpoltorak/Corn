FROM gradle:8.2-jdk17-alpine

WORKDIR /home/corn

COPY build.gradle .

COPY settings.gradle .

COPY src/ src/

RUN gradle build -x test

RUN mv build/libs/corn-backend-0.0.1-SNAPSHOT.jar corn.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "corn.jar"]

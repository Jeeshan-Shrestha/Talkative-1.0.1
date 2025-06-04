FROM eclipse-temurin:21-jdk-alpine

LABEL maintainer="shresthajeesan@gmail.com"

WORKDIR /app

COPY target/ChatAppV2-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

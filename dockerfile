FROM openjdk:17-jdk-slim as build
WORKDIR /app
COPY target/receipt-0.0.1-SNAPSHOT.jar /app/receipt-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "receipt-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080

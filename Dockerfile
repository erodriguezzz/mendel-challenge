FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /usr/src/app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

COPY --from=builder /usr/src/app/target/*.jar /usr/app/*.jar

WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "/usr/app/*.jar"]
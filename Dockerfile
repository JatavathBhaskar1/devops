# Stage 1: Build the application
FROM eclipse-temurin:21-jdk AS build

WORKDIR /musifyapi
# Copy Maven wrapper and pom.xml first to cache dependencies
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies without building the full project
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src ./src

# Build the project and skip tests to speed up build
RUN ./mvnw package -DskipTests


# Use lightweight Java 21 image
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /musifyapi

COPY target/musifyapi-0.0.1-SNAPSHOT.jar .

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "/musifyapi-0.0.1-SNAPSHOT.jar"]

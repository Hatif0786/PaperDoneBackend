# Stage 1: Use Maven to build the project
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the parent POM file and download dependencies (to leverage Docker cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application, skipping tests
RUN mvn clean package -DskipTests

# Stage 2: Use a slim JDK image for the runtime
FROM openjdk:17.0.1-jdk-slim

# Set the working directory in the runtime image
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the default port Spring Boot runs on
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]

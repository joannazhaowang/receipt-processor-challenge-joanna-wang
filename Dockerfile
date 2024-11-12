# Use OpenJDK 17 image as the base
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application JAR file into the container
COPY build/libs/receipt-processor-challenge-joanna-wang-0.0.1-SNAPSHOT.jar app.jar

# Expose the http port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

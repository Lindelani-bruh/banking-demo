# Use an OpenJDK base image
FROM openjdk:20

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY build/libs/demo-0.0.1-SNAPSHOT.jar /app/demo-0.0.1-SNAPSHOT.jar

# Expose the port that your Kotlin Spring Boot application listens on
EXPOSE 8080

# Specify the command to run your Kotlin Spring Boot application when the container starts
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
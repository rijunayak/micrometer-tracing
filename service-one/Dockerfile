# Use an OpenJDK base image with the necessary JDK version
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files and source code into the container
COPY build/libs/service-one-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application will run on
EXPOSE 8080
EXPOSE 5006

# Run the application
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5006", "-jar", "app.jar"]

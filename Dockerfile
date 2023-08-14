# Use an official OpenJDK runtime as a base image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the compiled JAR file into the container
COPY minglebox.jar minglebox.jar

# Run the application when the container starts
CMD ["java", "-jar", "minglebox.jar"]
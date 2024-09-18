# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle gradlew ./
COPY gradle gradle
COPY src src

# Install Gradle and build the project
RUN ./gradlew build

# Copy the built JAR file into the container
COPY build/libs/cocktail-game-backend-0.0.1-SNAPSHOT.jar /app/cocktail-game.jar

# Expose the port that the Spring Boot app will run on (default is 8080)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/cocktail-game.jar"]
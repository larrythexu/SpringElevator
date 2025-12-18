# Build
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar elevatoremu.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "elevatoremu.jar"]

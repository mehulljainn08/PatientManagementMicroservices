FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Step 1: Copy and install patient-service locally
COPY ../patient-service /tmp/patient-service
RUN mvn -f /tmp/patient-service/pom.xml clean install -DskipTests

# Step 2: Copy billing-service files (paths relative to build context = project-root)
COPY billing-service/pom.xml .
RUN mvn dependency:go-offline -B

COPY billing-service/src ./src
RUN mvn clean package -DskipTests

# Step 3: Runtime image
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 4002
EXPOSE 9002
ENTRYPOINT ["java", "-jar", "app.jar"]

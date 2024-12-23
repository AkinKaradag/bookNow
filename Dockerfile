# Build-Stage mit Maven und Java 20
FROM maven:3.9.4-eclipse-temurin-20 AS build
WORKDIR /app

# Kopiere die pom.xml und lade Abhängigkeiten vor
COPY pom.xml .
RUN mvn dependency:go-offline

# Anwendungscode kopieren und bauen
COPY src ./src
RUN mvn package

# Runtime-Stage mit schlankem JDK 20
FROM eclipse-temurin:20-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
COPY bookNowDB.sqlite /app/bookNowDB.sqlite
ENTRYPOINT ["java", "-jar", "app.jar"]

FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY rest/pom.xml rest/
COPY calculator/pom.xml calculator/
COPY rest/src rest/src
COPY calculator/src calculator/src

RUN mvn -B clean package -DskipTests
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY rest/target/rest-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
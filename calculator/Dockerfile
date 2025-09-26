FROM eclipse-temurin:21-jre
WORKDIR /app

COPY target/calculator-*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
🧮 WIT Calculator API

A RESTful API that implements the basic functionalities of a calculator.
Project developed as a technical exercise for evaluation.

📌 Features

Supported operations:

Addition

Subtraction

Multiplication

Division (with division-by-zero handling)

Support for 2 operands only (a and b).

Support for arbitrary precision signed decimal numbers.

🏗️ Architecture

Maven multi-module:

calculator → business logic module.

rest → REST module (controllers, validations, exception handler).

Spring Boot 3 as foundation.

Swagger/OpenAPI for documentation and testing.

Logback configured for console and file logging.

🚀 How to Run
Prerequisites

Java 21+

Maven 3.9+

Docker (optional, if you want to run via containers)

# Build and run tests
mvn clean install

# Run the application (REST module)
cd rest
mvn spring-boot:run


The API will be available at:
👉 http://localhost:8080

📖 API Documentation

Swagger UI available at:
👉 http://localhost:8080/swagger-ui.html

Endpoints can be tested directly through Swagger.

🐳 Docker (optional)

Build the Docker images:

docker build -t wit-calculator-rest ./rest
docker build -t wit-calculator-core ./calculator


Run with Docker Compose:

docker-compose up --build

✅ Tests

JUnit 5 + Spring Boot Test.

Unit tests implemented for:

Controllers (200/400/500 responses).

Services (correct calculations and error cases).

Exception Handler (validation errors, malformed JSON, etc).

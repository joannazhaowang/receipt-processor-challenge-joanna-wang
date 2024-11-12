# Receipt Processor Challenge

- **Author**: Joanna Wang
- **Email**: joannawang1027@gmail.com

## Introduction

This repository contains a web service implemented in **Java** and **Spring Boot** following requirements in [receipt-processor-challenge](https://github.com/fetch-rewards/receipt-processor-challenge) repo. 

The service provides two REST API endpoints to process receipt data and calculate points based on specific rules.

## API Endpoints

1. **Process Receipt**
    - **Endpoint**: `/receipts/process`
    - **Method**: `POST`
    - **Description**: Accepts a JSON payload containing receipt information and returns a unique receipt ID.

2. **Get Points**
    - **Endpoint**: `/receipts/{id}/points`
    - **Method**: `GET`
    - **Description**: Accepts a receipt ID as a path parameter and returns the calculated points for that receipt.

## Running the Service Locally

To run this web service in a Docker container locally, follow these steps. 

**Note:** Ensure that you execute the following commands from the root folder of this repository.

1. **Build the JAR File**  
   Run the following command to build the project and create the JAR file:

   ```bash
   ./gradlew clean build
   ```

2. **Build the Docker Image**
   Use the following command to build the Docker image for the application:

   ```bash
   docker build -t receipt-processor-challenge-joanna-wang .
   ```

3. **Run the Docker Container**  
   Start the Docker container with the following command:

   ```bash
   docker run -d -p 8080:8080 receipt-processor-challenge-joanna-wang
   ```

After this, the web service will be accessible at http://localhost:8080.

## Running Unit Tests

Unit tests have been implemented for both the controller and service layers of this application. To run these tests, execute the following command from the root directory:

```bash
./gradlew test --rerun-tasks
```

This command will re-run all tests and display the results in the console.

After running the tests, you can view a detailed HTML report by opening the following file:
- `build/reports/tests/test/index.html`

This report provides a summary and detailed results for each test case.

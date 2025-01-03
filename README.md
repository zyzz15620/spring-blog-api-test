# AutoTestingHub Backend Automation Testing Framework

This is the **API Automation Testing Framework** developed for testing the backend of the [**AutoTestingHub.org**](https://www.autotestinghub.org) project. It enables efficient testing of REST APIs, database validations, and entity management.

## Features
1. **Purpose**: Framework built to test the backend of the AutoTestingHub platform.
2. **Video**: A speeded-up video of the framework's development process is available here: [![Youtube Badge](https://img.shields.io/badge/YouTube-FF0000?style=for-the-badge&logo=youtube&logoColor=white)](https://www.youtube.com/watch?v=S5XX_2YpJp8)

3. **Test Cases**: Google Sheet containing all the test cases:  
   [View Test Cases](https://docs.google.com/spreadsheets/d/1Lj4-fV29-hv2MXqrjs9NGcxejD2Cee4Jc6nzJ-c02zA/edit?usp=sharing)
4. **Swagger Documentation**: Refer to the Swagger UI for backend API documentation and manual testing:  
   [Swagger UI](https://springboot-docker-blog-0-3-release.onrender.com/swagger-ui/index.html#/).  
   *(Note: The page might took up to 5 minutes as it uses free hosting, the server go to sleep after 30mins of not using.)*
5. **Backend**:
   [Springboot-hub Github Repo](https://github.com/zyzz15620/springboot-hub).  

## Project Structure
The framework is organized as follows:

- **`common`**: Utility classes for common functionalities:
  - `ConstantUtils`: Centralized constants for reusable configurations.
  - `DatabaseConnection`: Handles database connections for test data verification.
  - `EnvConfig`: Reads and manages environment-specific configurations.
  - `MethodUtils`: Helper methods for reusable test logic.
  - `RestAssuredSetup`: Base setup for RestAssured configurations.
- **`data`**: Contains test data and schema validators:
  - `JsonData`: Stores predefined JSON payloads or expected JSON for verification
  - `SchemaData`: Defines schema validation for response validation.
- **`model`**: Data models used for database interaction and validation:
  - `Account`: Model for testing Account-related resources.
  - `AccountEntity`: ORM representation of the Account resource.
- **`tests`**: Contains test cases:
  - `ResisterTests`: Test cases for CRUD operations on the **Account** resource.
- **`resources`**:
  - `env`: Environment-specific configuration files, such as `local.env`.
  - `hibernate.properties`: Hibernate-related configurations for database operations.

## Current Status
- Currently, tests have only been implemented for **CRUD operations of the Account resource**.
- The framework is **under development**, with continuous additions for other resources.

## Tech Stack
- **API Request Handling**: RestAssured
- **Build Tool**: Gradle
- **Database Validation and Management**: Hibernate ORM
- **Entity Validation**: Hibernate Validator
- **Assertions**:
  - JUnit 5 for unit testing.
  - Hamcrest for fluent and expressive assertions.
  - JsonUnit for JSON validation.
- **Programming Language**: Java 17


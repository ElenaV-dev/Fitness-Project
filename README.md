# Fitness Center Management System

A web application for managing a fitness center, developed as an educational project to practice Java and modern Spring technologies. The application provides functionality for managing users and fitness-related information through a web interface. It uses Spring MVC for request processing, Hibernate/JPA for database interaction, Spring Security for authentication and authorization, and PostgreSQL as the database.

## Features

- User registration and authentication
- User authorization using Spring Security
- Secure password handling
- Validation of user input
- Management of fitness-related data
- Interaction with PostgreSQL database through Hibernate/JPA
- Server-side HTML rendering with Thymeleaf
- Logging application events and errors with Log4j2
- Unit and integration-oriented testing with JUnit and Mockito

## Tech stack

- **Java 24**
- **Spring Core**
- **Spring MVC**
- **Spring Security**
- **Spring AOP**
- **Hibernate ORM**
- **Jakarta Persistence (JPA)**
- **Jakarta Validation**
- **AspectJ**
- **PostgreSQL**
- **HikariCP**
- **Thymeleaf**
- **Log4j2**
- **JUnit 5**
- **Mockito**
- **Spring Test**
- **Maven**
- **Apache Tomcat**

## Architecture

The application follows a layered architecture that separates responsibilities between different components. 

The main layers are:

- **Controller** — handles HTTP requests and prepares responses
- **Service** — contains business logic
- **DAO** — responsible for database operations
- **Entity** — represents database entities
- **Configuration** — application and security configuration
- **Exception handling** — handles application errors
- **Validation** — validates incoming data
  
This separation makes the application easier to maintain, test and extend.

## Project Structure

## Database

The application uses PostgreSQL as its relational database. Hibernate ORM and Jakarta Persistence (JPA) are used to map Java entities to database tables and perform persistence operations. Database connections are managed using HikariCP connection pool.

## Security

Spring Security is used to provide authentication and authorization.

The application includes:

- user authentication
- role-based authorization
- protected endpoints
- secure password storage

## Validation

User input is validated using Jakarta Validation and Hibernate Validator. Validation helps prevent invalid data from being passed to the application and database layers.

## Logging

Log4j2 is used for application logging. Logging is applied to important application events and helps with troubleshooting and monitoring application behavior.

## Testing

The project includes testing tools such as:

- **JUnit 5** — testing framework
- **Mockito** — mocking dependencies
- **Spring Test** — testing Spring components

Tests are used to verify application behavior and individual components.

## How To Run

### Prerequisites

- JDK 24
- Maven
- PostgreSQL
- Apache Tomcat 11

### 1. Clone the repository

```bush
git clone https://github.com/ElenaV-dev/Fitness-Project.git
cd Fitness-Project
```

### 2. Configure PostgreSQL

Create the required database and update the database connection settings.

### 3. Build The Project

Run:
```bush
mvn clean package
```

Maven will create the following WAR file:

```bush
target/fitness.war
```

### 4. Deploy to Apache Tomcat

Copy fitness.war to the Tomcat webapps directory and start the Tomcat server. The application will then be available through the configured Tomcat port.

## Screenshots

## Project Status

The project was developed as an educational project to strengthen practical knowledge of Java web development and the Spring ecosystem.
Possible future improvements include adding new fitness management features, improving the user interface, expanding test coverage and introducing additional API functionality.





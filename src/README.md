# Fitness

Fitness is a web application for managing fitness center activities such as users, workout types, subscriptions, and training records.

The project is built with Spring MVC, Hibernate, and deployed as a WAR application on Apache Tomcat.

---

## Technologies

- Java 24
- Spring Core
- Spring MVC
- Spring Security
- Hibernate ORM
- PostgreSQL
- Thymeleaf
- Maven
- HikariCP
- Log4j2

---

## Requirements

Before running the project, make sure you have installed:

- Java 21+
- Maven 3.9+
- Apache Tomcat 10+
- PostgreSQL 14+

---

## Run

1. Build project:
   mvn clean package

2. Copy WAR to Tomcat:
   target/fitness.war → apache-tomcat/webapps/

3. Start Tomcat:
   Windows
   apache-tomcat/bin/startup.bat

   Linux/Mac
   apache-tomcat/bin/startup.sh

4. Open in browser:
   http://localhost:8080/fitness

## Author

Elena
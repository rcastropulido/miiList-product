# MiiList

App to manage a products list.

---

## Requirements

- Java 17 o superior
- Maven 3.8+

---

## Running the Application

### Option 1: From IntelliJ or Eclipse

Run the main class annotated with @SpringBootApplication.

### Option 2: From Terminal

mvn clean install
mvn spring-boot:run

Or generate the executable .jar:

mvn clean package
java -jar target/miiList-0.0.1-SNAPSHOT.jar

### Option 3: Docker

docker build --target dev -t miilist:dev
docker run -d -p 8080:8080 --name miilist-dev miilist:dev

- Replace target dev with pro for production
- Exists distroless pro option for cloud


---

## Application Access

By default:

http://localhost:8080

Example endpoint:

GET http://localhost:8080/api/products

---

## Running Tests

mvn test


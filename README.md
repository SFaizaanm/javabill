# Electricity Bill Calculator

This is the beginner-friendly version of the project.

## What this version includes
- Consumer registration
- Electricity bill calculation
- Progressive tariff logic
- MySQL database integration
- Save bills and view bill history
- Simple HTML pages with CSS

## Requirements
- Java 17
- Maven
- MySQL
- VS Code or IntelliJ IDEA

## 1. Create database
Open MySQL and run:

```sql
CREATE DATABASE electricity_bill_db;
```

Then run:
- `db/schema.sql`
- `db/sample_data.sql` (optional)

## 2. Update MySQL connection
Open:
- `src/main/resources/application.properties`

Set your username/password if needed:

```properties
spring.datasource.username=root
spring.datasource.password=root
```

## 3. Run the project
In the project folder, run:

```bash
mvn clean package
mvn spring-boot:run
```

Open the browser:

```text
http://localhost:8080
```

## Main pages
- Home: `/`
- Register Consumer: `/register`
- Calculate Bill: `/calculate`
- Bill Details: `/bill/{id}`

## Example consumer
- Consumer ID: `C001`
- Name: `Aisha Khan`

## Example bill calculation
- Previous reading: `1200`
- Current reading: `1365`
- Units: `165`
- Total amount: `930.00`

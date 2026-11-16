# Electricity Bill Calculator

A Java 17 / Spring Boot 3 / Maven web app for consumer management and demonstration electricity bill calculations. The rates in `db/schema.sql` are sample values only; they do not represent a real electricity provider.

## Windows 10 + VS Code setup

### Install prerequisites
1. Install a 64-bit JDK 17 (for example, Temurin 17) and verify in Command Prompt: `java -version`.
2. Install Apache Maven 3.8+ and verify: `mvn -version`. Set `JAVA_HOME` to your JDK 17 folder and add `%JAVA_HOME%\bin` and Maven's `bin` folder to `Path` if needed.
3. Install MySQL Server 8 and start the MySQL80 service. MySQL Workbench is optional.
4. Install Visual Studio Code and the **Extension Pack for Java** (includes Java support) and **Spring Boot Extension Pack**.

### Open and configure
1. Clone or download this repository and open its root folder in VS Code (the folder containing `pom.xml`).
2. Open `src/main/resources/application.properties`. Set `DB_USERNAME` and `DB_PASSWORD` environment variables, or edit the defaults there. Do not commit real passwords.
3. In MySQL Workbench, open and execute `db/schema.sql`. To load optional demo data, execute `db/sample_data.sql` after the schema.
4. In VS Code, wait for the Java/Maven project import to finish.

### Build and run
From VS Code's **Terminal > New Terminal** (PowerShell or Command Prompt), at the project root run:

```powershell
mvn clean package
mvn spring-boot:run
```

Or double-click `run-windows.bat`. Stop with `Ctrl+C`; browse to http://localhost:8080. Health endpoint: http://localhost:8080/api/health.

To provide credentials only for the current PowerShell session:

```powershell
$env:DB_USERNAME = 'root'
$env:DB_PASSWORD = 'your-mysql-password'
mvn spring-boot:run
```

## VS Code shortcuts
- `Ctrl+Shift+P` → **Java: Configure Java Runtime** to confirm JDK 17.
- `Ctrl+Shift+P` → **Maven: Execute Commands** to run `clean package`.
- `F5` runs the Spring Boot application using `.vscode/launch.json`.

## Main modules
- `controller`: Spring MVC routes and Thymeleaf page rendering.
- `service`: validation, progressive tariff calculation, billing and dashboard logic.
- `repository`: JDBC CRUD and aggregation using `PreparedStatement`.
- `model`: consumer, bill and tariff data classes.
- `src/main/resources/templates`: server-rendered HTML templates.
- `src/main/resources/static`: CSS and JavaScript resources.
- `db/schema.sql`: schema and illustrative tariff sample data.

## Try it
1. Open Consumers → Add Consumer. Example: ID `DEMO-100`, name `Demo User`, address `10 Main Street`, mobile `9876543210`, category `Domestic`.
2. Calculate Bill with consumer ID `DEMO-100`, previous reading `1200`, current reading `1365`.
3. With the example slabs, 165 units cost ₹715.00 for energy (50×₹3.50 + 100×₹4.50 + 15×₹6.00); adding ₹150 fixed and ₹65 additional gives ₹930.00 total.
4. Generate the bill, inspect Bill History and use Print Bill.

## Troubleshooting
- **`mvn` is not recognized**: install Maven and add its `bin` directory to PATH, then reopen the terminal.
- **Java version mismatch**: set `JAVA_HOME` to JDK 17; verify `mvn -version` also reports Java 17.
- **Communications link failure**: start MySQL and check the URL, username, password and database name.
- **Access denied**: set `DB_USERNAME` and `DB_PASSWORD` correctly in the terminal before starting.
- **Port 8080 occupied**: change `server.port` in `application.properties`.

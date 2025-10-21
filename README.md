# Selenium Java Tests

Automated UI tests written in **Java** using **Selenium WebDriver**, **JUnit 5**, and optionally **Allure Report**.

## 🧰 Technologies
- Java 17+
- Maven
- Selenium WebDriver
- JUnit 5
- (Optional) Allure Report

## ⚙️ Setup & Run

### 1. Clone the repository
```bash
git clone https://github.com/yourusername/selenium-java-tests.git
cd selenium-java-tests
```

### 2. Install dependencies
```bash 
mvn clean install
```

### 3. Run tests
```bash
mvn test
```
### 4. Generate Allure report (optional)
``` bash
allure serve allure-results
```
### 5. CI/CD
``` bash
Tests are integrated with CI/CD pipelines, so they can be executed automatically on every commit or pull request.

Example setups:

Jenkins: automatically builds the project, runs tests, and generates Allure reports.

GitHub Actions: workflow triggers on push/PR and runs the same test commands.
```

📁 Project structure
``` bash
selenium-java-tests/
├── src/
│   ├── main/java/pages/        # Page Object classes
│   └── test/java/tests/        # JUnit test classes
├── pom.xml                     # Maven configuration
└── README.md                   # This file
```

### Example
![Allure Report](img/img.png)
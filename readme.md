<!--
    #/**
    # * @author Avdhut Shirgaonkar
    # * Email: avdhut.ssh@gmail.com
    # * LinkedIn: https://www.linkedin.com/in/avdhut-shirgaonkar-811243136/
    # */
    #/***************************************************/
-->
# 🚀 API Automation Framework Using REST Assured

## 📑 Table of Contents

- [Introduction](#-introduction)
- [Prerequisites](#️-prerequisites)
- [Project Structure](#-project-structure)
- [Getting Started](#️-getting-started)
- [Test Execution](#-test-execution)
- [Reporting](#-reporting)
- [Future Enhancements](#-future-enhancements)
- [Contacts](#-contacts)

## 📖 Introduction

This repository contains a REST API test automation framework developed using REST Assured and Java, managing dependencies with Maven. The framework is designed to automate test cases for PayPal's API services, focusing on authentication, payment processing, and other API functionalities.

The framework follows best practices such as:

- **Modularity** for maintainability and scalability
- **Object-oriented design** for reusable components
- **Factory pattern** for request/response specification building
- **Property-driven** configuration for environment flexibility
- Comprehensive test reporting using **Allure**
- **Explicit assertions** and validation points
- **Generic utilities** for property reading, assertions, test data loading, and common operations
- **Lombok integration** for reducing boilerplate code in POJO classes
- **CI/CD** ready with Jenkins and GitHub Actions integration
- **Well-structured test data** organization with JSON files

## 🛠️ Prerequisites

Before you start, ensure you have the following installed on your machine:

- **Java Development Kit (JDK)**: Version 11 or later
- **Maven**: To manage project dependencies
- **Docker**: For running the mock server and service
- **Git**: To clone the repository
- **An IDE**: (such as IntelliJ IDEA or Eclipse) with TestNG plugin installed

## 📁 Project Structure

The project follows a modular structure optimized for API testing:

```plaintext
project-root/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── paypal/
│   │               ├── constants/
│   │               │   ├── Endpoints.java                # API endpoint URLs constants
│   │               │   └── StatusCode.java               # HTTP status code constants
│   │               ├── pojo/                             # Package for all data models
│   │               │   ├── auth/
│   │               │   │   └── TokenResponse.java        # Authentication token response model
│   │               │   └── order/                        # Order-related POJO classes
│   │               │       ├── Address.java              # Address information model
│   │               │       ├── Amount.java               # Payment amount with currency details
│   │               │       ├── Breakdown.java            # Price breakdown components
│   │               │       ├── CaptureOrderResponse.java # Response for order capture operation
│   │               │       ├── Card.java                 # Card payment details
│   │               │       ├── ConfirmOrderRequest.java  # Request to confirm an order
│   │               │       ├── ConfirmOrderResponse.java # Response from order confirmation
│   │               │       ├── ErrorDetail.java          # Detailed error information
│   │               │       ├── ErrorResponse.java        # Error response structure
│   │               │       ├── Item.java                 # Line item in an order
│   │               │       ├── Link.java                 # HATEOAS link for API navigation
│   │               │       ├── Links.java                # Collection of API links
│   │               │       ├── OrderRequest.java         # Request to create new order
│   │               │       ├── OrderResponse.java        # Response from order creation
│   │               │       ├── PatchOperation.java       # Patch operation for order updates
│   │               │       ├── Payee.java                # Payment recipient information
│   │               │       ├── Payer.java                # Payment sender information
│   │               │       ├── PayerName.java            # Payer's name details
│   │               │       ├── PaymentSource.java        # Source of payment (card, etc.)
│   │               │       ├── PurchaseUnit.java         # Purchase unit with items and amounts
│   │               │       └── Shipping.java             # Shipping details for order
│   │               └── utils/
│   │                   └── PropertyReader.java           # Utility to read properties files
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── paypal/
│       │           ├── generic/
│       │           │   ├── RequestFactory.java           # Factory for creating API requests
│       │           │   └── RestClient.java               # REST client wrapper for RestAssured
│       │           ├── specs/
│       │           │   ├── RequestSpecificationBuilder.java  # Builder for request specifications
│       │           │   └── ResponseSpecificationBuilder.java # Builder for response specifications
│       │           ├── tests/
│       │           │   ├── _000_BaseTest.java            # Base test class with common setup/teardown
│       │           │   ├── _001_AuthTest.java            # Authentication tests
│       │           │   └── _002_OrdersTest.java          # End-to-end tests for order operations
│       │           └── utils/
│       │               ├── AllureReportUtils.java        # Utilities for Allure reporting
│       │               ├── AllureRestAssuredFilter.java  # Filter to capture API details for Allure
│       │               ├── AssertionUtils.java           # Custom assertion utilities
│       │               ├── CommonUtils.java              # General utility methods
│       │               ├── DataProviderUtils.java        # TestNG data providers
│       │               ├── EnvironmentSetup.java         # Environment configuration
│       │               ├── LogUtils.java                 # Logging utilities
│       │               ├── PropertyReader.java           # Property file reader
│       │               └── TestDataLoader.java           # Test data loading utilities
│       └── resources/
│           ├── allure.properties                         # Allure reporting configuration
│           ├── application.properties                    # Application configuration
│           ├── testdata/                                 # Test data files
│           │   └── orders/                               # Order-related test data
│           │       ├── createOrder.json                  # Test data for order creation
│           │       ├── updateOrder.json                  # Test data for order updates
│           │       └── confirmOrder.json                 # Test data for order confirmation
└── pom.xml                                               # Maven project configuration
└── testng.xml                                            # TestNG test suite configuration
```
## ▶️ Getting Started

1. Clone the repository:

```bash
git clone https://github.com/avdhutssh/sample-cart-offer_Automation.git
```

2. Navigate to the project directory:

```bash
cd API-Automation_RestAssured
```

3. Build the project:

```bash
mvn clean install -DskipTests
```

This will download all required dependencies such as RestAssured, TestNG, Allure Reports and Log4j.

## 🚀 Test Execution

You can run the test cases using TestNG directly from the IDE or from the command line:

1. Using Maven Command Line

Run all tests:

```bash
mvn clean test
```

Run with secure credentials:

```bash
mvn clean test -Dclient.id=YOUR_CLIENT_ID -Dclient.secret=YOUR_CLIENT_SECRET
```

Run tests using TestNG XML:

```bash
mvn test -DsuiteXmlFile=testng.xml
```


## 🎯 Reporting

This project integrates Allure Reports for detailed reporting of test executions.

To view the reports:

After test execution, generate the Allure report:
```bash
mvn allure:report
```

To view the report in a browser:
```bash
mvn allure:serve
```

You can also capture screenshots for failed test cases and view them in the Extent Report.

![Allure Report](/Misc/AllureReport.png)


## 🤖 CI/CD Using Jenkins

### 1. Jenkins Integration

You can integrate the project with Jenkins for Continuous Integration. Follow these steps:

1. Install Maven, Allure, TestNg, HTML Publisher plugins
2. Set up a Maven Project Dashboard in Jenkins.
3. Clone the GitHub repository under Source Code Management
4. Make this project parametrized for passing secrets
4. In the Build section, add the following command to run the tests:
   ```bash
   clean test -Dclient.id=${CLIENT_ID} -Dclient.secret=${CLIENT_SECRET}
   ```

![Jenkins-Execution](/Misc/Jenkins.png)

### 2. GitHub Actions

This project also uses **GitHub Actions** for CI/CD:

- The CI pipeline triggers on every push or pull request to the main branch.
- It automatically runs the test cases using the TestNG suite on github provided Windows machine and generates the Extent Reports as Artifacts.
- The results can be viewed in the `Actions` tab of the GitHub repository.
- Refer workflow yaml file for same. [Workflow file](/.github/workflows/api-tests.yml)
  ![GitHub Actions](/Misc/GitHub_Actions.png)

## 📧 Contacts

- [![Email](https://img.shields.io/badge/Email-avdhut.ssh@gmail.com-green)](mailto:avdhut.ssh@gmail.com)
- [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue)](https://www.linkedin.com/in/avdhut-shirgaonkar-811243136/)

Feel free to reach out if you have any questions, or suggestions.

Happy Learning!

Avdhut Satish Shirgaonkar
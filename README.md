# 🏥 Healthcare Management System – Backend

> 🚀 A RESTful Healthcare Management System backend developed using **Java, Spring Boot, Spring Security, JWT, Spring Data JPA, Hibernate, and MySQL**.

---

## 📌 Project Overview

The **Healthcare Management System** is a backend application designed to manage patients, doctors, appointments, prescriptions, medical records, billing, and dashboard reports.

The application follows a **layered architecture** to separate API handling, business logic, database operations, and data transformation. It also incorporates authentication, validation, exception handling, caching, scheduled tasks, and application monitoring.

This project demonstrates practical implementation of **REST APIs, Spring Boot, JWT Authentication, Role-Based Authorization, DTOs, JPA, Hibernate, SQL, Transaction Management, and Backend Development Best Practices**.

---

## ✨ Features

### 🧑‍⚕️ Patient Management
- ➕ Register New Patients
- 🔍 Retrieve Patient Details by ID
- 📋 View All Patients
- ✏️ Update Patient Information
- 🗑️ Delete Patients with Relationship Validation
- 🔎 Search Patients by Name and Mobile Number
- 📄 Pagination and Sorting
- ✅ Email and Mobile Number Validation
- 🚫 Prevent Duplicate Email and Mobile Numbers
- ⚡ Cache Patient Details Using Caffeine

### 👨‍⚕️ Doctor Management
- ➕ Add New Doctors
- 🔍 Retrieve Doctor Details
- 📋 View All Doctors
- ✏️ Update Doctor Information
- 🗑️ Delete Doctors
- 🔎 Search Doctors
- 🚫 Duplicate Email and Mobile Number Checks

### 📅 Appointment Management
- ➕ Schedule Appointments
- 🔍 Retrieve Appointment Details
- 📋 View All Appointments
- ✏️ Update Appointments
- ❌ Cancel Appointments
- 👤 Associate Appointments with Patients and Doctors
- 🔎 Retrieve Appointments by Patient or Doctor
- 🕒 Scheduled Appointment Monitoring

### 💊 Prescription Management
- ➕ Create Prescriptions
- 🔍 Retrieve Prescription Details
- 📋 View All Prescriptions
- ✏️ Update Prescriptions
- 🗑️ Delete Prescriptions
- 🔗 Retrieve Prescriptions by Appointment

### 🗂️ Medical Record Management
- ➕ Create Medical Records
- 🔍 Retrieve Medical Records
- 📋 View All Medical Records
- ✏️ Update Medical Records
- 🗑️ Delete Medical Records
- 🔗 Retrieve Medical Records by Patient

### 💳 Billing Management
- ➕ Create Billing Records
- 🔍 Retrieve Billing Details
- 📋 View All Billing Records
- ✏️ Update Billing Information
- 🗑️ Delete Billing Records
- 🧮 Calculate Total Charges from Consultation, Medicine, Lab, and Other Charges

### 📊 Dashboard & Reporting
- 👥 Total Patient Count
- 👨‍⚕️ Total Doctor Count
- 📅 Appointment Count
- 💰 Total Revenue Summary

### 🔐 Authentication & Security
- 🔑 User Authentication Using JWT
- 🛡️ Spring Security Integration
- 🔒 Role-Based Authorization
- 🔑 JWT Request Filter
- 🔐 Password Encoding
- 🚧 Protect Restricted API Endpoints

### ⚙️ Backend Engineering Features
- 🏗️ Layered Architecture
- 📦 Request and Response DTOs
- 🔄 Entity-to-DTO Mapping
- ✅ Jakarta Bean Validation
- ⚠️ Centralized Exception Handling
- 📬 Standardized API Responses Using `ApiResponse<T>`
- 🔁 Transaction Management Using `@Transactional`
- 📝 Structured Logging Using SLF4J and Lombok
- 🔍 JPQL and Native SQL Queries
- 📄 Pagination and Sorting
- ⚡ Spring Cache with Caffeine
- ⏰ Scheduled Tasks Using Spring Scheduler
- 📈 Application Health Monitoring Using Spring Boot Actuator
- 📚 Interactive API Documentation Using Swagger/OpenAPI
- 🕒 Entity Auditing for Creation and Update Timestamps

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| ☕ Java 17 | Programming Language |
| 🍃 Spring Boot 3.5.6 | Backend Framework |
| 🌐 Spring Web | REST API Development |
| 🔐 Spring Security | Authentication and Authorization |
| 🎟️ JWT | Token-Based Authentication |
| 🗄️ Spring Data JPA | Database Access |
| 🔄 Hibernate | ORM and Entity Mapping |
| 🐬 MySQL | Relational Database |
| 📦 Maven | Dependency Management |
| ✅ Jakarta Bean Validation | Request Validation |
| ⚡ Spring Cache + Caffeine | In-Memory Caching |
| ⏰ Spring Scheduler | Scheduled Background Tasks |
| 📈 Spring Boot Actuator | Health and Metrics Monitoring |
| 📚 Springdoc OpenAPI / Swagger UI | API Documentation |
| 📝 SLF4J + Lombok | Logging and Boilerplate Reduction |
| 🧪 Postman / Swagger UI | API Testing |
| 🔧 Git & GitHub | Version Control |

---

## 🏗️ Project Architecture

The application follows a layered architecture:

```text
                    👤 CLIENT
               (Postman / Swagger UI)
                         │
                         ▼
                🔐 Spring Security
                  + JWT Filter
                         │
                         ▼
                 🌐 Controller Layer
                         │
                         ▼
                  ⚙️ Service Layer
                   (Business Logic)
                         │
                  ┌──────┴──────┐
                  ▼             ▼
             🔄 Mapper      ⚡ Cache
                  │
                  ▼
                🗄️ Repository Layer
                         │
                         ▼
                  🔄 JPA / Hibernate
                         │
                         ▼
                     🐬 MySQL
```

### 📂 Main Application Layers

- **Controller Layer:** Handles HTTP requests, endpoint mapping, and response status codes.
- **Service Layer:** Implements business rules, validation checks, and application workflows.
- **Repository Layer:** Performs database operations using Spring Data JPA.
- **Entity Layer:** Represents database tables and entity relationships.
- **DTO Layer:** Defines request and response structures.
- **Mapper Layer:** Converts entities into DTOs and DTOs into entities.
- **Security Layer:** Handles JWT authentication and authorization.
- **Exception Layer:** Centralizes application error handling.
- **Configuration Layer:** Contains security, caching, and application configurations.

---

## 🔗 API Endpoints

### Patients

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/patients` | Create Patient |
| GET | `/api/patients` | Get All Patients |
| GET | `/api/patients/{patientId}` | Get Patient by ID |
| PUT | `/api/patients/{patientId}` | Update Patient |
| DELETE | `/api/patients/{patientId}` | Delete Patient |
| GET | `/api/patients/search?keyword=value` | Search Patients |
| GET | `/api/patients/page?page=0&size=10&sortBy=firstName&direction=asc` | Paginated and Sorted Patients |

### Doctors

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/doctors` | Create Doctor |
| GET | `/api/doctors` | Get All Doctors |
| GET | `/api/doctors/{doctorId}` | Get Doctor by ID |
| PUT | `/api/doctors/{doctorId}` | Update Doctor |
| DELETE | `/api/doctors/{doctorId}` | Delete Doctor |
| GET | `/api/doctors/search?keyword=value` | Search Doctors |

### Appointments

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/appointments` | Create Appointment |
| GET | `/api/appointments` | Get All Appointments |
| GET | `/api/appointments/{appointmentId}` | Get Appointment by ID |
| PUT | `/api/appointments/{appointmentId}` | Update Appointment |
| DELETE | `/api/appointments/{appointmentId}` | Delete Appointment |

### Prescriptions

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/prescriptions` | Create Prescription |
| GET | `/api/prescriptions` | Get All Prescriptions |
| GET | `/api/prescriptions/{prescriptionId}` | Get Prescription by ID |
| PUT | `/api/prescriptions/{prescriptionId}` | Update Prescription |
| DELETE | `/api/prescriptions/{prescriptionId}` | Delete Prescription |

### Medical Records

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/records` | Create Medical Record |
| GET | `/api/records` | Get All Medical Records |
| GET | `/api/records/{recordId}` | Get Medical Record by ID |
| PUT | `/api/records/{recordId}` | Update Medical Record |
| DELETE | `/api/records/{recordId}` | Delete Medical Record |

### Billing

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/billing` | Create Billing Record |
| GET | `/api/billing` | Get All Billing Records |
| GET | `/api/billing/{billingId}` | Get Billing by ID |
| PUT | `/api/billing/{billingId}` | Update Billing |
| DELETE | `/api/billing/{billingId}` | Delete Billing |

> **Note:** Verify endpoint paths, HTTP methods, query parameters, and authorization rules against the current controllers and Swagger documentation before publishing. Additional appointment, prescription, medical-record, billing, authentication, and dashboard endpoints may also be available.

---

## 🔄 Example Request Flow

```text
Client sends HTTP Request
          │
          ▼
JWT Authentication & Authorization
          │
          ▼
Controller receives the request
          │
          ▼
DTO Validation using @Valid
          │
          ▼
Service executes business logic
          │
          ▼
Repository accesses MySQL
          │
          ▼
JPA / Hibernate processes database operations
          │
          ▼
Mapper converts Entity to Response DTO
          │
          ▼
ApiResponse<T> + HTTP Status
          │
          ▼
Client receives JSON Response
```

---

## 📬 Standard API Response

### Successful Response

```json
{
  "success": true,
  "message": "Patient retrieved successfully",
  "data": {
    "patientId": 1
  }
}
```

### Error Response

```json
{
  "success": false,
  "status": 404,
  "message": "Patient not found",
  "timestamp": "2026-10-09T10:30:00"
}
```

*These are illustrative examples. Actual response fields and messages depend on the configured DTOs and exception handlers.*

---

## ⚠️ Exception Handling & Validation

The application uses centralized exception handling with `@RestControllerAdvice`.

- `ResourceNotFoundException` — Resource not found.
- `DuplicateResourceException` — Duplicate email or mobile number.
- `BadRequestException` — Invalid business request.
- `MethodArgumentNotValidException` — Request validation failure.
- `ConstraintViolationException` — Constraint validation failure.
- General exception handler — Unexpected application errors.

Request validation uses annotations such as `@NotBlank`, `@NotNull`, `@Size`, `@Email`, `@Pattern`, `@Min`, and `@Max`, where applicable.

---

## ⚡ Caching, Scheduling & Monitoring

- **Caffeine Cache:** Caches patient lookup by ID to reduce repeated database reads.
- **Cache Eviction:** Removes the corresponding cached patient entry during update and delete operations.
- **Spring Scheduler:** Runs a scheduled job to monitor appointments for the current day.
- **Spring Boot Actuator:** Provides application health and metrics endpoints.
- **Swagger/OpenAPI:** Provides interactive API documentation and testing.

### 🔗 Useful URLs

| Feature | URL |
|---|---|
| Swagger UI | `http://localhost:8080/swagger-ui/index.html` |
| OpenAPI JSON | `http://localhost:8080/v3/api-docs` |
| Application Health | `http://localhost:8080/actuator/health` |

---

## ⚙️ Installation & Setup

### Prerequisites

- Java 17 or compatible configured JDK
- MySQL Server
- IntelliJ IDEA or another Java IDE
- Git
- Maven support through Maven Wrapper or IDE

### 1. Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd healthcare-management-system
```

### 2. Create the MySQL Database

```sql
CREATE DATABASE healthcare_management_db;
```

### 3. Configure Database Connection

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/healthcare_management_db
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080
```

Configure `DB_USERNAME` and `DB_PASSWORD` in your local environment before starting the application. Retain any other required properties already present in your project.

### 4. Run the Application

Open the project in IntelliJ IDEA, reload the Maven project, and run the main Spring Boot application class.

Alternatively, if Maven Wrapper is included:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

### 5. Test the APIs

Open Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

Use Swagger UI or Postman to test the available endpoints. Authenticate first if the endpoint requires a JWT token.

---

## 🧪 Testing Checklist

- ✅ Patient, Doctor, Appointment, Prescription, Medical Record and Billing CRUD
- ✅ Invalid request validation
- ✅ Duplicate email and mobile-number handling
- ✅ Resource-not-found scenarios
- ✅ JWT authentication and authorization
- ✅ Pagination, sorting and search
- ✅ JPQL and native query behavior
- ✅ Billing calculations and dashboard summaries
- ✅ Cache hit and cache eviction behavior
- ✅ Scheduled appointment monitoring
- ✅ Actuator health endpoint
- ✅ Swagger endpoint availability
- ✅ Transaction behavior and database consistency

---

## 🔒 Security & Best Practices

- Store database credentials and JWT secrets in environment variables or a secrets manager.
- Never commit passwords, tokens, or private configuration to GitHub.
- Use HTTPS in deployed environments.
- Restrict access to protected endpoints and operational monitoring endpoints.
- Validate incoming requests and avoid exposing internal exception details.
- Review authorization rules for every module before production deployment.

---

## 🚀 Future Enhancements

- 🧪 Automated unit and integration tests using JUnit and Mockito
- 🐳 Docker containerization
- 🔄 CI/CD pipeline integration
- ☁️ Cloud deployment
- 📊 Advanced monitoring and alerting
- 🗃️ Database migration management using Flyway or Liquibase
- 📧 Email and appointment reminder notifications
- 💳 Payment gateway integration
- 📈 Advanced reporting and analytics

---

## 👨‍💻 Author

**Poovarasan M**

Java Backend Developer

**Technologies:** Java | Spring Boot | Spring Security | JWT | Spring Data JPA | Hibernate | MySQL | REST APIs

---

⭐ If you find this project useful, feel free to star the repository!


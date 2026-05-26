# Antigravity LMS Backend Monolith

Welcome to the **Antigravity Learning Management System (LMS) Backend**. 

This system has been architected as a **Domain-Driven Modular Monolith** in **Spring Boot 3.5.3** and **Java 21**. This structure ensures extreme modular separation, high testability, clean separation of concerns, and immediate readiness to split separate modules (like Payments or Notifications) into microservices in the future.

---

## 🏗️ Architectural Foundations

Unlike traditional layered architectures that group everything globally by `controller/` or `service/`, this codebase is strictly organized by business domains (modules). Each module is self-contained.

```
com.lms
├── config/             # Global configurations (Security, Redis, RabbitMQ, OpenAPI)
├── security/           # Stateless JWT Security filters & token providers
├── shared/             # General ApiResponse, base entities, global errors
└── modules/            # Self-contained business domains
    ├── auth/           # Login, OTP Generation, verification, registrations
    ├── users/          # Core user identity and roles (USER/ADMIN)
    ├── students/       # School-profile documents mapping
    ├── courses/        # Catalog management, lessons, syllabus tracking
    ├── payments/       # Razorpay order generation & signature verify
    ├── enrollments/    # Student-to-course paid allocations
    ├── notifications/  # HTML-styled SMTP email deliveries
    └── analytics/      # Administrative stats engine
```

---

## 🛠️ Technology Stack

* **Core Framework**: Spring Boot `3.5.3` (Java `21`)
* **Datastore**: MongoDB (local Compass or cloud MongoDB Atlas)
* **Security & Authentication**: Spring Security + Stateless JWT Role-Based Access Control (RBAC)
* **API Documentation**: OpenAPI 3 / Swagger (`springdoc-openapi`)
* **Mapping Boilerplate Compiler**: MapStruct `1.6.3`
* **Integrations**: Razorpay Java SDK `1.4.4` (Checkout payments)
* **Helper Libraries**: Lombok (code-reduction)
* **Email Sender**: Spring Boot Mail (`JavaMailSender` SMTP)

---

## 🍃 Database Configurations & Lifecycle Auditing

The backend implements database auditing through `@EnableMongoAuditing` (`MongoConfig.java`). 

All core entities inherit from `BaseEntity.java` to automatically populate:
* `createdAt` (Created date timestamp)
* `updatedAt` (Last modified timestamp)
* `createdBy` (Populated with `SYSTEM` for OTP generation or the authenticated user's email)
* `updatedBy` (Populated with the active auditor email)

---

## 🌐 Complete REST API Endpoint Directory

All endpoints return a uniform wrapper format (`ApiResponse`):
```json
{
  "success": true,
  "message": "Operation message",
  "data": { ... },
  "timestamp": "2026-05-26T14:48:31.123"
}
```

### 🔐 Authentication Module (`/auth`)
* `POST /auth/request-otp`: Request a registration validation code. Sends beautifully styled HTML validation template to the user's email.
* `POST /auth/verify-otp`: Validates the provided code, persists the user to MongoDB, and returns new Access and Refresh tokens.
* `POST /auth/login`: Authenticates verified users using email/mobile and password, returning tokens.

### 👤 Students Module (`/students`)
* `POST /students/profile`: Saves or updates your academic student profile details.
* `GET /students/me`: Retrieves the current user's profile card.
* `GET /students` *(Admin Only)*: Fetch all students.
* `PUT /students/{id}` *(Admin Only)*: Update student card by ID.
* `DELETE /students/{id}` *(Admin Only)*: Delete student record.

### 📚 Courses Module (`/courses`)
* `POST /courses` *(Admin Only)*: Append a new course card into the catalog.
* `GET /courses/class/{className}`: Fetch catalog cards filtered by standard grade (Class 9, Class 10, etc.).
* `GET /courses/{id}`: Fetch full detailed course document by ID.
* `PUT /courses/{id}` *(Admin Only)*: Update course details.
* `DELETE /courses/{id}` *(Admin Only)*: Remove course.

### 💳 Payments Module (`/payments`)
* `POST /payments/create-order?courseId=...&amount=...`: Communicates with Razorpay SDK to create transactional order and persists a tracking document with state `PENDING`.
* `POST /payments/verify?orderId=...&paymentId=...&signature=...`: Confirms signature validity. If valid, changes payment status to `SUCCESS` and **automatically triggers enrollment**.

### 📝 Enrollments Module (`/enrollments`)
* `GET /enrollments/my-courses`: Fetch active course access matching the logged-in student.

### 📊 Analytics Module (`/admin/stats`)
* `GET /admin/stats` *(Admin Only)*: Returns dashboard counters: `{ "students": X, "courses": Y, "enrollments": Z }`.

---

## 🚀 Getting Started

### 1. Requirements
* Java Development Kit (JDK) 21
* Maven (or use the packaged Maven wrapper `mvnw`)
* MongoDB (Compass locally, or Atlas cloud cluster)

### 2. Environment Configurations
Configure the connection URI in `src/main/resources/application-dev.properties`. 

We support the `MONGODB_URI` environment variable, enabling local Compass fallback automatically:
```properties
spring.data.mongodb.uri=${MONGODB_URI:mongodb://localhost:27017/lms_dev_db}
spring.data.mongodb.database=lms_dev_db
```

To run on MongoDB Atlas, set your cloud connection string before launching:
* **Windows (PowerShell)**:
  ```powershell
  $env:MONGODB_URI="mongodb+srv://<username>:<password>@cluster.mongodb.net/lms_dev_db"
  ```
* **macOS / Linux**:
  ```bash
  export MONGODB_URI="mongodb+srv://<username>:<password>@cluster.mongodb.net/lms_dev_db"
  ```

### 3. Running the App
Run the application using the Maven wrapper:
```powershell
# Windows
.\mvnw.cmd spring-boot:run

# macOS / Linux
./mvnw spring-boot:run
```

Once started, the backend is active at `http://localhost:8080` and the interactive OpenAPI documentation is visible at `http://localhost:8080/swagger-ui.html`.

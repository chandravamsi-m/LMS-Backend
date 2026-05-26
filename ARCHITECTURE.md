# LMS Backend: Modular Monolith Architecture

This backend has been modernized from a traditional layered architecture to a **Domain-Driven Modular Monolith**. This approach ensures high maintainability, clear separation of concerns, and readiness for future microservice extraction.

## 🏗️ Architecture Overview

### Modular Structure
Instead of global folders like `service/` or `controller/`, the project is organized by business domains (modules). Each module is self-contained.

```
com.lms
├── config/             # Global configurations (Security, Redis, RabbitMQ, OpenAPI)
├── security/           # Security abstractions (JWT, TokenProvider)
├── shared/             # Shared components (BaseEntity, ApiResponse, Exceptions)
└── modules/            # Domain-specific modules
    ├── auth/           # Login, Registration, OTP logic
    ├── users/          # User and Role management
    ├── courses/        # Course and Syllabus management
    ├── enrollments/    # Student enrollment tracking
    ├── students/       # Detailed student profiles
    ├── payments/       # Razorpay integration
    └── notifications/  # Email and (future) SMS services
```

### Module Internal Structure
Each module follows a consistent internal pattern:
- `controller/`: REST API endpoints.
- `service/`: Business logic.
- `dto/`: Data Transfer Objects for request/response.
- `entity/`: Database models (MongoDB documents).
- `repository/`: Data access layer.
- `mapper/`: Automated conversion between Entities and DTOs (using MapStruct).

---

## 🚀 Modern Engineering Practices

### 1. Standardized API Communication
All endpoints return a consistent `ApiResponse` wrapper:
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... },
  "timestamp": "2026-05-12T..."
}
```

### 2. DTO & Mapper Architecture
Entities are never exposed directly to the client. This prevents "leaking" database internal details and allows the API to evolve independently of the schema. **MapStruct** handles the boilerplate mapping logic.

### 3. Advanced Security
- **Dual Token Architecture**: Implemented support for both Access and Refresh tokens.
- **Role-Based Access Control (RBAC)**: Fine-grained security using `@PreAuthorize`.
- **Token Provider Abstraction**: Decoupled JWT logic from Spring Security.

### 4. Enterprise Foundation
- **Global Exception Handling**: All errors are caught and returned in a standard format.
- **Auditing**: All entities automatically track `createdAt`, `updatedAt`, `createdBy`, and `updatedBy`.
- **Validation**: Jakarta Bean Validation ensures data integrity before it reaches the service layer.

### 5. Future Scalability (Foundation Ready)
- **Redis Integration**: Ready for caching and session management.
- **RabbitMQ Integration**: Prepared for asynchronous notification processing.
- **OpenAPI/Swagger**: Automated documentation available at `/swagger-ui.html`.

---

## 🛠️ Module Responsibilities

| Module | Responsibility |
| :--- | :--- |
| **Auth** | Handles OTP generation, user registration, and secure login. |
| **Users** | Core user identity and role assignment. |
| **Courses** | Catalog management, curriculum, and syllabus tracking. |
| **Enrollments** | Linking students to courses with payment status tracking. |
| **Students** | Managing extended profile information (Full Name, Roll No, etc.). |
| **Payments** | Interfacing with Razorpay for secure checkout. |
| **Notifications** | Sending multi-channel alerts (Email/SMS). |

---

## 📈 Scalability & Microservices
Because each module is self-contained and communicates through clearly defined services, moving a module (like `Payments` or `Notifications`) to its own microservice in the future is a matter of redirecting internal service calls to REST or Message Broker (RabbitMQ) calls.

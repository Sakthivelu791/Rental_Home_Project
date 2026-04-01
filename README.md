Live Website : https://sakthivelu791.github.io/Rental_Home_Frontend/
Frontend github Link : https://github.com/Sakthivelu791/Rental_Home_Frontend.git
# Rental Project

A RESTful backend API for listing and managing rental houses, built with **Spring Boot 4**, **Spring Security**, **JWT authentication**, and **MySQL**.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.3 |
| Security | Spring Security + JWT (jjwt 0.11.5) |
| Persistence | Spring Data JPA + Hibernate |
| Database | MySQL |
| Utilities | Lombok |
| Build Tool | Maven (Maven Wrapper included) |

---

## Project Structure

```
src/main/java/com/sakthi/dev/Rental_Project/
├── Controller/
│   ├── AuthController.java         # Register & login endpoints
│   └── RentalHouseController.java  # CRUD endpoints for rental listings
├── Models/
│   ├── User.java                   # User entity
│   └── RentalHouse.java            # Rental house entity
├── Repository/
│   ├── UserRepository.java
│   └── RentalHouseRepository.java
├── Services/
│   ├── UserServices.java
│   └── RentalHouseServices.java
├── utils/
│   └── JwtUtil.java                # JWT generation & validation
├── JwtFilter.java                  # JWT request filter
├── SecurityConfig.java             # Spring Security configuration
└── RentalProjectApplication.java   # Entry point
```

---

## Prerequisites

- Java 21+
- MySQL 8+
- Maven (or use the included `./mvnw`)

---

## Setup & Configuration

1. **Clone the repository.**

2. **Create the MySQL database:**
   ```sql
   CREATE DATABASE Rental_db;
   ```

3. **Configure `src/main/resources/application.properties`:**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/Rental_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

4. **⚠️ Update the JWT secret** in `JwtUtil.java` before deploying:
   ```java
   private final String SECRET = "your-256-bit-secret-key-which-should-be-very-long!";
   ```
   Use an environment variable or externalized config in production — never hardcode secrets.

5. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```
   The server starts on `http://localhost:8080`.

---

## API Reference

### Auth Endpoints (Public)

| Method | Path | Description | Body |
|---|---|---|---|
| POST | `/auth/register` | Register a new user | `{ "email": "...", "password": "..." }` |
| POST | `/auth/login` | Login and get JWT token | `{ "email": "...", "password": "..." }` |

**Login response:**
```json
{ "token": "<jwt_token>" }
```

---

### Rental House Endpoints

All endpoints below (except `/Home`) require the `Authorization: Bearer <token>` header.

| Method | Path | Auth Required | Description |
|---|---|---|---|
| GET | `/Home` | No | Get all rental listings (public) |
| GET | `/my-houses` | Yes | Get listings created by the logged-in user |
| POST | `/create` | Yes | Create a new rental listing |
| PUT | `/Update` | Yes | Update an existing listing (owner only) |
| DELETE | `/deleteMyHouse` | Yes | Delete a listing (owner only) |

**Rental House fields:**
```json
{
  "location": "string",
  "phoneNumber": 9999999999,
  "mapLink": "https://maps.google.com/...",
  "rentPrice": 5000
}
```

---

## Authentication Flow

1. Register via `POST /auth/register`.
2. Login via `POST /auth/login` — receive a JWT token.
3. Include the token in all subsequent requests:
   ```
   Authorization: Bearer <token>
   ```
4. The `JwtFilter` intercepts every request, validates the token, and injects the user ID into the Spring Security context.
5. Protected operations (update, delete) verify that the requesting user owns the resource.

---

## Data Models

### User
| Field | Type | Notes |
|---|---|---|
| id | Long | Auto-generated primary key |
| email | String | Must be a valid email; unique |
| password | String | BCrypt-encoded |

### RentalHouse
| Field | Type | Notes |
|---|---|---|
| id | Long | Auto-generated primary key |
| location | String | Address or area name |
| phoneNumber | Long | Contact number |
| mapLink | String | Google Maps or similar URL |
| rentPrice | Long | Monthly rent |
| userId | Long | FK — ID of the owner (set automatically from JWT) |

---

## Known Issues & Recommendations

- **JWT secret is hardcoded** — move to an environment variable or Spring config server.
- **Token expiry is set to 24 minutes** (`1000 * 60 * 24` ms) — confirm this is intentional; most apps use 24 hours (`1000 * 60 * 60 * 24`).
- **Owner check uses `!=` on `Long` objects** in `RentalHouseController` — this compares object references, not values. Use `.equals()` or unbox to `long` to avoid subtle bugs.
- **CORS is open to all origins** — restrict `allowedOriginPatterns` in `SecurityConfig` for production.
- **No role-based access control** — all authenticated users have equal permissions; consider adding user roles if needed.

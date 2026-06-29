# 🔗 ShortLink Analytics Platform

A production-inspired backend application built using **Spring Boot** that enables users to create secure short URLs, manage them with JWT authentication, analyze click statistics, and improve performance using Redis caching.

---

## 🚀 Features

### Authentication & Security

* User Registration
* User Login
* JWT Authentication
* BCrypt Password Encryption
* Stateless Authentication using Spring Security

### URL Management

* Create Short URLs
* Redirect to Original URLs
* View Personal URLs
* Delete Short URLs

### Analytics

* Total Click Count
* Click History
* Visitor IP Tracking
* User-Agent Tracking

### Performance

* Redis Caching for URL Redirection
* Automatic Cache Eviction on URL Deletion
* Configurable Cache TTL

### API Documentation

* Interactive Swagger/OpenAPI Documentation

### Validation & Error Handling

* Bean Validation
* Global Exception Handling
* Standardized API Responses

---

# 🛠 Tech Stack

| Category          | Technology                  |
| ----------------- | --------------------------- |
| Language          | Java 21                     |
| Framework         | Spring Boot                 |
| Security          | Spring Security, JWT        |
| Database          | MySQL                       |
| Cache             | Redis                       |
| ORM               | Spring Data JPA / Hibernate |
| Build Tool        | Maven                       |
| API Documentation | Swagger / OpenAPI           |
| Testing           | JUnit 5, Mockito            |
| Version Control   | Git & GitHub                |

---

# 📂 Project Structure

```text
src
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── exception
├── repo
├── security
├── service
└── util
```

---

# 🔐 Authentication Flow

1. Register a new user.
2. Login with email and password.
3. Receive a JWT token.
4. Click **Authorize** in Swagger.
5. Access secured endpoints using the token.

---

# 📌 REST API Endpoints

## Authentication

| Method | Endpoint         |
| ------ | ---------------- |
| POST   | `/auth/register` |
| POST   | `/auth/login`    |

---

## URL Management

| Method | Endpoint            |
| ------ | ------------------- |
| POST   | `/urls`             |
| GET    | `/urls/my`          |
| GET    | `/urls/{shortCode}` |
| DELETE | `/urls/{shortCode}` |

---

## Public Endpoint

| Method | Endpoint                     |
| ------ | ---------------------------- |
| GET    | `/urls/redirect/{shortCode}` |

---

## Analytics

| Method | Endpoint                      |
| ------ | ----------------------------- |
| GET    | `/urls/analytics/{shortCode}` |

---

# ⚡ Redis Cache

The application caches frequently accessed short URLs in Redis to reduce database lookups and improve redirect performance.

* Cache on first redirect
* Subsequent redirects served from Redis
* Automatic cache eviction when a URL is deleted
* Configurable cache expiration

---

# 🗄 Database

Main Entities

* User
* ShortUrl
* Analytics

Relationships

```
User
  │
  └──────< ShortUrl
              │
              └──────< Analytics
```

---

# 📖 API Documentation

After starting the application, Swagger UI is available at:

```
http://localhost:8081/swagger-ui/index.html
```

---

# ▶ Running the Application

## Clone the repository

```bash
git clone https://github.com/Tumansh/shortlink-analytics-platform.git
```

## Navigate to the project

```bash
cd ShortLink-Analytics-Platform
```

## Configure

Copy the example configuration:

```
application-example.properties
```

to

```
application.properties
```

and update the database, Redis, and JWT configuration.

## Start MySQL

Ensure MySQL is running.

## Start Redis

Ensure Redis Server is running.

## Run the application

```bash
mvn spring-boot:run
```


---

# 👨‍💻 Author

**Tumansh Vij**

Backend Developer

Java • Spring Boot • Redis • MySQL • REST APIs • Spring Security

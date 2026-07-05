# 🔗 ShortLink Analytics Platform

A production-inspired backend application built using **Spring Boot** that enables users to securely create, manage, and analyze short URLs. The application is containerized using **Docker**, orchestrated with **Docker Compose**, and deployed on **AWS EC2** with **MySQL** and **Redis**.

---

# 🚀 Features

## Authentication & Security

- User Registration
- User Login
- JWT Authentication
- BCrypt Password Encryption
- Stateless Authentication with Spring Security
- Protected REST APIs

---

## URL Management

- Create Short URLs
- Redirect to Original URLs
- View User URLs
- Delete Short URLs

---

## Analytics

- Total Click Count
- Click History
- Visitor IP Tracking
- User-Agent Tracking

---

## Performance

- Redis Caching for URL Redirection
- Automatic Cache Eviction
- Configurable Cache TTL
- Reduced Database Lookups

---

## Validation & Error Handling

- Bean Validation
- Global Exception Handling
- Standardized API Responses

---

## API Documentation

- Interactive Swagger / OpenAPI Documentation

---

# 🛠 Tech Stack

| Category | Technology |
|-----------|------------|
| Language | Java 21 |
| Framework | Spring Boot |
| Security | Spring Security, JWT |
| Database | MySQL |
| Cache | Redis |
| ORM | Spring Data JPA / Hibernate |
| Build Tool | Maven |
| Containerization | Docker |
| Orchestration | Docker Compose |
| Cloud | AWS EC2 |
| API Documentation | Swagger / OpenAPI |
| Testing | JUnit 5, Mockito |
| Version Control | Git & GitHub |

---

# 🏗 Architecture

```
                        Internet
                            │
                            ▼
                  AWS EC2 (Ubuntu Server)
                            │
                    Docker Compose
                            │
        ┌───────────────────┼───────────────────┐
        ▼                   ▼                   ▼
 Spring Boot App         MySQL              Redis
        │
        ▼
   REST APIs + JWT
```

---

# 📂 Project Structure

```
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
├── util
└── ShortLinkAnalyticsPlatformApplication
```

---

# 🔐 Authentication Flow

```
Register
     │
     ▼
Login
     │
     ▼
Receive JWT Token
     │
     ▼
Authorize in Swagger
     │
     ▼
Access Protected APIs
```

---

# 📌 REST API Endpoints

## Authentication

| Method | Endpoint |
|--------|----------|
| POST | /auth/register |
| POST | /auth/login |

---

## URL Management

| Method | Endpoint |
|--------|----------|
| POST | /urls |
| GET | /urls/my |
| GET | /urls/{shortCode} |
| DELETE | /urls/{shortCode} |

---

## Public Endpoint

| Method | Endpoint |
|--------|----------|
| GET | /urls/redirect/{shortCode} |

---

## Analytics

| Method | Endpoint |
|--------|----------|
| GET | /urls/analytics/{shortCode} |

---

# ⚡ Redis Cache

Frequently accessed URLs are cached in Redis to improve redirect performance.

- Cache on first redirect
- Subsequent redirects served from Redis
- Automatic cache eviction on URL deletion
- Configurable cache expiration

---

# 🗄 Database Design

```
User
 │
 └────────────< ShortUrl
                    │
                    └────────────< Analytics
```

---

# 📖 Swagger Documentation

After starting the application:

Local

```
http://localhost:8081/swagger-ui/index.html
```

AWS Deployment

```
http://<EC2-PUBLIC-IP>:8081/swagger-ui/index.html
```

---

# 🐳 Running with Docker

Clone the repository

```bash
git clone https://github.com/Tumansh/shortlink-analytics-platform.git

cd shortlink-analytics-platform
```

Start the complete application

```bash
docker compose up -d
```

This starts

- Spring Boot Application
- MySQL
- Redis

Verify running containers

```bash
docker ps
```

Stop containers

```bash
docker compose down
```

Remove containers and volumes

```bash
docker compose down -v
```

---

# ⚙ Environment Variables

The application is configured using environment variables.

| Variable | Description |
|-----------|-------------|
| SERVER_PORT | Application Port |
| SPRING_DATASOURCE_URL | MySQL JDBC URL |
| SPRING_DATASOURCE_USERNAME | Database Username |
| SPRING_DATASOURCE_PASSWORD | Database Password |
| SPRING_DATA_REDIS_HOST | Redis Host |
| SPRING_DATA_REDIS_PORT | Redis Port |
| JWT_SECRET | JWT Secret Key |
| JWT_EXPIRATION | JWT Expiration Time |

---

# ☁ AWS Deployment

The application is deployed on an Ubuntu EC2 instance using Docker Compose.

Deployment includes

- Docker
- Docker Compose
- MySQL
- Redis
- Spring Boot
- Environment Variables
- Docker Hub Image

Deployment Flow

```
Developer
     │
     ▼
GitHub Repository
     │
     ▼
Docker Build
     │
     ▼
Docker Hub
     │
     ▼
AWS EC2
     │
     ▼
Docker Compose
     │
 ┌───┴────┐
 ▼        ▼
MySQL   Redis
     │
     ▼
Spring Boot
```

---

# 📦 Docker Image

```
docker pull tumansh03/shortlink-analytics-platform:latest
```

---

# 🔧 Future Enhancements

- URL Expiration Support
- QR Code Generation
- Custom Short Codes
- Rate Limiting
- Docker Multi-stage Builds
- Nginx Reverse Proxy
- HTTPS with Let's Encrypt
- CI/CD Pipeline using GitHub Actions
- Monitoring with Prometheus & Grafana

---

# 👨‍💻 Author

## Tumansh Vij

Backend Developer

**Tech Stack**

- Java
- Spring Boot
- Spring Security
- JWT
- MySQL
- Redis
- Docker
- Docker Compose
- AWS EC2
- Hibernate
- REST APIs
- Maven
- Git & GitHub

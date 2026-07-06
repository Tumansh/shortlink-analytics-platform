# 🔗 ShortLink Analytics Platform

A production-inspired backend application built using **Spring Boot** that enables users to securely create, manage, and analyze short URLs. The application is containerized with **Docker**, orchestrated using **Docker Compose**, and deployed on **AWS EC2** with **MySQL** and **Redis**.

---

## 🌐 Live Demo

### Swagger UI

**Live API Documentation**

http://16.16.136.188:8081/swagger-ui/index.html

---

## 🚀 Features

### 🔐 Authentication & Security

- User Registration
- User Login
- JWT Authentication
- BCrypt Password Encryption
- Stateless Authentication using Spring Security
- Protected REST APIs

---

### 🔗 URL Management

- Create Short URLs
- Redirect to Original URLs
- View Personal URLs
- Delete Short URLs

---

### 📊 Analytics

- Total Click Count
- Click History
- Visitor IP Tracking
- User-Agent Tracking

---

### ⚡ Performance

- Redis Caching for Frequently Accessed URLs
- Automatic Cache Eviction
- Configurable Cache TTL
- Reduced Database Queries

---

### ✅ Validation & Error Handling

- Bean Validation
- Global Exception Handling
- Standardized API Responses

---

### 📖 API Documentation

- Interactive Swagger / OpenAPI Documentation

---

# 🛠 Tech Stack

| Category | Technology |
|------------|------------------------------|
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
| Documentation | Swagger / OpenAPI |
| Testing | JUnit 5, Mockito |
| Version Control | Git & GitHub |

---

# 🏗 System Architecture

```
                           Internet
                               │
                               ▼
                      AWS EC2 (Ubuntu)
                               │
                        Docker Compose
                               │
          ┌────────────────────┼────────────────────┐
          ▼                    ▼                    ▼
    Spring Boot App         MySQL               Redis
          │
          ▼
 REST APIs + JWT Authentication
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
User Registration
        │
        ▼
User Login
        │
        ▼
JWT Token Generated
        │
        ▼
Swagger Authorization
        │
        ▼
Access Protected APIs
```

---

# 📌 REST API Endpoints

## Authentication

| Method | Endpoint |
|--------|----------|
| POST | `/auth/register` |
| POST | `/auth/login` |

---

## URL Management

| Method | Endpoint |
|--------|----------|
| POST | `/urls` |
| GET | `/urls/my` |
| GET | `/urls/{shortCode}` |
| DELETE | `/urls/{shortCode}` |

---

## Public Endpoint

| Method | Endpoint |
|--------|----------|
| GET | `/urls/redirect/{shortCode}` |

---

## Analytics

| Method | Endpoint |
|--------|----------|
| GET | `/urls/analytics/{shortCode}` |

---

# ⚡ Redis Cache

The application caches frequently accessed URLs to improve redirection performance.

### Cache Workflow

```
User Request
      │
      ▼
Redis Cache
      │
 ┌────┴────┐
 │         │
Hit      Miss
 │         │
 ▼         ▼
Return   MySQL
Response   │
           ▼
     Store in Redis
```

Features

- Cache on first redirect
- Subsequent redirects served from Redis
- Automatic cache eviction on deletion
- Configurable cache expiration

---

# 🗄 Database Design

```
User
 │
 └──────────────< ShortUrl
                       │
                       └──────────────< Analytics
```

---

# 📖 API Documentation

## Local

```
http://localhost:8081/swagger-ui/index.html
```

## Live Deployment

```
http://16.16.136.188:8081/swagger-ui/index.html
```

---

# 🐳 Docker Deployment

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

- Spring Boot
- MySQL
- Redis

Verify running containers

```bash
docker ps
```

Stop all containers

```bash
docker compose down
```

Stop and remove volumes

```bash
docker compose down -v
```

---

# ⚙ Environment Variables

The application is configured using environment variables.

| Variable | Description |
|-----------|-------------|
| SERVER_PORT | Spring Boot Port |
| SPRING_DATASOURCE_URL | MySQL JDBC URL |
| SPRING_DATASOURCE_USERNAME | Database Username |
| SPRING_DATASOURCE_PASSWORD | Database Password |
| SPRING_DATA_REDIS_HOST | Redis Host |
| SPRING_DATA_REDIS_PORT | Redis Port |
| JWT_SECRET | JWT Secret |
| JWT_EXPIRATION | JWT Expiration Time |

---

# ☁ AWS Deployment

The application is deployed on an Ubuntu EC2 instance using Docker Compose.

Deployment includes

- Docker
- Docker Compose
- Spring Boot
- MySQL
- Redis
- Environment Variables
- Docker Hub

Deployment Flow

```
Developer
      │
      ▼
Git Push
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
 ┌────┴────┐
 ▼         ▼
MySQL    Redis
      │
      ▼
Spring Boot Application
```

---

# 📦 Docker Image

```bash
docker pull tumansh03/shortlink-analytics-platform:latest
```

---

# 🚀 Running Locally

Build the project

```bash
mvn clean package
```

Run the application

```bash
mvn spring-boot:run
```

or

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

---

# 📈 Future Improvements

- Custom Short URLs
- QR Code Generation
- URL Expiration
- Rate Limiting
- User Dashboard
- Docker Multi-stage Build
- Nginx Reverse Proxy
- HTTPS using Let's Encrypt
- GitHub Actions CI/CD
- Prometheus & Grafana Monitoring

---

# 📸 Screenshots

You can add:

- Swagger UI
- Docker Containers (`docker ps`)
- AWS EC2 Deployment
- Redis Cache Demonstration

---

# 👨‍💻 Author

## Tumansh Vij

Backend Developer

### Skills Demonstrated

- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- MySQL
- Redis
- Hibernate
- REST APIs
- Docker
- Docker Compose
- AWS EC2
- Maven
- Git & GitHub

---

## ⭐ If you found this project useful, consider giving it a star!

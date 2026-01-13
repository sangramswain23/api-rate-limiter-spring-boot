# API Rate Limiter – Spring Boot

A backend service that enforces **IP-based rate limiting** on HTTP APIs using a **fixed-window strategy**, implemented at the **servlet filter level** in Spring Boot.

The project focuses on request lifecycle interception, concurrency-safe in-memory state management, and correct HTTP error handling.

---

## Features

* Fixed-window rate limiting per client IP
* Request interception using Spring `OncePerRequestFilter`
* Thread-safe in-memory storage with `ConcurrentHashMap`
* Proper HTTP `429 Too Many Requests` response
* Clean separation of Filter, Service, and Model layers
* Lightweight design with no database dependency

---

## Tech Stack

**Backend:** Java 17, Spring Boot, Spring Web  
**Concepts:** Servlet Filters, Concurrency, REST APIs  
**Tools:** Maven, Postman, GitHub  

---

## Application Request Flow

The rate-limiting logic is enforced **before** any controller is executed by intercepting incoming HTTP requests at the servlet filter level.

### Request Lifecycle

Client Request
↓
RateLimitFilter (OncePerRequestFilter)
↓
RateLimiterService.validateRequest()
↓
[ Allowed ] ─────────── Controller (/api/test) ─ 200 OK
│
└─ [ Blocked ] ─── 429 Too Many Requests

## API Overview

* **GET** `/api/test` — Test endpoint protected by rate limiting

**Behavior:**
- First N requests within the time window → `200 OK`
- Requests exceeding the limit → `429 Too Many Requests`
- Requests allowed again after the time window resets

---

## Project Architecture

```
/api-rate-limiter
├── src/main/java/com/ratelimiter
│ ├── config
│ │ └── RateLimitFilter.java
│ ├── service
│ │ ├── RateLimiterService.java
│ │ └── RateLimiterServiceImpl.java
│ ├── model
│ │ └── RequestCounter.java
│ ├── controller
│ │ └── TestController.java
│ ├── exception
│ │ └── RateLimitExceededException.java
│ └── ApiRateLimiterApplication.java
├── pom.xml
```

## Running the Project

```bash
mvn spring-boot:run
```

## The application starts on:
```
http://localhost:8080
```

## Author

Sangram Swain

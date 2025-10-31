# 🧩 POC – JWT Secure Access

Secure REST API with JWT Authentication using Spring Boot

## 📘 Overview

This Proof of Concept (POC) demonstrates how to implement JWT-based authentication in a Spring Boot application.
It provides a simple and secure way to authenticate users, issue JWT tokens, and protect API endpoints.

Users can log in using credentials, receive a Bearer Token, and use it to access secured resources.

## 🎯 Objectives

- Implement JWT Authentication in a REST API.

- Secure routes using Spring Security.

- Use stateless authentication (no sessions).

- Learn how to issue and validate JWT tokens.

- Practice Spring components such as:
    - OncePerRequestFilter
    - AuthenticationManager
    - UserDetailsService
    - SecurityFilterChain

## 🧱 Package structure

````
com.myprojecticaro.poc_jwt_secure_access
 ├── config/
 │    ├── SecurityConfig.java
 │    └── JwtAuthenticationFilter.java
 ├── controller/
 │    └── AuthController.java
 ├── dto/
 │    ├── AuthRequest.java
 │    └── AuthResponse.java
 ├── service/
 │    ├── JwtService.java
 │    └── UserService.java
 └── util/
      └── JwtUtil.java
````

## 🧠 Key Components

Component	Description
AuthController	Exposes authentication endpoints (/auth/login).
UserService	Loads user data for authentication (in-memory for this POC).
JwtService	Authenticates users and generates JWT tokens.
JwtUtil	Utility class to build and validate JWT tokens.
JwtAuthenticationFilter	Validates JWT tokens for every request.
SecurityConfig	Configures Spring Security, filters, and endpoint protection.

## ⚙️ Technologies Used

- Java 17+

- Spring Boot 3+

- Spring Security

- JJWT (io.jsonwebtoken)

- Maven or Gradle

- Jakarta Servlet API

## 🚀 Getting Started

### 1️⃣ Clone the repository

git clone https://github.com/yourusername/poc-jwt-secure-access.git
cd poc-jwt-secure-access

### 2️⃣ Build and run the application

Using Maven:

````
mvn spring-boot:run
````

Using Gradle:

````
./gradlew bootRun
````

The app will start at:

````
http://localhost:8080
````

## 🔐 Authentication Flow

1. The client sends a POST request to /auth/login with username and password.

2. The server validates the credentials using UserService.

3. If valid, the server returns a JWT Token.

4. The client includes the token in the header for future requests:

````
Authorization: Bearer <your_token>
````

6. The filter *JwtAuthenticationFilter* validates the token for each request.

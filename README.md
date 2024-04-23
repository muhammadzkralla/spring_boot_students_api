# Student Management System API

## Description

This is a RESTful API built with Spring Boot and PostgreSQL for managing students, classes, submissions, requests, and more in a student management system.

## Endpoints

### 1. Authentication

#### Register User

- **Endpoint:** `/api/auth/admin/signup`
- **Method:** `POST`
- **Request Body:**
```json
{
    "email": "email@gmail.com",
    "password": "password",
    "firstName": "John",
    "lastName": "Doe"
}
```
- **Response Body**
```json
{
    "id": 4,
    "email": "email@gmail.com",
    "password": "$2a$10$2Xn08z2qpBv9/w5mTGx7keWLip1mtEIFPqMk0kCmGQ6zlM2n0mt1q",
    "firstName": "John",
    "lastName": "Doe",
    "imageUrl": null,
    "createdAt": "2024-04-23T21:17:08.051+00:00",
    "dob": null,
    "code": 515336,
    "codeExpiredAt": "2024-04-24T21:17:08.051+00:00",
    "enabled": true,
    "authorities": [
        {
            "id": 1,
            "name": "ADMIN",
            "authority": "ADMIN"
        }
    ],
    "emailVerified": false,
    "username": "email@gmail.com",
    "accountNonExpired": true,
    "credentialsNonExpired": true,
    "accountNonLocked": true
}
```

> **Note:** You can change the role in the endpoint from admin to student to register as a student or to teacher to register as a teacher.

#### Regenerate Verification Code

- **Endpoint:** `/api/auth/regenerate-code`
- **Method:** `POST`
- **Request Body:**
```json
{
    "email": "email@gmail.com"
}
```
- **Response Body**
```json
{
    "message": "OTP regenerated successfully. Check your email for the new OTP."
}
```

#### Verify Code

- **Endpoint:** `/api/auth/verify-code`
- **Method:** `POST`
- **Request Body:**
```json
{
    "email": "email@gmail.com",
    "code": "390132"
}
```
- **Response Body**
```json
{
    "message": "Account Verified Successfully!"
}
```

> **Note:** The OTP code is sent to the user via email.

#### Login

- **Endpoint:** `/api/auth/login`
- **Method:** `POST`
- **Request Body:**
```json
{
    "email": "email@gmail.com",
    "password": "password"
}
```
- **Response Body**
```json
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpYXQiOjE3MTM5MDc4NjEsImV4cCI6MTcxNDUxMjY2MSwicm9sZXMiOlt7ImlkIjoxLCJuYW1lIjoiQURNSU4iLCJhdXRob3JpdHkiOiJBRE1JTiJ9XSwianRpIjoiZjNiZGZmM2UtODQyMS00OTExLWE3NWUtZmRkYTUwMjc4MmFiIn0.WdU93H9q0mDeFosjb2Ego1od6EhvYHWtiBs38cR9Bsn8Lv92W6Bi8t4tPCkSMp0w4DQ0Z-rpNtrgCFHRnbZVKw",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpYXQiOjE3MTM5MDc4NjEsImV4cCI6MTcxNjQ5OTg2MX0.hXrVlIYhyE6KjGMjWCui7ZZC6EujoQd3UvuTqYuCYi2jQ0ZqyPJrHn-cf6_OKZijORC3jT__sBZIsNwnqiM7DQ",
    "accessTokenExpiresIn": "2024-04-30T21:31:01.000+00:00",
    "refreshTokenExpiresIn": "2024-05-23T21:31:01.000+00:00"
}
```

#### Token Refresh

- **Endpoint:** `/api/auth/token-refresh`
- **Method:** `GET`
- **Response Body**
```json
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpYXQiOjE3MTM5MDc5NDgsImV4cCI6MTcxNDUxMjc0OCwicm9sZXMiOlt7ImlkIjoxLCJuYW1lIjoiQURNSU4iLCJhdXRob3JpdHkiOiJBRE1JTiJ9XSwianRpIjoiMjY0MGI0ZGMtMTYzZC00ZTlkLWFlNzUtMjVhZDU1YjUyMmMxIn0.proWULrXuFrluTGlrp0tLbc8yX4G6hRMtlT81jDdyjFm2JVsLdawqtL-57eoQODoa1U6hCzEsNkJ9V4pWVDwtw",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpYXQiOjE3MTM5MDc4NjEsImV4cCI6MTcxNjQ5OTg2MX0.hXrVlIYhyE6KjGMjWCui7ZZC6EujoQd3UvuTqYuCYi2jQ0ZqyPJrHn-cf6_OKZijORC3jT__sBZIsNwnqiM7DQ",
    "accessTokenExpiresIn": "2024-04-30T21:32:28.000+00:00",
    "refreshTokenExpiresIn": "2024-05-23T21:31:01.000+00:00"
}
```

> **Note:** The refresh token must be provided in the request header to receive the new tokens.

#### Reset Password

- **Endpoint:** `/api/auth/reset-password`
- **Method:** `POST`
- - **Request Body:**
```json
{
    "email": "email@gmail.com",
    "password": "new_password",
    "code": "390132"
}
```
- **Response Body**
```json
{
    "message": "Password Reset Successfully!"
}
```

> **Note:** You should request code generation before this request to receive an OTP code via the user's email.


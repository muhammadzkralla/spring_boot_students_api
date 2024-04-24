# Student Management System API

## Description

This is a RESTful API built with Spring Boot and PostgreSQL for managing students, classes, submissions, requests, and more in a student management system. <br>


> **Please Note That:**  <br> <br>
> • Any user should be authenticated and send their JWT access token to receive a response. <br> <br>
> • Any user should verify their email to be able to make a request. <br> <br>
> • The JWT access token must be valid and not expired, if it is expired, you should request to refresh token or login again. <br> <br>
> • All Admin Endpoints must be called ONLY by Admin users. If a Student or a Teacher tries to make an admin request,
>  they will receive a 403 Forbidden Response. <br> <br>
> • All Teacher Endpoints must be called ONLY by Teacher users. If a Student or an Admin tries to make an admin request,
>  they will receive a 403 Forbidden Response. <br> <br>
> • All Student Endpoints must be called ONLY by Student users. If a Teacher or an Admin tries to make an admin request,
>  they will receive a 403 Forbidden Response. <br> <br>

<h1 align = "center">  Authentication Endpoints </h1> <br>

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

> **Note:** The OTP code is sent to the user via email. <br>
> **Note:** No User of any role (Admin, Teacher, or Student) would be able to do any request if they did not verify their account.

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

> **Note:** The refresh token must be provided in the request header to receive the response.

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

<br><hr>

<h1 align = "center">  User Endpoints </h1> <br>

#### Get All Users

- **Endpoint:** `/api/users`
- **Method:** `GET`
- **Response Body:** list of users

#### Get a Certain User

- **Endpoint:** `/api/users/{userId}`
- **Method:** `GET`
- **Response Body**
```json
{
    "id": 1,
    "email": "muhammad.hesham442@gmail.com",
    "password": "$2a$10$ZsfxMKPoptP4HhoLGEXnauwU0v5mbM949q9zYM.ulrrO.zuhsj7Te",
    "firstName": "Muhammad",
    "lastName": "zkrallah",
    "imageUrl": "https://firebasestorage.googleapis.com/v0/b/spring-students-system.appspot.com/o/5aae8217-9e87-42d9-9a27-542f06cb48d5.jpeg?alt=media",
    "createdAt": "2024-04-18T17:11:41.016+00:00",
    "dob": null,
    "code": 257199,
    "codeExpiredAt": "2024-04-19T17:11:41.016+00:00",
    "enabled": true,
    "username": "muhammad.hesham442@gmail.com",
    "emailVerified": true,
    "authorities": [
        {
            "id": 1,
            "name": "ADMIN",
            "authority": "ADMIN"
        }
    ],
    "accountNonExpired": true,
    "credentialsNonExpired": true,
    "accountNonLocked": true
}
```

#### Get Classes of a User

- **Endpoint:** `/api/users/{userId}/classes`
- **Method:** `GET`
- **Response Body**
```json
[
    {
        "id": 1,
        "name": "Class1",
        "description": "This is the description for Class1."
    },
    {
        "id": 2,
        "name": "Class2",
        "description": "This is the description for Class2."
    },
    {
        "id": 3,
        "name": "Class3",
        "description": "This is the description for Class3."
    },
    {
        "id": 5,
        "name": "Class Name1",
        "description": "This is the description for The Class."
    }
]
```

#### Update User

- **Endpoint:** `/api/admin/update-class/{classId}`
- **Method:** `PUT`
- **Request Body:**
```json
{
    "firstName": "Muhammad",
    "lastName": "zkrallaaaah",
    "dob": "22-2-2004"
}
```
- **Response Body**
```json
{
    "id": 2,
    "email": "muhammad.hesham441@gmail.com",
    "password": "$2a$10$rvrF4OSYuojarYaKZhZnDe7kbuvHplFWy0zNffGmAgep9D2Lk9eLO",
    "firstName": "Muhammad",
    "lastName": "zkrallaaaah",
    "imageUrl": "https://firebasestorage.googleapis.com/v0/b/spring-students-system.appspot.com/o/668adaea-1e80-460c-9660-2015e3830bc2.jpeg?alt=media",
    "createdAt": "2024-04-18T17:11:49.487+00:00",
    "dob": "2004-02-22",
    "code": 738239,
    "codeExpiredAt": "2024-04-19T17:11:49.487+00:00",
    "enabled": true,
    "username": "muhammad.hesham441@gmail.com",
    "emailVerified": true,
    "authorities": [
        {
            "id": 2,
            "name": "TEACHER",
            "authority": "TEACHER"
        }
    ],
    "accountNonExpired": true,
    "credentialsNonExpired": true,
    "accountNonLocked": true
}
```

#### Get Requests of a User

- **Endpoint:** `/api/users/{userId}/requests`
- **Method:** `GET`
- **Response Body**
```json
[
    {
        "id": 1,
        "user": {
            "id": 3,
            "email": "muhammad.hesham440@gmail.com",
            "password": "$2a$10$UZmIpcjMaijPzpzM60lEnOMyijLDP52q0G060TTtdfNxqwlhGOGtK",
            "firstName": "Muhammad",
            "lastName": "zkrallah",
            "imageUrl": "https://firebasestorage.googleapis.com/v0/b/spring-students-system.appspot.com/o/50ce094e-f130-4340-ac06-0115900f0bde.png?alt=media",
            "createdAt": "2024-04-18T17:11:55.769+00:00",
            "dob": null,
            "code": 393381,
            "codeExpiredAt": "2024-04-20T20:29:18.239+00:00",
            "enabled": true,
            "username": "muhammad.hesham440@gmail.com",
            "emailVerified": true,
            "authorities": [
                {
                    "id": 3,
                    "name": "STUDENT",
                    "authority": "STUDENT"
                }
            ],
            "accountNonExpired": true,
            "credentialsNonExpired": true,
            "accountNonLocked": true
        },
        "requestedClass": {
            "id": 1,
            "name": "Class1",
            "description": "This is the description for Class1."
        },
        "status": "APPROVED",
        "timestamp": "2024-04-24T01:22:27.698+00:00"
    }
]
```

#### Upload Profile Photo

- **Endpoint:** `/api/users/{userId}/upload-image`
- **Method:** `POST`
- **Response Body**
```json
{
    "message": "https://firebasestorage.googleapis.com/v0/b/spring-students-system.appspot.com/o/a163d0ea-2c1f-413c-b65f-217a23d78fb6.jpeg?alt=media"
}
```

> **Note:** This is a MULTIPART request, this meaning that you should pass the photo as a request paramenter called `file`.


<h1 align = "center">  Admin Endpoints </h1> <br>

> **Note:** All Admin Endpoints must be called ONLY by Admin users. If a Student or a Teacher tries to do an admin request, you'll receive a 403 Forbidden Response. 

#### Create Class

- **Endpoint:** `/api/admin/create-class`
- **Method:** `POST`
- **Request Body:**
```json
{
    "name": "Class Name",
    "description": "This is the description for The Class."
}
```
- **Response Body**
```json
{
    "id": 4,
    "name": "Class Name",
    "description": "This is the description for The Class."
}
```

> **Note:** An Admin token must be provided in the request header to receive the response.

#### Update Class

- **Endpoint:** `/api/admin/update-class/{classId}`
- **Method:** `PUT`
- **Request Body:**
```json
{
    "name": "Class4",
    "description": "This is the modified description for class4"
}
```
- **Response Body**
```json
{
    "id": 4,
    "name": "Class4",
    "description": "This is the modified description for class4"
}
```

> **Note:** An Admin token must be provided in the request header to receive the response.

#### Delete Class

- **Endpoint:** `/api/admin/delete-class/{classId}`
- **Method:** `DELETE`
- **Response Body**
```json
{
    "message": "Class Deleted Successfully!"
}
```

> **Note:** An Admin token must be provided in the request header to receive the response.

#### Add a User to a Class

- **Endpoint:** `/api/admin/add/{userId}/to/{classId}`
- **Method:** `GET`
- **Response Body**
```json
{
    "message": "User Added to Class."
}
```

> **Note:** An Admin token must be provided in the request header to receive the response.

#### Remove a User from a Class

- **Endpoint:** `/api/admin/remove/{userId}/from/{classId}`
- **Method:** `GET`
- **Response Body**
```json
{
    "message": "User Removed from Class.."
}
```

> **Note:** An Admin token must be provided in the request header to receive the response.

#### Approve User Request

- **Endpoint:** `/api/admin/approve-request/{requestId}`
- **Method:** `PUT`
- **Response Body**
```json
{
    "id": 1,
    "user": {
        "id": 3,
        "email": "muhammad.hesham440@gmail.com",
        "password": "$2a$10$UZmIpcjMaijPzpzM60lEnOMyijLDP52q0G060TTtdfNxqwlhGOGtK",
        "firstName": "Muhammad",
        "lastName": "zkrallah",
        "imageUrl": "https://firebasestorage.googleapis.com/v0/b/spring-students-system.appspot.com/o/50ce094e-f130-4340-ac06-0115900f0bde.png?alt=media",
        "createdAt": "2024-04-18T17:11:55.769+00:00",
        "dob": null,
        "code": 393381,
        "codeExpiredAt": "2024-04-20T20:29:18.239+00:00",
        "enabled": true,
        "username": "muhammad.hesham440@gmail.com",
        "emailVerified": true,
        "authorities": [
            {
                "id": 3,
                "name": "STUDENT",
                "authority": "STUDENT"
            }
        ],
        "accountNonExpired": true,
        "credentialsNonExpired": true,
        "accountNonLocked": true
    },
    "requestedClass": {
        "id": 1,
        "name": "Class1",
        "description": "This is the description for Class1."
    },
    "status": "APPROVED",
    "timestamp": "2024-04-24T01:22:27.698+00:00"
}
```

> **Note:** An Admin token must be provided in the request header to receive the response.

#### Decline User Request

- **Endpoint:** `/api/admin/decline-request/{requestId}`
- **Method:** `PUT`
- **Response Body**
```json
{
    "id": 1,
    "user": {
        "id": 3,
        "email": "muhammad.hesham440@gmail.com",
        "password": "$2a$10$UZmIpcjMaijPzpzM60lEnOMyijLDP52q0G060TTtdfNxqwlhGOGtK",
        "firstName": "Muhammad",
        "lastName": "zkrallah",
        "imageUrl": "https://firebasestorage.googleapis.com/v0/b/spring-students-system.appspot.com/o/50ce094e-f130-4340-ac06-0115900f0bde.png?alt=media",
        "createdAt": "2024-04-18T17:11:55.769+00:00",
        "dob": null,
        "code": 393381,
        "codeExpiredAt": "2024-04-20T20:29:18.239+00:00",
        "enabled": true,
        "username": "muhammad.hesham440@gmail.com",
        "emailVerified": true,
        "authorities": [
            {
                "id": 3,
                "name": "STUDENT",
                "authority": "STUDENT"
            }
        ],
        "accountNonExpired": true,
        "credentialsNonExpired": true,
        "accountNonLocked": true
    },
    "requestedClass": {
        "id": 1,
        "name": "Class1",
        "description": "This is the description for Class1."
    },
    "status": "DECLINED",
    "timestamp": "2024-04-24T01:22:27.698+00:00"
}
```

> **Note:** An Admin token must be provided in the request header to receive the response.

<br><hr>


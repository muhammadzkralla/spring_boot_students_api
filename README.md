# Student Management System API

## Description

This is a RESTful API built with Spring Boot and PostgreSQL for managing students, classes, submissions, requests, and more in a student management system. <br>


> **Please Note That:**  <br> <br>
> • Any user should be authenticated and send their JWT access token to receive a response. <br> <br>
> • Any user should verify their email to be able to make a request. <br> <br>
> • The JWT access token must be valid and not expired, if it is expired, you should request to refresh token or login again. <br> <br>
> • All Admin Endpoints must be called ONLY by Admin users. If a Student or a Teacher tries to make an admin request,
>  they will receive a 403 Forbidden Response. <br> <br>
> • All Teacher Endpoints must be called ONLY by Teacher users. If a Student or an Admin tries to make a Teacher request,
>  they will receive a 403 Forbidden Response. <br> <br>
> • All Student Endpoints must be called ONLY by Student users. If a Teacher or an Admin tries to make a Student request,
>  they will receive a 403 Forbidden Response. <br> <br>

<h1 align = "center">  Authentication Endpoints </h1> <br>

<h2 align = "center">  Register User </h2>

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
- **Response Body:**
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

<h2 align = "center">  Regenerate Verification Code </h2>

- **Endpoint:** `/api/auth/regenerate-code`
- **Method:** `POST`
- **Request Body:**
```json
{
    "email": "email@gmail.com"
}
```
- **Response Body:**
```json
{
    "message": "OTP regenerated successfully. Check your email for the new OTP."
}
```

<h2 align = "center">  Verify Code </h2>

- **Endpoint:** `/api/auth/verify-code`
- **Method:** `POST`
- **Request Body:**
```json
{
    "email": "email@gmail.com",
    "code": "390132"
}
```
- **Response Body:**
```json
{
    "message": "Account Verified Successfully!"
}
```

> **Note:** The OTP code is sent to the user via email. <br>
> **Note:** No User of any role (Admin, Teacher, or Student) would be able to do any request if they did not verify their account.

<h2 align = "center">  Login </h2>

- **Endpoint:** `/api/auth/login`
- **Method:** `POST`
- **Request Body:**
```json
{
    "email": "email@gmail.com",
    "password": "password"
}
```
- **Response Body:**
```json
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpYXQiOjE3MTM5MDc4NjEsImV4cCI6MTcxNDUxMjY2MSwicm9sZXMiOlt7ImlkIjoxLCJuYW1lIjoiQURNSU4iLCJhdXRob3JpdHkiOiJBRE1JTiJ9XSwianRpIjoiZjNiZGZmM2UtODQyMS00OTExLWE3NWUtZmRkYTUwMjc4MmFiIn0.WdU93H9q0mDeFosjb2Ego1od6EhvYHWtiBs38cR9Bsn8Lv92W6Bi8t4tPCkSMp0w4DQ0Z-rpNtrgCFHRnbZVKw",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpYXQiOjE3MTM5MDc4NjEsImV4cCI6MTcxNjQ5OTg2MX0.hXrVlIYhyE6KjGMjWCui7ZZC6EujoQd3UvuTqYuCYi2jQ0ZqyPJrHn-cf6_OKZijORC3jT__sBZIsNwnqiM7DQ",
    "accessTokenExpiresIn": "2024-04-30T21:31:01.000+00:00",
    "refreshTokenExpiresIn": "2024-05-23T21:31:01.000+00:00"
}
```

<h2 align = "center">  Token Refresh </h2>

- **Endpoint:** `/api/auth/token-refresh`
- **Method:** `GET`
- **Response Body:**
```json
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpYXQiOjE3MTM5MDc5NDgsImV4cCI6MTcxNDUxMjc0OCwicm9sZXMiOlt7ImlkIjoxLCJuYW1lIjoiQURNSU4iLCJhdXRob3JpdHkiOiJBRE1JTiJ9XSwianRpIjoiMjY0MGI0ZGMtMTYzZC00ZTlkLWFlNzUtMjVhZDU1YjUyMmMxIn0.proWULrXuFrluTGlrp0tLbc8yX4G6hRMtlT81jDdyjFm2JVsLdawqtL-57eoQODoa1U6hCzEsNkJ9V4pWVDwtw",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpYXQiOjE3MTM5MDc4NjEsImV4cCI6MTcxNjQ5OTg2MX0.hXrVlIYhyE6KjGMjWCui7ZZC6EujoQd3UvuTqYuCYi2jQ0ZqyPJrHn-cf6_OKZijORC3jT__sBZIsNwnqiM7DQ",
    "accessTokenExpiresIn": "2024-04-30T21:32:28.000+00:00",
    "refreshTokenExpiresIn": "2024-05-23T21:31:01.000+00:00"
}
```

> **Note:** The refresh token must be provided in the request header to receive the response.

<h2 align = "center">  Reset Password </h2>

- **Endpoint:** `/api/auth/reset-password`
- **Method:** `POST`
- **Request Body:**
```json
{
    "email": "email@gmail.com",
    "password": "new_password",
    "code": "390132"
}
```
- **Response Body:**
```json
{
    "message": "Password Reset Successfully!"
}
```

> **Note:** You should request code generation before this request to receive an OTP code via the user's email.

<br><hr>

<h1 align = "center">  User Endpoints </h1> <br>

<h2 align = "center">  Get All Users </h2>

- **Endpoint:** `/api/users`
- **Method:** `GET`
- **Response Body:** `list of users`

<h2 align = "center">  Get a Certain User </h2>

- **Endpoint:** `/api/users/{userId}`
- **Method:** `GET`
- **Response Body:**
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

<h2 align = "center">  Get Classes of a User </h2>

- **Endpoint:** `/api/users/{userId}/classes`
- **Method:** `GET`
- **Response Body:**
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

<h2 align = "center">  Update a User </h2>

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
- **Response Body:**
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

<h2 align = "center">  Get Requests of a User </h2>

- **Endpoint:** `/api/users/{userId}/requests`
- **Method:** `GET`
- **Response Body:**
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

<h2 align = "center">  Upload Profile Photo </h2>

- **Endpoint:** `/api/users/{userId}/upload-image`
- **Method:** `POST`
- **Response Body:**
```json
{
    "message": "https://firebasestorage.googleapis.com/v0/b/spring-students-system.appspot.com/o/a163d0ea-2c1f-413c-b65f-217a23d78fb6.jpeg?alt=media"
}
```

> **Note:** This is a MULTIPART request, this meaning that you should pass the photo as a request paramenter called `file`.


<h1 align = "center">  Admin Endpoints </h1> <br>

> **Note:** All Admin Endpoints must be called ONLY by Admin users. If a Student or a Teacher tries to make an Admin request, they will receive a 403 Forbidden Response. 

<h2 align = "center">  Create a Class </h2>

- **Endpoint:** `/api/admin/create-class`
- **Method:** `POST`
- **Request Body:**
```json
{
    "name": "Class Name",
    "description": "This is the description for The Class."
}
```
- **Response Body:**
```json
{
    "id": 4,
    "name": "Class Name",
    "description": "This is the description for The Class."
}
```

<h2 align = "center">  Update a Class </h2>

- **Endpoint:** `/api/admin/update-class/{classId}`
- **Method:** `PUT`
- **Request Body:**
```json
{
    "name": "Class4",
    "description": "This is the modified description for class4"
}
```
- **Response Body:**
```json
{
    "id": 4,
    "name": "Class4",
    "description": "This is the modified description for class4"
}
```

<h2 align = "center">  Delete a Class </h2>

- **Endpoint:** `/api/admin/delete-class/{classId}`
- **Method:** `DELETE`
- **Response Body:**
```json
{
    "message": "Class Deleted Successfully!"
}
```

<h2 align = "center">  Add a User to a Class </h2>

- **Endpoint:** `/api/admin/add/{userId}/to/{classId}`
- **Method:** `GET`
- **Response Body:**
```json
{
    "message": "User Added to Class."
}
```

<h2 align = "center">  Remove a User from a Class </h2>

- **Endpoint:** `/api/admin/remove/{userId}/from/{classId}`
- **Method:** `GET`
- **Response Body:**
```json
{
    "message": "User Removed from Class.."
}
```

<h2 align = "center">  Approve a User's Request </h2>

- **Endpoint:** `/api/admin/approve-request/{requestId}`
- **Method:** `PUT`
- **Response Body:**
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

<h2 align = "center">  Decline a User's Request </h2>

- **Endpoint:** `/api/admin/decline-request/{requestId}`
- **Method:** `PUT`
- **Response Body:**
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

<br><hr>

<h1 align = "center">  Student Endpoints </h1> <br>

> **Note:** All Student Endpoints must be called ONLY by Student users. If an Admin or a Teacher tries to make an Student request, they will receive a 403 Forbidden Response. 

<h2 align = "center">  Create a User's Request to Join a Class </h2>

- **Endpoint:** `/api/student/request/{userId}/to/{classId}`
- **Method:** `POST`
- **Response Body:**
```json
{
    "id": 2,
    "user": {
        "id": 4,
        "email": "email@gmail.com",
        "password": "$2a$10$TjkvfMLHNwdKRWZCAXv2aeeScyKKxlQGypV5e4GMaDcgjRJxIVKEe",
        "firstName": "John",
        "lastName": "Doe",
        "imageUrl": null,
        "createdAt": "2024-04-23T21:17:08.051+00:00",
        "dob": null,
        "code": 390132,
        "codeExpiredAt": "2024-04-24T21:24:36.604+00:00",
        "enabled": true,
        "emailVerified": true,
        "username": "email@gmail.com",
        "accountNonLocked": true,
        "authorities": [
            {
                "id": 1,
                "name": "ADMIN",
                "authority": "ADMIN"
            }
        ],
        "accountNonExpired": true,
        "credentialsNonExpired": true
    },
    "requestedClass": {
        "id": 1,
        "name": "Class1",
        "description": "This is the description for Class1."
    },
    "status": "WAITING",
    "timestamp": "2024-04-25T02:58:13.973+00:00"
}
```

<h2 align = "center">  Create a User's Submission to a Task </h2>

- **Endpoint:** `/api/students/submit/{userId}/{taskId}`
- **Method:** `POST`
- **Request Body:**
```json
{
    "link": "github.com/muhammadzkrallaah",
    "additional": null,
    "grade": 0
}
```
- **Response Body:**
```json
{
    "id": 1,
    "task": {
        "id": 1,
        "title": "Task3",
        "description": "description3",
        "due": "0023-10-14T22:00:00.000+00:00",
        "sources": [],
        "targetedClass": {
            "id": 1,
            "name": "Class1",
            "description": "This is the description for Class1."
        }
    },
    "user": {
        "id": 1,
        "email": "muhammad.hesham442@gmail.com",
        "password": "$2a$10$ZsfxMKPoptP4HhoLGEXnauwU0v5mbM949q9zYM.ulrrO.zuhsj7Te",
        "firstName": "Muhammad",
        "lastName": "zkrallah",
        "imageUrl": "https://firebasestorage.googleapis.com/v0/b/spring-students-system.appspot.com/o/a163d0ea-2c1f-413c-b65f-217a23d78fb6.jpeg?alt=media",
        "createdAt": "2024-04-18T17:11:41.016+00:00",
        "dob": null,
        "code": 257199,
        "codeExpiredAt": "2024-04-19T17:11:41.016+00:00",
        "enabled": true,
        "emailVerified": true,
        "username": "muhammad.hesham442@gmail.com",
        "accountNonLocked": true,
        "authorities": [
            {
                "id": 1,
                "name": "ADMIN",
                "authority": "ADMIN"
            }
        ],
        "accountNonExpired": true,
        "credentialsNonExpired": true
    },
    "grade": 0,
    "additional": null,
    "link": "github.com/muhammadzkrallaah"
}
```

<h2 align = "center">  Get a User's Submission </h2>

- **Endpoint:** `/api/students/submission/{submissionId}`
- **Method:** `GET`
- **Response Body:**
```json
{
    "id": 1,
    "task": {
        "id": 1,
        "title": "Task3",
        "description": "description3",
        "due": "0023-10-14T22:00:00.000+00:00",
        "sources": [],
        "targetedClass": {
            "id": 1,
            "name": "Class1",
            "description": "This is the description for Class1."
        }
    },
    "user": {
        "id": 1,
        "email": "muhammad.hesham442@gmail.com",
        "password": "$2a$10$ZsfxMKPoptP4HhoLGEXnauwU0v5mbM949q9zYM.ulrrO.zuhsj7Te",
        "firstName": "Muhammad",
        "lastName": "zkrallah",
        "imageUrl": "https://firebasestorage.googleapis.com/v0/b/spring-students-system.appspot.com/o/a163d0ea-2c1f-413c-b65f-217a23d78fb6.jpeg?alt=media",
        "createdAt": "2024-04-18T17:11:41.016+00:00",
        "dob": null,
        "code": 257199,
        "codeExpiredAt": "2024-04-19T17:11:41.016+00:00",
        "enabled": true,
        "emailVerified": true,
        "username": "muhammad.hesham442@gmail.com",
        "accountNonLocked": true,
        "authorities": [
            {
                "id": 1,
                "name": "ADMIN",
                "authority": "ADMIN"
            }
        ],
        "accountNonExpired": true,
        "credentialsNonExpired": true
    },
    "grade": 0,
    "additional": null,
    "link": "github.com/muhammadzkrallaah"
}
```

<h2 align = "center">  Get Submissions of a User </h2>

- **Endpoint:** `/api/students/user/{userId}/submissions"`
- **Method:** `GET`
- **Response Body:** `list of all user's submissions.`

  <h2 align = "center">  Delete a User's Submission </h2>

- **Endpoint:** `/api/students/user/{userId}/submissions"`
- **Method:** `DELETE`
- **Response Body:**
```json
{
    "message": "Submission Deleted Successfully!"
}
```
  


# Student Management System API

## Introduction

This is a RESTful API built with Spring Boot and PostgreSQL for managing students, classes, submissions, requests, and more in a student management system. <br> <br>

Teachers can create classes and add tasks with resources, and students will be able to submit their solutions to the tasks after requesting to join the class and being approved, and later teachers can grade the submissions and add announcements. <br> <br>

• You can see a sample client mobile app on this API from here : [Mobile App](https://github.com/muhammadzkralla/Z-Students "Mobile App") 

## System Design Overview

![drawSQL-image-export-2024-05-10](https://github.com/muhammadzkralla/spring_boot_students_api/assets/54005330/47f7b7a8-6b88-4007-97f6-074380291761)

## Installation

To start using the API, you should clone the repository :
```link
https://github.com/muhammadzkralla/spring_boot_students_api.git
```
• Email service is disabled for testing environment and the verification OTP is always `111111`. If you want to enable it, go to `MailSenderServiceImpl` class and remove the if condition,
and inside `AuthenticationServiceImpl` class, modify the `generateRandomOtp` function. <br> <br>

• Rename the `env` file in the resources package to `application.yml` and update it with your database credentials and environment information. <br> <br>

• Refer to this [Article](https://medium.com/@poojithairosha/image-uploading-with-spring-boot-firebase-cloud-storage-e5ef2fbf942d "Article") 
to know how to integrate your own Firebase Cloud Storage to the project. <br>
Once you have downloaded the JSON file: <br> <br>
1- add it under the resources package. <br>
2- Replace the `CREDENTIALS_FILE_PATH` variable in the `StorageServiceImpl` class with the path of your JSON file.

That's it, now you are good to go! Run the project in Inellij. <br> <br>

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

## Api Response

All The below responses are returned in a default ApiResponse format that looks like this :
```
{
    "success": ,
    "message": ,
    "data": 
}
```

<h1 align = "center">  Authentication Endpoints </h1> <br>

• Refer to this Postman [Collection](https://www.postman.com/zkrallah/workspace/zschool/collection/17742019-30983c98-7dbc-4d84-b824-886babd2e2d5?action=share&creator=17742019 "Collection") 

<h1 align = "center">  Admin Endpoints </h1> <br>

• Refer to this Postman [Collection](https://www.postman.com/zkrallah/workspace/zschool/collection/17742019-5c4d7a0f-7add-4dcc-bbbf-53194ec240a3?action=share&creator=17742019 "Collection") 

<h1 align = "center">  Teacher Endpoints </h1> <br>

• Refer to this Postman [Collection](https://www.postman.com/zkrallah/workspace/zschool/collection/17742019-5697ab94-fbd7-4c9f-bd48-e98bf8052f73?action=share&creator=17742019 "Collection") 

<h1 align = "center">  Student Endpoints </h1> <br>

• Refer to this Postman [Collection](https://www.postman.com/zkrallah/workspace/zschool/collection/17742019-747d4d5a-b143-43c9-94a2-40b06d709a69?action=share&creator=17742019 "Collection") 

<h1 align = "center">  User Endpoints </h1> <br>

• Refer to this Postman [Collection](https://www.postman.com/zkrallah/workspace/zschool/collection/17742019-7a2225d3-b58a-4a0c-9953-a54e94220302?action=share&creator=17742019 "Collection") 

<h1 align = "center">  Class Endpoints </h1> <br>

• Refer to this Postman [Collection](https://www.postman.com/zkrallah/workspace/zschool/collection/17742019-7a2225d3-b58a-4a0c-9953-a54e94220302?action=share&creator=17742019 "Collection") 
  

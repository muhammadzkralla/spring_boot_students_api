package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.LoginUserDto;
import com.zkrallah.students_api.dtos.RegisterUserDto;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.response.LoginResponse;
import com.zkrallah.students_api.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        LoginResponse loginResponse = authenticationService.login(loginUserDto);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @PostMapping("/admin/signup")
    public ResponseEntity<User> adminRegister(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto, "ADMIN");
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/teacher/signup")
    public ResponseEntity<User> teacherRegister(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto, "TEACHER");
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/student/signup")
    public ResponseEntity<User> studentRegister(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto, "STUDENT");
        return ResponseEntity.ok(registeredUser);
    }

}

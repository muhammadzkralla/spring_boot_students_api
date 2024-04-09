package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.LoginUserDto;
import com.zkrallah.students_api.dtos.RegenerateCodeDto;
import com.zkrallah.students_api.dtos.RegisterUserDto;
import com.zkrallah.students_api.dtos.VerifyCodeDto;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.response.LoginResponse;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.auth.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/admin/signup")
    public ResponseEntity<?> adminRegister(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User registeredUser = authenticationService.signup(registerUserDto, "ADMIN");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to register admin user: " + e.getMessage()));
        }
    }

    @PostMapping("/teacher/signup")
    public ResponseEntity<?> teacherRegister(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User registeredUser = authenticationService.signup(registerUserDto, "TEACHER");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to register teacher user: " + e.getMessage()));
        }
    }

    @PostMapping("/student/signup")
    public ResponseEntity<?> studentRegister(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User registeredUser = authenticationService.signup(registerUserDto, "STUDENT");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to register student user: " + e.getMessage()));
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<MessageResponse> verifyCode(@RequestBody VerifyCodeDto verifyCodeDto) {
        try {
            boolean success = authenticationService.verifyUser(verifyCodeDto);
            if (success) {
                return ResponseEntity.ok(new MessageResponse("Account Verified Successfully!"));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new MessageResponse("Verification Failed," + " Code Might Be Invalid Or Expired!"));
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Internal Server Error: " + e.getMessage()));
        }
    }

    @PostMapping("/regenerate-code")
    public ResponseEntity<MessageResponse> regenerateOtp(@RequestBody RegenerateCodeDto regenerateCodeDto) {
        try {
            authenticationService.regenerateOtp(regenerateCodeDto.getEmail());
            return ResponseEntity.ok(new MessageResponse("OTP regenerated successfully." +
                    " Check your email for the new OTP."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("User not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Internal Server Error: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto) {
        try {
            LoginResponse loginResponse = authenticationService.login(loginUserDto);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to login: " + e.getMessage()));
        }
    }

    @GetMapping("/token-refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            LoginResponse loginResponse = authenticationService.refreshToken(authorizationHeader);
            if (loginResponse == null) throw new NullPointerException();
            return ResponseEntity.ok().body(loginResponse);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Bad Request: " + e.getMessage()));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Refresh Token Expired: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Unexpected Error: " + e.getMessage()));
        }
    }
}
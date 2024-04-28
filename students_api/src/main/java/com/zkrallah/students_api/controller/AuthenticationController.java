package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.*;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.response.ApiResponse;
import com.zkrallah.students_api.response.LoginResponse;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.auth.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import static com.zkrallah.students_api.response.ApiResponse.createFailureResponse;
import static com.zkrallah.students_api.response.ApiResponse.createSuccessResponse;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/admin/signup")
    public ResponseEntity<ApiResponse<User>> adminRegister(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User registeredUser = authenticationService.signup(registerUserDto, "ADMIN");
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(registeredUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to register admin user: " + e.getMessage()));
        }
    }

    @PostMapping("/teacher/signup")
    public ResponseEntity<ApiResponse<User>> teacherRegister(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User registeredUser = authenticationService.signup(registerUserDto, "TEACHER");
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(registeredUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to register teacher user: " + e.getMessage()));
        }
    }

    @PostMapping("/student/signup")
    public ResponseEntity<ApiResponse<User>> studentRegister(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User registeredUser = authenticationService.signup(registerUserDto, "STUDENT");
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(registeredUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to register student user: " + e.getMessage()));
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<ApiResponse<MessageResponse>> verifyCode(@RequestBody VerifyCodeDto verifyCodeDto) {
        try {
            boolean success = authenticationService.verifyUser(verifyCodeDto);
            if (success) {
                return ResponseEntity
                        .ok(createSuccessResponse(new MessageResponse("Account Verified Successfully!")));
            } else {
                return ResponseEntity.badRequest()
                        .body(createFailureResponse("Verification Failed, Code Might Be Invalid Or Expired!"));
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(createFailureResponse("User not found"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Internal Server Error: " + e.getMessage()));
        }
    }

    @PostMapping("/regenerate-code")
    public ResponseEntity<ApiResponse<MessageResponse>> regenerateOtp(@RequestBody RegenerateCodeDto regenerateCodeDto) {
        try {
            authenticationService.regenerateOtp(regenerateCodeDto.getEmail());
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("OTP regenerated successfully." +
                    " Check your email for the new OTP.")));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("User not found"));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(createFailureResponse("Internal Server Error: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginUserDto loginUserDto) {
        try {
            LoginResponse loginResponse = authenticationService.login(loginUserDto);
            return ResponseEntity.ok(createSuccessResponse(loginResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to login: " + e.getMessage()));
        }
    }

    @GetMapping("/token-refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            LoginResponse loginResponse = authenticationService.refreshToken(authorizationHeader);
            if (loginResponse == null) throw new NullPointerException();
            return ResponseEntity.ok(createSuccessResponse(loginResponse));
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Bad Request: " + e.getMessage()));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(UNAUTHORIZED)
                    .body(createFailureResponse("Refresh Token Expired: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(createFailureResponse("Unexpected Error: " + e.getMessage()));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<MessageResponse>> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        try {
            boolean success = authenticationService.resetPassword(resetPasswordDto);
            if (success) {
                return ResponseEntity
                        .ok(createSuccessResponse(new MessageResponse("Password Reset Successfully!")));
            } else {
                return ResponseEntity.status(FORBIDDEN)
                        .body(createFailureResponse("Reset Failed," + " Code Might Be Invalid Or Expired!"));
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(createFailureResponse("User not found"));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(createFailureResponse("Internal Server Error: " + e.getMessage()));
        }
    }
}
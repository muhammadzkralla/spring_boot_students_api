package com.zkrallah.students_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zkrallah.students_api.dtos.LoginUserDto;
import com.zkrallah.students_api.dtos.RegisterUserDto;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.response.LoginResponse;
import com.zkrallah.students_api.service.AuthenticationService;
import com.zkrallah.students_api.service.JwtService;
import com.zkrallah.students_api.service.user.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String accessToken = jwtService.generateAccessToken(authenticatedUser);
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setAccessTokenExpiresIn(jwtService.extractExpiration(accessToken));
        loginResponse.setRefreshTokenExpiresIn(jwtService.extractExpiration(refreshToken));

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                final String refreshToken = authHeader.substring(7);

                if (jwtService.validateToken(refreshToken)) {
                    final String email = jwtService.getEmailFromToken(refreshToken);
                    User user = userService.getUser(email);

                    String accessToken = jwtService.generateAccessToken(user);

                    LoginResponse loginResponse = new LoginResponse();
                    loginResponse.setAccessToken(accessToken);
                    loginResponse.setRefreshToken(refreshToken);
                    loginResponse.setAccessTokenExpiresIn(jwtService.extractExpiration(accessToken));
                    loginResponse.setRefreshTokenExpiresIn(jwtService.extractExpiration(refreshToken));

                    new ObjectMapper().writeValue(response.getOutputStream(), loginResponse);
                }
            } catch (ExpiredJwtException e) {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                PrintWriter out = response.getWriter();
                out.println("{\"message\":\"" + e.getMessage() + "\"}");
                log.error("Token Expired: " + e.getMessage());
            } catch (Exception e) {
                log.error("JwtAuthenticationFilterError" + e.getMessage());
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Refresh Token is Invalid");
        }
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

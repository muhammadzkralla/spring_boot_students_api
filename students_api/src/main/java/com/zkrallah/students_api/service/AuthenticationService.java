package com.zkrallah.students_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zkrallah.students_api.dtos.LoginUserDto;
import com.zkrallah.students_api.dtos.RegisterUserDto;
import com.zkrallah.students_api.entity.Role;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.repository.RoleRepository;
import com.zkrallah.students_api.repository.UserRepository;
import com.zkrallah.students_api.response.ErrorResponse;
import com.zkrallah.students_api.response.LoginResponse;
import com.zkrallah.students_api.service.user.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public LoginResponse login(LoginUserDto loginUserDto) {
        User authenticatedUser = authenticate(loginUserDto);

        String accessToken = jwtService.generateAccessToken(authenticatedUser);
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setAccessTokenExpiresIn(jwtService.extractExpiration(accessToken));
        loginResponse.setRefreshTokenExpiresIn(jwtService.extractExpiration(refreshToken));

        return loginResponse;
    }

    private User authenticate(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );

        return userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow();
    }

    public User signup(RegisterUserDto input, String role) {
        User user = createUserFromDto(input);
        Role userRole = roleRepository.findByName(role).orElseThrow();
        user.getRoles().add(userRole);
        return userRepository.save(user);
    }

    private User createUserFromDto(RegisterUserDto input) {
        User user = new User();
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        return user;
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                final String refreshToken = authHeader.substring(7);
                if (jwtService.validateToken(refreshToken)) handleValidToken(refreshToken, response);
            } catch (ExpiredJwtException e) {
                handleExpiredJwtException(e, response);
            } catch (Exception e) {
                handleException(e, response);
            }
        } else {
            handleInvalidRefreshToken(response);
        }
    }

    private LoginResponse createLoginResponse(String accessToken, String refreshToken) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setAccessTokenExpiresIn(jwtService.extractExpiration(accessToken));
        loginResponse.setRefreshTokenExpiresIn(jwtService.extractExpiration(refreshToken));
        return loginResponse;
    }

    private void handleValidToken(String refreshToken, HttpServletResponse response) throws IOException {
        final String email = jwtService.getEmailFromToken(refreshToken);
        User user = userService.getUser(email);

        String accessToken = jwtService.generateAccessToken(user);
        LoginResponse loginResponse = createLoginResponse(accessToken, refreshToken);

        objectMapper.writeValue(response.getOutputStream(), loginResponse);
    }

    private void handleExpiredJwtException(ExpiredJwtException e, HttpServletResponse response) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        objectMapper.writeValue(response.getOutputStream(), errorResponse);

        log.error("Token Expired: " + e.getMessage());
        e.printStackTrace();
    }

    private void handleException(Exception e, HttpServletResponse response) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        objectMapper.writeValue(response.getOutputStream(), errorResponse);

        log.error("JwtAuthenticationFilterError" + e.getMessage());
        e.printStackTrace();
    }

    private void handleInvalidRefreshToken(HttpServletResponse response) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Refresh Token is not valid, please login again.");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}

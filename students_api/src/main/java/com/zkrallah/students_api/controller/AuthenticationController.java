package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.LoginUserDto;
import com.zkrallah.students_api.dtos.RegisterUserDto;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.response.LoginResponse;
import com.zkrallah.students_api.service.AuthenticationService;
import com.zkrallah.students_api.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/admin/signup")
    public ResponseEntity<User> adminRegister(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String accessToken = jwtService.generateAccessToken(authenticatedUser);
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setAccessTokenExpiresIn(jwtService.getAccessTokenExpiration());
        loginResponse.setRefreshTokenExpiresIn(jwtService.getRefreshTokenExpiration());

        return ResponseEntity.ok(loginResponse);
    }

}

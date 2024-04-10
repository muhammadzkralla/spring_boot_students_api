package com.zkrallah.students_api.service.auth;

import com.zkrallah.students_api.dtos.LoginUserDto;
import com.zkrallah.students_api.dtos.RegisterUserDto;
import com.zkrallah.students_api.dtos.ResetPasswordDto;
import com.zkrallah.students_api.dtos.VerifyCodeDto;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.response.LoginResponse;

public interface AuthenticationService {
    LoginResponse login(LoginUserDto loginUserDto);
    User signup(RegisterUserDto registerUserDto, String role);
    LoginResponse refreshToken(String authHeader);
    boolean verifyUser(VerifyCodeDto verifyCodeDto);
    void regenerateOtp(String email);
    boolean resetPassword(ResetPasswordDto resetPasswordDto);
}

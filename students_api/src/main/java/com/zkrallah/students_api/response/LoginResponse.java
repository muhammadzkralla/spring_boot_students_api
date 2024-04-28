package com.zkrallah.students_api.response;

import com.zkrallah.students_api.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpiresIn;
    private Date refreshTokenExpiresIn;
    private User user;
}
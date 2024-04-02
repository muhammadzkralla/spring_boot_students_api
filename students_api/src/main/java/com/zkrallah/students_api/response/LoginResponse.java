package com.zkrallah.students_api.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiresIn;
    private long refreshTokenExpiresIn;
}

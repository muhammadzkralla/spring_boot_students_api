package com.zkrallah.students_api.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {
    String generateAccessToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    String getEmailFromToken(String token);
    boolean validateToken(String token);
    Date extractExpiration(String token);
}
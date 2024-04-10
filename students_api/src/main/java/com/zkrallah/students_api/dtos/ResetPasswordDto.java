package com.zkrallah.students_api.dtos;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String email;
    private String password;
    private int code;
}
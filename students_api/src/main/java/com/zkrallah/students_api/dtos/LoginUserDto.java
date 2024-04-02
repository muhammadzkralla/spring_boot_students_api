package com.zkrallah.students_api.dtos;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;
    private String password;
}

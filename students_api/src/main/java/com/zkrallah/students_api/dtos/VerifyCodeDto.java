package com.zkrallah.students_api.dtos;

import lombok.Data;

@Data
public class VerifyCodeDto {
    private String email;
    private int code;
}

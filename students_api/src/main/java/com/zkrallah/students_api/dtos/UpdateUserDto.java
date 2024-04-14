package com.zkrallah.students_api.dtos;

import lombok.Data;

import java.sql.Date;

@Data
public class UpdateUserDto {
    private String firstName;
    private String lastName;
    private String dob;
    private String imageUrl;
}

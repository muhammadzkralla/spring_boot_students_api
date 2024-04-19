package com.zkrallah.students_api.dtos;

import lombok.Data;

@Data
public class UpdateUserDto {
    private String firstName;
    private String lastName;
    private String dob;
}

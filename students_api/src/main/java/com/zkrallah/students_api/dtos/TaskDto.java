package com.zkrallah.students_api.dtos;

import lombok.Data;

@Data
public class TaskDto {
    private String title;
    private String description;
    private String due;
}

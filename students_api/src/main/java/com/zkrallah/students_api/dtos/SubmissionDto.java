package com.zkrallah.students_api.dtos;

import lombok.Data;

@Data
public class SubmissionDto {
    private String link;
    private String additional;
    private int grade;
}

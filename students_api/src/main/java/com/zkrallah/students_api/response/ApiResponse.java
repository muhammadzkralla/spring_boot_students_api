package com.zkrallah.students_api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> createSuccessResponse(T data) {
        return new ApiResponse<>(true, null, data);
    }

    public static <T> ApiResponse<T> createFailureResponse(String message) {
        return new ApiResponse<>(false, message, null);
    }
}

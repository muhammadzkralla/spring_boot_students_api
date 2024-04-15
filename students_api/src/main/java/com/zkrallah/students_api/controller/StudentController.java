package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.entity.Request;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.request.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    private final RequestService requestService;

    @PostMapping("/request/{userId}/to/{classId}")
    public ResponseEntity<?> requestUserToClass(@PathVariable Long userId, @PathVariable Long classId) {
        try {
            Request request = requestService.createRequest(userId, classId);
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not create request: " + e.getMessage()));
        }
    }
}

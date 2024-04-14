package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.CreateClassDto;
import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.classes.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final ClassService classService;

    @PostMapping("/create-class")
    public ResponseEntity<?> createClass(
            @RequestBody CreateClassDto createClassDto,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            Class createdClass = classService.createClass(createClassDto, authorizationHeader);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdClass);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to create class: " + e.getMessage()));
        }
    }

    @GetMapping("/add/{userId}/to/{classId}")
    public ResponseEntity<?> addUserToClass(@PathVariable Long userId, @PathVariable Long classId) {
        try {
            classService.addUserToClass(userId, classId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new MessageResponse("Done."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to add user to class: " + e.getMessage()));
        }
    }

}

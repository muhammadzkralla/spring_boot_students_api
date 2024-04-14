package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.CreateClassDto;
import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.classes.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final ClassService classService;

    @PostMapping("/create-class")
    public ResponseEntity<?> createClass(@RequestBody CreateClassDto createClassDto) {
        try {
            Class createdClass = classService.createClass(createClassDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdClass);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to create class: " + e.getMessage()));
        }
    }

}

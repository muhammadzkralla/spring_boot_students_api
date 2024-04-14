package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.classes.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/classes")
public class ClassController {

    private final ClassService classService;

    @GetMapping
    public ResponseEntity<List<Class>> getUsers() {
        return ResponseEntity.ok(classService.getClasses());
    }

    @GetMapping("/{classId}/users")
    public ResponseEntity<?> getUserClasses(@PathVariable Long classId) {
        try {
            Set<User> users = classService.getUsersInClass(classId);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not fetch classes: " + e.getMessage()));
        }
    }
}

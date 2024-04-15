package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.CreateClassDto;
import com.zkrallah.students_api.dtos.UpdateClassDto;
import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.entity.Request;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.classes.ClassService;
import com.zkrallah.students_api.service.request.RequestService;
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
    private final RequestService requestService;

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

    @PutMapping("/update-class/{classId}")
    public ResponseEntity<?> updateClass(@PathVariable Long classId, @RequestBody UpdateClassDto updateClassDto) {
        try {
            Class _class = classService.updateClass(classId, updateClassDto);
            return ResponseEntity.ok(_class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not update class: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete-class/{classId}")
    public ResponseEntity<?> removeClass(@PathVariable Long classId) {
        try {
            classService.removeClass(classId);
            return ResponseEntity.ok(new MessageResponse("Class Deleted Successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Could not delete class: " + e.getMessage()));
        }
    }

    @GetMapping("/add/{userId}/to/{classId}")
    public ResponseEntity<?> addUserToClass(@PathVariable Long userId, @PathVariable Long classId) {
        try {
            classService.addUserToClass(userId, classId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new MessageResponse("User Added to Class."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to add user to class: " + e.getMessage()));
        }
    }

    @GetMapping("/remove/{userId}/from/{classId}")
    public ResponseEntity<?> removeUserFromClass(@PathVariable Long userId, @PathVariable Long classId) {
        try {
            classService.removeUserFromClass(userId, classId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new MessageResponse("User Removed from Class.."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to remove user from class: " + e.getMessage()));
        }
    }

    @PutMapping("/approve-request/{requestId}")
    public ResponseEntity<?> approveRequest(@PathVariable Long requestId) {
        try {
            Request request = requestService.approveRequest(requestId);
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to approve request: " + e.getMessage()));
        }
    }

    @PutMapping("/decline-request/{requestId}")
    public ResponseEntity<?> declineRequest(@PathVariable Long requestId) {
        try {
            Request request = requestService.declineRequest(requestId);
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Failed to decline request: " + e.getMessage()));
        }
    }

}

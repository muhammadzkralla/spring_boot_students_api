package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.CreateClassDto;
import com.zkrallah.students_api.dtos.UpdateClassDto;
import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.entity.Request;
import com.zkrallah.students_api.response.ApiResponse;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.classes.ClassService;
import com.zkrallah.students_api.service.request.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.zkrallah.students_api.response.ApiResponse.createFailureResponse;
import static com.zkrallah.students_api.response.ApiResponse.createSuccessResponse;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final ClassService classService;
    private final RequestService requestService;

    @PostMapping("/create-class")
    public ResponseEntity<ApiResponse<Class>> createClass(
            @RequestBody CreateClassDto createClassDto,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            Class createdClass = classService.createClass(createClassDto, authorizationHeader);
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(createdClass));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to create class: " + e.getMessage()));
        }
    }

    @PutMapping("/update-class/{classId}")
    public ResponseEntity<ApiResponse<Class>> updateClass(
            @PathVariable Long classId,
            @RequestBody UpdateClassDto updateClassDto
    ) {
        try {
            Class _class = classService.updateClass(classId, updateClassDto);
            return ResponseEntity.ok(createSuccessResponse(_class));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not update class: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete-class/{classId}")
    public ResponseEntity<ApiResponse<MessageResponse>> removeClass(@PathVariable Long classId) {
        try {
            classService.removeClass(classId);
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("Class Deleted Successfully!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not delete class: " + e.getMessage()));
        }
    }

    @GetMapping("/add/{userId}/to/{classId}")
    public ResponseEntity<ApiResponse<MessageResponse>> addUserToClass(
            @PathVariable Long userId,
            @PathVariable Long classId
    ) {
        try {
            classService.addUserToClass(userId, classId);
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("User Added to Class.")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to add user to class: " + e.getMessage()));
        }
    }

    @GetMapping("/remove/{userId}/from/{classId}")
    public ResponseEntity<ApiResponse<MessageResponse>> removeUserFromClass(
            @PathVariable Long userId,
            @PathVariable Long classId
    ) {
        try {
            classService.removeUserFromClass(userId, classId);
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("User Removed from Class..")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to remove user from class: " + e.getMessage()));
        }
    }

    @PutMapping("/approve-request/{requestId}")
    public ResponseEntity<ApiResponse<Request>> approveRequest(@PathVariable Long requestId) {
        try {
            Request request = requestService.approveRequest(requestId);
            return ResponseEntity.ok(createSuccessResponse(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to approve request: " + e.getMessage()));
        }
    }

    @PutMapping("/decline-request/{requestId}")
    public ResponseEntity<ApiResponse<Request>> declineRequest(@PathVariable Long requestId) {
        try {
            Request request = requestService.declineRequest(requestId);
            return ResponseEntity.ok(createSuccessResponse(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Failed to decline request: " + e.getMessage()));
        }
    }

}

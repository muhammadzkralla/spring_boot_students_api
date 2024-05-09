package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.UpdateUserDto;
import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.entity.Request;
import com.zkrallah.students_api.entity.Task;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.response.ApiResponse;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.classes.ClassService;
import com.zkrallah.students_api.service.request.RequestService;
import com.zkrallah.students_api.service.storage.StorageService;
import com.zkrallah.students_api.service.task.TaskService;
import com.zkrallah.students_api.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

import static com.zkrallah.students_api.response.ApiResponse.createFailureResponse;
import static com.zkrallah.students_api.response.ApiResponse.createSuccessResponse;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;
    private final RequestService requestService;
    private final ClassService classService;
    private final StorageService storageService;
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getUsers() {
        return ResponseEntity.ok(createSuccessResponse(userService.getUsers()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(createSuccessResponse(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not fetch user: " + e.getMessage()));
        }
    }

    @GetMapping("/{userId}/classes")
    public ResponseEntity<ApiResponse<Set<Class>>> getUserClasses(@PathVariable Long userId) {
        try {
            Set<Class> classes = classService.getUserClasses(userId);
            return ResponseEntity.ok(createSuccessResponse(classes));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not fetch classes: " + e.getMessage()));
        }
    }

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserDto updateUser
    ) {
        try {
            User updatedUser = userService.updateUser(userId, updateUser);
            return ResponseEntity.ok(createSuccessResponse(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not update user: " + e.getMessage()));
        }
    }

    @GetMapping("/{userId}/requests")
    public ResponseEntity<ApiResponse<Set<Request>>> getUserRequests(@PathVariable Long userId) {
        try {
            Set<Request> requests = requestService.getUserRequests(userId);
            return ResponseEntity.ok(createSuccessResponse(requests));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not get user's requests: " + e.getMessage()));
        }
    }

    @PostMapping("/request/{userId}/to/{classId}")
    public ResponseEntity<ApiResponse<Request>> requestUserToClass(
            @PathVariable Long userId,
            @PathVariable Long classId
    ) {
        try {
            Request request = requestService.createRequest(userId, classId);
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not create request: " + e.getMessage()));
        }
    }

    @Transactional
    @PostMapping("/{userId}/upload-image")
    public ResponseEntity<ApiResponse<MessageResponse>> upload(
            @RequestParam("file") MultipartFile multipartFile,
            @PathVariable Long userId
    ) {
        try {
            String url = storageService.upload(multipartFile);
            User user = userService.getUserById(userId);
            user.setImageUrl(url);

            return ResponseEntity.ok(createSuccessResponse(new MessageResponse(url)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not upload user's image: " + e.getMessage()));
        }
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<Task>> getTask(@PathVariable Long taskId) {
        try {
            Task task = taskService.getTask(taskId);
            return ResponseEntity.ok(createSuccessResponse(task));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not get task: " + e.getMessage()));
        }
    }
}
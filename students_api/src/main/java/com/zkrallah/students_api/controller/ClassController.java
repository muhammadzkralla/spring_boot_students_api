package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.entity.Announcement;
import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.entity.Request;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.response.ApiResponse;
import com.zkrallah.students_api.service.announcement.AnnouncementService;
import com.zkrallah.students_api.service.classes.ClassService;
import com.zkrallah.students_api.service.request.RequestService;
import com.zkrallah.students_api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.zkrallah.students_api.response.ApiResponse.createFailureResponse;
import static com.zkrallah.students_api.response.ApiResponse.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/classes")
public class ClassController {

    private final ClassService classService;
    private final RequestService requestService;
    private final AnnouncementService announcementService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Class>>> getClasses() {
        return ResponseEntity.ok(createSuccessResponse(classService.getClasses()));
    }

    @GetMapping("/{classId}")
    public ResponseEntity<ApiResponse<Class>> getClass(@PathVariable Long classId) {
        try {
            Class _class = classService.getClassById(classId);
            return ResponseEntity.ok(createSuccessResponse(_class));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not fetch class: " + e.getMessage()));
        }
    }

    @GetMapping("/{classId}/users")
    public ResponseEntity<ApiResponse<Set<User>>> getClassUsers(@PathVariable Long classId) {
        try {
            Set<User> users = userService.getUsersInClass(classId);
            return ResponseEntity.ok(createSuccessResponse(users));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not fetch users: " + e.getMessage()));
        }
    }

    @GetMapping("/{classId}/requests")
    public ResponseEntity<ApiResponse<Set<Request>>> getClassRequests(@PathVariable Long classId) {
        try {
            Set<Request> requests = requestService.getClassRequests(classId);
            return ResponseEntity.ok(createSuccessResponse(requests));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not get class's requests: " + e.getMessage()));
        }
    }

    @GetMapping("/{classId}/announcements")
    public ResponseEntity<ApiResponse<Set<Announcement>>> getClassAnnouncements(@PathVariable Long classId) {
        try {
            Set<Announcement> announcements = announcementService.getAnnouncementsInClass(classId);
            return ResponseEntity.ok(createSuccessResponse(announcements));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not get class's announcements: " + e.getMessage()));
        }
    }
}

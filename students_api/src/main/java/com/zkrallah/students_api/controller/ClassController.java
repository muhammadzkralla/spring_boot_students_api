package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.entity.Announcement;
import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.entity.Request;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.announcement.AnnouncementService;
import com.zkrallah.students_api.service.classes.ClassService;
import com.zkrallah.students_api.service.request.RequestService;
import com.zkrallah.students_api.service.user.UserService;
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
    private final RequestService requestService;
    private final AnnouncementService announcementService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Class>> getClasses() {
        return ResponseEntity.ok(classService.getClasses());
    }

    @GetMapping("/{classId}")
    public ResponseEntity<?> getClass(@PathVariable Long classId) {
        try {
            Class _class = classService.getClassById(classId);
            return ResponseEntity.ok(_class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not fetch class: " + e.getMessage()));
        }
    }

    @GetMapping("/{classId}/users")
    public ResponseEntity<?> getClassUsers(@PathVariable Long classId) {
        try {
            Set<User> users = userService.getUsersInClass(classId);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not fetch users: " + e.getMessage()));
        }
    }

    @GetMapping("/{classId}/requests")
    public ResponseEntity<?> getClassRequests(@PathVariable Long classId) {
        try {
            Set<Request> requests = requestService.getClassRequests(classId);
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not get class's requests: " + e.getMessage()));
        }
    }

    @GetMapping("/{classId}/announcements")
    public ResponseEntity<?> getClassAnnouncements(@PathVariable Long classId) {
        try {
            Set<Announcement> requests = announcementService.getAnnouncementsInClass(classId);
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not get class's announcements: " + e.getMessage()));
        }
    }
}

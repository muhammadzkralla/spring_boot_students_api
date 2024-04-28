package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.entity.Announcement;
import com.zkrallah.students_api.response.ApiResponse;
import com.zkrallah.students_api.service.announcement.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.zkrallah.students_api.response.ApiResponse.createFailureResponse;
import static com.zkrallah.students_api.response.ApiResponse.createSuccessResponse;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
@Slf4j
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Announcement>>> getAnnouncements() {
        return ResponseEntity.ok(createSuccessResponse(announcementService.getAnnouncements()));
    }

    @GetMapping("/{announcementId}")
    public ResponseEntity<ApiResponse<Announcement>> getAnnouncement(@PathVariable Long announcementId) {
        try {
            Announcement announcement = announcementService.getAnnouncement(announcementId);
            return ResponseEntity.ok(createSuccessResponse(announcement));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not fetch announcement: " + e.getMessage()));
        }
    }
}

package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.entity.Announcement;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.announcement.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
@Slf4j
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    public ResponseEntity<?> getAnnouncements() {
        return ResponseEntity.ok(announcementService.getAnnouncements());
    }

    @GetMapping("/{announcementId}")
    public ResponseEntity<?> getAnnouncement(@PathVariable Long announcementId) {
        try {
            Announcement announcement = announcementService.getAnnouncement(announcementId);
            return ResponseEntity.ok(announcement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not fetch announcement: " + e.getMessage()));
        }
    }
}

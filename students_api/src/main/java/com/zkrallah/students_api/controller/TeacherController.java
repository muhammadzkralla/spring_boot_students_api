package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.AnnouncementDto;
import com.zkrallah.students_api.entity.Announcement;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.announcement.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@Slf4j
public class TeacherController {

    private final AnnouncementService announcementService;

    @PostMapping("/create-announcement/{classId}")
    public ResponseEntity<?> createAnnouncement(
            @RequestBody AnnouncementDto announcementDto,
            @PathVariable Long classId
    ) {
        try {
            Announcement announcement = announcementService.createAnnouncement(announcementDto, classId);
            return ResponseEntity.ok(announcement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not create announcement: " + e.getMessage()));
        }
    }

    @PutMapping("/update-announcement/{announcementId}")
    public ResponseEntity<?> updateAnnouncement(
            @PathVariable Long announcementId,
            @RequestBody AnnouncementDto announcementDto
    ) {
        try {
            Announcement announcement = announcementService.updateAnnouncement(announcementId, announcementDto);
            return ResponseEntity.ok(announcement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not update announcement: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete-announcement/{announcementId}")
    public ResponseEntity<?> deleteAnnouncement(@PathVariable Long announcementId) {
        try {
            announcementService.deleteAnnouncement(announcementId);
            return ResponseEntity.ok(new MessageResponse("Announcement Deleted Successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not delete announcement: " + e.getMessage()));
        }
    }

}

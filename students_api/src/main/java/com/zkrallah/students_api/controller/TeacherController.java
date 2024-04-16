package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.AnnouncementDto;
import com.zkrallah.students_api.dtos.SourceDto;
import com.zkrallah.students_api.dtos.TaskDto;
import com.zkrallah.students_api.entity.Announcement;
import com.zkrallah.students_api.entity.Source;
import com.zkrallah.students_api.entity.Submission;
import com.zkrallah.students_api.entity.Task;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.announcement.AnnouncementService;
import com.zkrallah.students_api.service.source.SourceService;
import com.zkrallah.students_api.service.submission.SubmissionService;
import com.zkrallah.students_api.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@Slf4j
public class TeacherController {

    private final AnnouncementService announcementService;
    private final TaskService taskService;
    private final SourceService sourceService;
    private final SubmissionService submissionService;

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

    @PostMapping("/class/{classId}/create-task")
    public ResponseEntity<?> createTask(
            @PathVariable Long classId,
            @RequestBody TaskDto taskDto
    ) {
        try {
            Task task = taskService.createTask(classId, taskDto);
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not create task: " + e.getMessage()));
        }
    }

    @GetMapping("/class/{classId}/tasks")
    public ResponseEntity<?> getTasksInClass(@PathVariable Long classId) {
        try {
            Set<Task> tasks = taskService.getTasksInClass(classId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not get tasks: " + e.getMessage()));
        }
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable Long taskId) {
        try {
            Task task = taskService.getTask(taskId);
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not get task: " + e.getMessage()));
        }
    }

    @PutMapping("/update-task/{taskId}")
    public ResponseEntity<?> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskDto taskDto
    ) {
        try {
            Task task = taskService.updateTask(taskId, taskDto);
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not update task: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete-task/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.ok(new MessageResponse("Task Deleted Successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not delete task: " + e.getMessage()));
        }
    }

    @PostMapping("/task/{taskId}/create-source")
    public ResponseEntity<?> createSource(
            @PathVariable Long taskId,
            @RequestBody SourceDto sourceDto
    ) {
        try {
            Source source = sourceService.createSource(taskId, sourceDto);
            return ResponseEntity.ok(source);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not create source: " + e.getMessage()));
        }
    }

    @GetMapping("/task/{taskId}/sources")
    public ResponseEntity<?> getTaskSources(@PathVariable Long taskId) {
        try {
            Set<Source> sources = sourceService.getTaskSources(taskId);
            return ResponseEntity.ok(sources);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not get sources: " + e.getMessage()));
        }
    }

    @GetMapping("/source/{sourceId}")
    public ResponseEntity<?> getSource(@PathVariable Long sourceId) {
        try {
            Source source = sourceService.getSource(sourceId);
            return ResponseEntity.ok(source);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not get source: " + e.getMessage()));
        }
    }

    @PutMapping("/update-source/{sourceId}")
    public ResponseEntity<?> updateSource(
            @PathVariable Long sourceId,
            @RequestBody SourceDto sourceDto
    ) {
        try {
            Source source = sourceService.updateSource(sourceId, sourceDto);
            return ResponseEntity.ok(source);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not update source: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete-source/{sourceId}")
    public ResponseEntity<?> deleteSource(@PathVariable Long sourceId) {
        try {
            sourceService.deleteSource(sourceId);
            return ResponseEntity.ok(new MessageResponse("Source Deleted Successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not delete source: " + e.getMessage()));
        }
    }

    @GetMapping("/task/{taskId}/submissions")
    public ResponseEntity<?> getTaskSubmissions(@PathVariable Long taskId) {
        try {
            Set<Submission> submissions = submissionService.getTaskSubmissions(taskId);
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not get submissions: " + e.getMessage()));
        }
    }

}

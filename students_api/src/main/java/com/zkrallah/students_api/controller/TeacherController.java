package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.AnnouncementDto;
import com.zkrallah.students_api.dtos.SourceDto;
import com.zkrallah.students_api.dtos.SubmissionDto;
import com.zkrallah.students_api.dtos.TaskDto;
import com.zkrallah.students_api.entity.Announcement;
import com.zkrallah.students_api.entity.Source;
import com.zkrallah.students_api.entity.Submission;
import com.zkrallah.students_api.entity.Task;
import com.zkrallah.students_api.response.ApiResponse;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.announcement.AnnouncementService;
import com.zkrallah.students_api.service.source.SourceService;
import com.zkrallah.students_api.service.submission.SubmissionService;
import com.zkrallah.students_api.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.zkrallah.students_api.response.ApiResponse.createFailureResponse;
import static com.zkrallah.students_api.response.ApiResponse.createSuccessResponse;
import static org.springframework.http.HttpStatus.CREATED;

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
    public ResponseEntity<ApiResponse<Announcement>> createAnnouncement(
            @RequestBody AnnouncementDto announcementDto,
            @PathVariable Long classId
    ) {
        try {
            Announcement announcement = announcementService.createAnnouncement(announcementDto, classId);
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(announcement));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not create announcement: " + e.getMessage()));
        }
    }

    @PutMapping("/update-announcement/{announcementId}")
    public ResponseEntity<ApiResponse<Announcement>> updateAnnouncement(
            @PathVariable Long announcementId,
            @RequestBody AnnouncementDto announcementDto
    ) {
        try {
            Announcement announcement = announcementService.updateAnnouncement(announcementId, announcementDto);
            return ResponseEntity.ok(createSuccessResponse(announcement));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not update announcement: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete-announcement/{announcementId}")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteAnnouncement(@PathVariable Long announcementId) {
        try {
            announcementService.deleteAnnouncement(announcementId);
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("Announcement Deleted Successfully!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not delete announcement: " + e.getMessage()));
        }
    }

    @PostMapping("/class/{classId}/create-task")
    public ResponseEntity<ApiResponse<Task>> createTask(
            @PathVariable Long classId,
            @RequestBody TaskDto taskDto
    ) {
        try {
            Task task = taskService.createTask(classId, taskDto);
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(task));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not create task: " + e.getMessage()));
        }
    }

    @PutMapping("/update-task/{taskId}")
    public ResponseEntity<ApiResponse<Task>> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskDto taskDto
    ) {
        try {
            Task task = taskService.updateTask(taskId, taskDto);
            return ResponseEntity.ok(createSuccessResponse(task));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not update task: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete-task/{taskId}")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteTask(@PathVariable Long taskId) {
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("Task Deleted Successfully!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not delete task: " + e.getMessage()));
        }
    }

    @PostMapping("/task/{taskId}/create-source")
    public ResponseEntity<ApiResponse<Source>> createSource(
            @PathVariable Long taskId,
            @RequestBody SourceDto sourceDto
    ) {
        try {
            Source source = sourceService.createSource(taskId, sourceDto);
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(source));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not create source: " + e.getMessage()));
        }
    }

    @GetMapping("/task/{taskId}/sources")
    public ResponseEntity<ApiResponse<Set<Source>>> getTaskSources(@PathVariable Long taskId) {
        try {
            Set<Source> sources = sourceService.getTaskSources(taskId);
            return ResponseEntity.ok(createSuccessResponse(sources));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not get sources: " + e.getMessage()));
        }
    }

    @GetMapping("/source/{sourceId}")
    public ResponseEntity<ApiResponse<Source>> getSource(@PathVariable Long sourceId) {
        try {
            Source source = sourceService.getSource(sourceId);
            return ResponseEntity.ok(createSuccessResponse(source));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not get source: " + e.getMessage()));
        }
    }

    @PutMapping("/update-source/{sourceId}")
    public ResponseEntity<ApiResponse<Source>> updateSource(
            @PathVariable Long sourceId,
            @RequestBody SourceDto sourceDto
    ) {
        try {
            Source source = sourceService.updateSource(sourceId, sourceDto);
            return ResponseEntity.ok(createSuccessResponse(source));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not update source: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete-source/{sourceId}")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteSource(@PathVariable Long sourceId) {
        try {
            sourceService.deleteSource(sourceId);
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("Source Deleted Successfully!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not delete source: " + e.getMessage()));
        }
    }

    @GetMapping("/task/{taskId}/submissions")
    public ResponseEntity<ApiResponse<Set<Submission>>> getTaskSubmissions(@PathVariable Long taskId) {
        try {
            Set<Submission> submissions = submissionService.getTaskSubmissions(taskId);
            return ResponseEntity.ok(createSuccessResponse(submissions));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not get submissions: " + e.getMessage()));
        }
    }

    @PutMapping("/update-submission/{submissionId}")
    public ResponseEntity<ApiResponse<Submission>> updateSubmission(
            @PathVariable Long submissionId,
            @RequestBody SubmissionDto submissionDto
    ) {
        try {
            Submission submission = submissionService.updateSubmission(submissionId, submissionDto);
            return ResponseEntity.ok(createSuccessResponse(submission));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not update submission: " + e.getMessage()));
        }
    }

}

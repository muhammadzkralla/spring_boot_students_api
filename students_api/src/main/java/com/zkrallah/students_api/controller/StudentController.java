package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.SubmissionDto;
import com.zkrallah.students_api.entity.Submission;
import com.zkrallah.students_api.response.ApiResponse;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.submission.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.zkrallah.students_api.response.ApiResponse.createFailureResponse;
import static com.zkrallah.students_api.response.ApiResponse.createSuccessResponse;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    private final SubmissionService submissionService;

    @PostMapping("/submit/{userId}/{taskId}")
    public ResponseEntity<ApiResponse<Submission>> createSubmission(
            @PathVariable Long userId,
            @PathVariable Long taskId,
            @RequestBody SubmissionDto submissionDto
    ) {
        try {
            Submission submission = submissionService.createSubmission(userId, taskId, submissionDto);
            return ResponseEntity.status(CREATED)
                    .body(createSuccessResponse(submission));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not submit: " + e.getMessage()));
        }
    }

    @GetMapping("/submission/{submissionId}")
    public ResponseEntity<ApiResponse<Submission>> getSubmission(@PathVariable Long submissionId) {
        try {
            Submission submission = submissionService.getSubmission(submissionId);
            return ResponseEntity.ok(createSuccessResponse(submission));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not get submission: " + e.getMessage()));
        }
    }

    @GetMapping("/submissions/{taskId}/{userId}")
    public ResponseEntity<ApiResponse<List<Submission>>> getUserTaskSubmission(
            @PathVariable Long taskId,
            @PathVariable Long userId
    ) {
        try {
            List<Submission> submission = submissionService.getUserTaskSubmissions(taskId, userId);
            return ResponseEntity.ok(createSuccessResponse(submission));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not get submission: " + e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}/submissions")
    public ResponseEntity<ApiResponse<Set<Submission>>> getUserSubmissions(@PathVariable Long userId) {
        try {
            Set<Submission> submissions = submissionService.getUserSubmissions(userId);
            return ResponseEntity.ok(createSuccessResponse(submissions));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not get submissions: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete-submission/{submissionId}")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteSubmission(@PathVariable Long submissionId) {
        try {
            submissionService.deleteSubmission(submissionId);
            return ResponseEntity.ok(createSuccessResponse(new MessageResponse("Submission Deleted Successfully!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createFailureResponse("Could not delete submission: " + e.getMessage()));
        }
    }
}

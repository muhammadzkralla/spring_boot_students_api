package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.SubmissionDto;
import com.zkrallah.students_api.entity.Request;
import com.zkrallah.students_api.entity.Submission;
import com.zkrallah.students_api.response.ApiResponse;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.request.RequestService;
import com.zkrallah.students_api.service.submission.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.zkrallah.students_api.response.ApiResponse.createFailureResponse;
import static com.zkrallah.students_api.response.ApiResponse.createSuccessResponse;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    private final RequestService requestService;
    private final SubmissionService submissionService;

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

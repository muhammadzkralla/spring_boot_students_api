package com.zkrallah.students_api.controller;

import com.zkrallah.students_api.dtos.SubmissionDto;
import com.zkrallah.students_api.entity.Request;
import com.zkrallah.students_api.entity.Submission;
import com.zkrallah.students_api.response.MessageResponse;
import com.zkrallah.students_api.service.request.RequestService;
import com.zkrallah.students_api.service.submission.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    private final RequestService requestService;
    private final SubmissionService submissionService;

    @PostMapping("/request/{userId}/to/{classId}")
    public ResponseEntity<?> requestUserToClass(@PathVariable Long userId, @PathVariable Long classId) {
        try {
            Request request = requestService.createRequest(userId, classId);
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not create request: " + e.getMessage()));
        }
    }

    @PostMapping("/submit/{userId}/{taskId}")
    public ResponseEntity<?> createSubmission(
            @PathVariable Long userId,
            @PathVariable Long taskId,
            @RequestBody SubmissionDto submissionDto
    ) {
        try {
            Submission submission = submissionService.createSubmission(userId, taskId, submissionDto);
            return ResponseEntity.ok(submission);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not submit: " + e.getMessage()));
        }
    }

    @GetMapping("/submission/{submissionId}")
    public ResponseEntity<?> getSubmission(@PathVariable Long submissionId) {
        try {
            Submission submission = submissionService.getSubmission(submissionId);
            return ResponseEntity.ok(submission);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not get submission: " + e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}/submissions")
    public ResponseEntity<?> getUserSubmissions(@PathVariable Long userId) {
        try {
            Set<Submission> submissions = submissionService.getUserSubmissions(userId);
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not get submissions: " + e.getMessage()));
        }
    }

    @PutMapping("/update-submission/{submissionId}")
    public ResponseEntity<?> updateSubmission(
            @PathVariable Long submissionId,
            @RequestBody SubmissionDto submissionDto
    ) {
        try {
            Submission submission = submissionService.updateSubmission(submissionId, submissionDto);
            return ResponseEntity.ok(submission);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not update submission: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete-submission/{submissionId}")
    public ResponseEntity<?> deleteSubmission(@PathVariable Long submissionId) {
        try {
            submissionService.deleteSubmission(submissionId);
            return ResponseEntity.ok(new MessageResponse("Submission Deleted Successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Could not delete submission: " + e.getMessage()));
        }
    }
}

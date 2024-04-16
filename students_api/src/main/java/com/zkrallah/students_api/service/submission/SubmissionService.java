package com.zkrallah.students_api.service.submission;

import com.zkrallah.students_api.dtos.SubmissionDto;
import com.zkrallah.students_api.entity.Submission;

import java.util.List;
import java.util.Set;

public interface SubmissionService {
    Submission createSubmission(Long userId, Long taskId, SubmissionDto submissionDto);

    Submission updateSubmission(Long submissionId, SubmissionDto submissionDto);

    void deleteSubmission(Long submissionId);

    Submission getSubmission(Long submissionId);

    List<Submission> getSubmissions();

    Set<Submission> getUserSubmissions(Long userId);

    Set<Submission> getTaskSubmissions(Long taskId);
}

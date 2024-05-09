package com.zkrallah.students_api.service.submission;

import com.zkrallah.students_api.dtos.SubmissionDto;
import com.zkrallah.students_api.entity.Submission;
import com.zkrallah.students_api.entity.Task;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.repository.SubmissionRepository;
import com.zkrallah.students_api.service.task.TaskService;
import com.zkrallah.students_api.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final TaskService taskService;
    private final UserService userService;

    @Override
    @Transactional
    public Submission createSubmission(Long userId, Long taskId, SubmissionDto submissionDto) {
        Task task = taskService.getTask(taskId);
        User user = userService.getUserById(userId);
        Submission submission = new Submission();

        submission.setLink(submissionDto.getLink());
        submission.setAdditional(submissionDto.getAdditional());
        submission.setGrade(submissionDto.getGrade());
        submission.setUser(user);
        submission.setTask(task);

        return submissionRepository.save(submission);
    }

    @Override
    @Transactional
    public Submission updateSubmission(Long submissionId, SubmissionDto submissionDto) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found."));

        submission.setLink(submissionDto.getLink());
        submission.setAdditional(submissionDto.getAdditional());
        submission.setGrade(submissionDto.getGrade());

        return submission;
    }

    @Override
    public void deleteSubmission(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found."));

        submissionRepository.delete(submission);
    }

    @Override
    public Submission getSubmission(Long submissionId) {
        return submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found."));
    }

    @Override
    public List<Submission> getSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public Set<Submission> getUserSubmissions(Long userId) {
        User user = userService.getUserById(userId);
        return user.getSubmissions();
    }

    @Override
    public Set<Submission> getTaskSubmissions(Long taskId) {
        Task task = taskService.getTask(taskId);
        return task.getSubmissions();
    }

    @Override
    public List<Submission> getUserTaskSubmissions(Long taskId, Long userId) {
        Task task = taskService.getTask(taskId);
        User user = userService.getUserById(userId);

        return submissionRepository.findByUserAndTask(user, task)
                .orElseThrow(() -> new RuntimeException("Submission not found."));
    }
}

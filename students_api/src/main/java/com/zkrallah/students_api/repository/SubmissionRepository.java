package com.zkrallah.students_api.repository;

import com.zkrallah.students_api.entity.Submission;
import com.zkrallah.students_api.entity.Task;
import com.zkrallah.students_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<List<Submission>> findByUserAndTask(User user, Task task);
}

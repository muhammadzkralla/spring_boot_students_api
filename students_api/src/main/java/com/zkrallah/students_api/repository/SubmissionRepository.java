package com.zkrallah.students_api.repository;

import com.zkrallah.students_api.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}

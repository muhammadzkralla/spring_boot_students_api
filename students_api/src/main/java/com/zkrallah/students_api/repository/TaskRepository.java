package com.zkrallah.students_api.repository;

import com.zkrallah.students_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

package com.zkrallah.students_api.service.task;

import com.zkrallah.students_api.dtos.TaskDto;
import com.zkrallah.students_api.entity.Task;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

public interface TaskService {
    Task createTask(Long classId, TaskDto taskDto) throws ParseException;

    Task updateTask(Long taskId, TaskDto taskDto) throws ParseException;

    void deleteTask(Long taskId);

    Task getTask(Long taskId);

    List<Task> getTasks();

    Set<Task> getTasksInClass(Long classId);
}

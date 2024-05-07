package com.zkrallah.students_api.service.task;

import com.zkrallah.students_api.dtos.TaskDto;
import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.entity.Task;
import com.zkrallah.students_api.repository.TaskRepository;
import com.zkrallah.students_api.service.classes.ClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final ClassService classService;

    @Override
    @Transactional
    public Task createTask(Long classId, TaskDto taskDto) throws ParseException {
        Class _class = classService.getClassById(classId);
        Task task = new Task();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDue(new Date(simpleDateFormat.parse(taskDto.getDue()).getTime()));
        task.setTargetedClass(_class);

        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task updateTask(Long taskId, TaskDto taskDto) throws ParseException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found."));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDue(new Date(simpleDateFormat.parse(taskDto.getDue()).getTime()));

        return task;
    }

    @Override
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found."));

        taskRepository.delete(task);
    }

    @Override
    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found."));
    }

    @Override
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Set<Task> getTasksInClass(Long classId) {
        Class _class = classService.getClassById(classId);
        return _class.getTasks();
    }
}

package com.zkrallah.students_api.service.source;

import com.zkrallah.students_api.dtos.SourceDto;
import com.zkrallah.students_api.entity.Source;
import com.zkrallah.students_api.entity.Task;
import com.zkrallah.students_api.repository.SourceRepository;
import com.zkrallah.students_api.service.task.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SourceServiceImpl implements SourceService{

    private final SourceRepository sourceRepository;
    private final TaskService taskService;

    @Override
    @Transactional
    public Source createSource(Long taskId, SourceDto sourceDto) {
        Task task = taskService.getTask(taskId);
        Source source = new Source();

        source.setSource(sourceDto.getSource());
        source.setTask(task);

        return sourceRepository.save(source);
    }

    @Override
    @Transactional
    public Source updateSource(Long sourceId, SourceDto sourceDto) {
        Source source = sourceRepository.findById(sourceId)
                .orElseThrow(() -> new RuntimeException("Source not found."));

        source.setSource(sourceDto.getSource());

        return source;
    }

    @Override
    public void deleteSource(Long sourceId) {
        Source source = sourceRepository.findById(sourceId)
                .orElseThrow(() -> new RuntimeException("Source not found."));

        sourceRepository.delete(source);
    }

    @Override
    public Source getSource(Long sourceId) {
        return sourceRepository.findById(sourceId)
                .orElseThrow(() -> new RuntimeException("Source not found."));
    }

    @Override
    public List<Source> getSources() {
        return sourceRepository.findAll();
    }

    @Override
    public Set<Source> getTaskSources(Long taskId) {
        Task task = taskService.getTask(taskId);
        return task.getSources();
    }
}

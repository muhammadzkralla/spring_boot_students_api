package com.zkrallah.students_api.service.source;

import com.zkrallah.students_api.dtos.SourceDto;
import com.zkrallah.students_api.entity.Source;

import java.util.List;
import java.util.Set;

public interface SourceService {
    Source createSource(Long taskId, SourceDto sourceDto);

    Source updateSource(Long sourceId, SourceDto sourceDto);

    void deleteSource(Long sourceId);

    Source getSource(Long sourceId);

    List<Source> getSources();

    Set<Source> getTaskSources(Long taskId);
}

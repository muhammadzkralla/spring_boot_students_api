package com.zkrallah.students_api.service.classes;

import com.zkrallah.students_api.dtos.CreateClassDto;
import com.zkrallah.students_api.dtos.UpdateClassDto;
import com.zkrallah.students_api.entity.Class;

import java.util.List;
import java.util.Set;

public interface ClassService {
    Class createClass(CreateClassDto createClassDto, String authorizationHeader);

    List<Class> getClasses();

    Class getClassById(Long classId);

    Set<Class> getUserClasses(Long userId);

    void addUserToClass(Long userId, Long classId);

    void removeUserFromClass(Long userId, Long classId);

    Class updateClass(Long classId, UpdateClassDto updateClassDto);

    void removeClass(Long classId);
}
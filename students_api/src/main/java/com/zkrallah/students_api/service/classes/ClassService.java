package com.zkrallah.students_api.service.classes;

import com.zkrallah.students_api.dtos.CreateClassDto;
import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.entity.User;

import java.util.List;
import java.util.Set;

public interface ClassService {
    Class createClass(CreateClassDto createClassDto, String authorizationHeader);

    List<Class> getClasses();

    Class getClassById(Long classId);

    Set<User> getUsersInClass(Long classId);

    void addUserToClass(Long userId, Long classId);
}
package com.zkrallah.students_api.service.classes;

import com.zkrallah.students_api.dtos.CreateClassDto;
import com.zkrallah.students_api.entity.Class;

public interface ClassService {
    Class createClass(CreateClassDto createClassDto);
}
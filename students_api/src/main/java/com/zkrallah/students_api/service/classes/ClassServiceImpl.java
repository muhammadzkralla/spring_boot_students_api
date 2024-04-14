package com.zkrallah.students_api.service.classes;

import com.zkrallah.students_api.dtos.CreateClassDto;
import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;

    @Override
    public Class createClass(CreateClassDto createClassDto) {
        Class createdClass = createClassFromDto(createClassDto);
        Optional<Class> userOptional = classRepository.findByName(createdClass.getName());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("Class Already Exists!");
        }

        return classRepository.save(createdClass);
    }

    private Class createClassFromDto(CreateClassDto createClassDto) {
        Class createdClass = new Class();
        createdClass.setName(createClassDto.getName());
        createdClass.setDescription(createClassDto.getDescription());

        return createdClass;
    }
}
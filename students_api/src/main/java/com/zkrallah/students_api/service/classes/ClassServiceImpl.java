package com.zkrallah.students_api.service.classes;

import com.zkrallah.students_api.dtos.CreateClassDto;
import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.repository.ClassRepository;
import com.zkrallah.students_api.service.jwt.JwtService;
import com.zkrallah.students_api.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public Class createClass(CreateClassDto createClassDto, String authorizationHeader) {
        Class createdClass = createClassFromDto(createClassDto, authorizationHeader);
        Optional<Class> userOptional = classRepository.findByName(createdClass.getName());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("Class Already Exists!");
        }

        return classRepository.save(createdClass);
    }

    @Transactional
    private Class createClassFromDto(CreateClassDto createClassDto, String authorizationHeader) {
        Class createdClass = new Class();
        createdClass.setName(createClassDto.getName());
        createdClass.setDescription(createClassDto.getDescription());

        User user = extractUserFromToken(authorizationHeader);
        user.getClasses().add(createdClass);

        return createdClass;
    }

    private User extractUserFromToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            final String token = authorizationHeader.substring(7);
            if (jwtService.validateToken(token)) {
                final String email = jwtService.getEmailFromToken(token);

                return userService.getUser(email).orElseThrow(()
                        -> new NoSuchElementException("User not found"));
            }
        }

        return null;
    }

    @Override
    public List<Class> getClasses() {
        return classRepository.findAll();
    }

    @Override
    public Class getClassById(Long classId) {
        return classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));
    }

    @Override
    public Set<User> getUsersInClass(Long classId) {
        Class _class = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));
        return _class.getUsers();
    }

    @Override
    @Transactional
    public void addUserToClass(Long userId, Long classId) {
        User user = userService.getUserById(userId);
        Class _class = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));

        user.getClasses().add(_class);
    }
}
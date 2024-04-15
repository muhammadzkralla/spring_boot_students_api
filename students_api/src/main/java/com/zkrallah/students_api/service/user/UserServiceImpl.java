package com.zkrallah.students_api.service.user;

import com.zkrallah.students_api.dtos.UpdateUserDto;
import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.entity.Role;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.repository.UserRepository;
import com.zkrallah.students_api.service.classes.ClassService;
import com.zkrallah.students_api.service.role.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleService roleService;
    @Lazy
    @Autowired
    private ClassService classService;

    @Override
    public User saveUser(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("Email Already Exists!");
        }

        return userRepository.save(user);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Role role = roleService.getRole(roleName).orElseThrow();
        user.getRoles().add(role);
    }

    @Override
    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getUsersWithRole(String roleName) {
        Role role = roleService.getRole(roleName).orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        return userRepository.findByRolesContaining(role).orElseThrow();
    }

    @Override
    public Set<User> getUsersInClass(Long classId) {
        Class _class = classService.getClassById(classId);
        return _class.getUsers();
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUser(Long userId, UpdateUserDto updateUserDto) throws ParseException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setImageUrl(updateUserDto.getImageUrl());
        user.setDob(new Date(simpleDateFormat.parse(updateUserDto.getDob()).getTime()));

        return user;
    }
}
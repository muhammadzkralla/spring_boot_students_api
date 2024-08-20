package com.zkrallah.students_api.service.user;

import com.zkrallah.students_api.dtos.UpdateUserDto;
import com.zkrallah.students_api.entity.User;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    User saveUser(User user);

    void addRoleToUser(String email, String roleName);

    Optional<User> getUser(String email);

    User getUserById(Long id);

    List<User> getUsersWithRole(String roleName);

    Set<User> getUsersInClass(Long classId);

    List<User> getUsers();

    User updateUser(Long userId, UpdateUserDto updateUserDto) throws ParseException;

    void updateUserPhoto(Long userId, String url);
}
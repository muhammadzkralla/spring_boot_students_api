package com.zkrallah.students_api.service.user;

import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    User saveUser(User user);
    void addRoleToUser(String email, String roleName);
    Optional<User> getUser(String email);
    Optional<User> getUserById(Long id);
    List<User> getUsersWithRole(String roleName);
    Set<Class> getClasses(Long userId);
    List<User> getUsers();
}
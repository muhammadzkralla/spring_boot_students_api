package com.zkrallah.students_api.service.user;

import com.zkrallah.students_api.entity.Role;
import com.zkrallah.students_api.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    void addRoleToUser(String email, String roleName);
    Optional<User> getUser(String email);
    List<User> getUsersWithRole(String roleName);
    List<User> getUsers();
}

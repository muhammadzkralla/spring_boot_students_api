package com.zkrallah.students_api.service.user;

import com.zkrallah.students_api.entity.Role;
import com.zkrallah.students_api.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    User getUser(String email);
    List<User> getUsers();
}

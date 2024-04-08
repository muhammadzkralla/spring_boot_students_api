package com.zkrallah.students_api.service.role;

import com.zkrallah.students_api.entity.Role;

import java.util.Optional;

public interface RoleService {
    Role saveRole(Role role);
    Optional<Role> getRole(String role);
}

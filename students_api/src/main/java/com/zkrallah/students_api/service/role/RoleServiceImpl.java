package com.zkrallah.students_api.service.role;

import com.zkrallah.students_api.entity.Role;
import com.zkrallah.students_api.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> getRole(String roleName) {
        return roleRepository.findByName(roleName);
    }
}

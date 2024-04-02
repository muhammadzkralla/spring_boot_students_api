package com.zkrallah.students_api.repository;

import com.zkrallah.students_api.entity.Role;
import com.zkrallah.students_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRolesContaining(Role role);
}

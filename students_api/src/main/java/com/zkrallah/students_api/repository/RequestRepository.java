package com.zkrallah.students_api.repository;

import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.entity.Request;
import com.zkrallah.students_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findByUserAndRequestedClass(User user, Class requestedClass);
}

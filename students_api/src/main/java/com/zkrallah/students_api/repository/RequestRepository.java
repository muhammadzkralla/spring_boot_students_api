package com.zkrallah.students_api.repository;

import com.zkrallah.students_api.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

}

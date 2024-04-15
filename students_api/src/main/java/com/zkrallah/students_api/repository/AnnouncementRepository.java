package com.zkrallah.students_api.repository;

import com.zkrallah.students_api.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}

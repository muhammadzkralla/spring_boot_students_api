package com.zkrallah.students_api.service.announcement;

import com.zkrallah.students_api.dtos.AnnouncementDto;
import com.zkrallah.students_api.entity.Announcement;
import com.zkrallah.students_api.entity.Class;
import com.zkrallah.students_api.repository.AnnouncementRepository;
import com.zkrallah.students_api.service.classes.ClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final ClassService classService;

    @Override
    public Announcement createAnnouncement(AnnouncementDto announcementDto, Long classId) {
        Class targetedClass = classService.getClassById(classId);

        Announcement announcement = new Announcement();
        announcement.setTitle(announcementDto.getTitle());
        announcement.setContent(announcementDto.getContent());
        announcement.setTargetedClass(targetedClass);

        return announcementRepository.save(announcement);
    }

    @Override
    @Transactional
    public Announcement updateAnnouncement(Long announcementId, AnnouncementDto announcementDto) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("Announcement not found."));

        announcement.setTitle(announcementDto.getTitle());
        announcement.setContent(announcementDto.getContent());

        return announcement;
    }

    @Override
    public Announcement getAnnouncement(Long announcementId) {
        return announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("Announcement not found."));
    }

    @Override
    public List<Announcement> getAnnouncements() {
        return announcementRepository.findAll();
    }

    @Override
    public Set<Announcement> getAnnouncementsInClass(Long classId) {
        Class _class = classService.getClassById(classId);
        return _class.getAnnouncements();
    }

    @Override
    public void deleteAnnouncement(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                        .orElseThrow(() -> new RuntimeException("Announcement not found."));

        announcementRepository.delete(announcement);
    }
}

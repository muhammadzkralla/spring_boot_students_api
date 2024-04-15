package com.zkrallah.students_api.service.announcement;

import com.zkrallah.students_api.dtos.AnnouncementDto;
import com.zkrallah.students_api.entity.Announcement;

import java.util.List;
import java.util.Set;

public interface AnnouncementService {
    Announcement createAnnouncement(AnnouncementDto announcementDto, Long classId);

    Announcement updateAnnouncement(Long announcementId, AnnouncementDto announcementDto);

    Announcement getAnnouncement(Long announcementId);

    List<Announcement> getAnnouncements();

    Set<Announcement> getAnnouncementsInClass(Long classId);

    void deleteAnnouncement(Long announcementId);
}

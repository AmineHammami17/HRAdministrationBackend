package com.example.HRApplication.Services;

import com.example.HRApplication.Models.Announcement;
import com.example.HRApplication.Repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public Announcement addAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public Optional<Announcement> getAnnouncementById(Long id) {
        return announcementRepository.findById(id);
    }

    public Announcement updateAnnouncement(Long id, Announcement updatedAnnouncement) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if (announcement.isPresent()) {
            updatedAnnouncement.setId(id);
            return announcementRepository.save(updatedAnnouncement);
        }
        return null;
    }
    /*
    public Announcement addAnnouncement(String title, String description, MultipartFile file) throws IOException {
        Announcement announcement = Announcement.builder()
                .title(title)
                .description(description)
                .displayPicture(file.getBytes())
                .build();
        return announcementRepository.save(announcement);
    }
    */



    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }
}

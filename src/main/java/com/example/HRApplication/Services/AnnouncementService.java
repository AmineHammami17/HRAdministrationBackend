package com.example.HRApplication.Services;

import com.example.HRApplication.Models.Announcement;
import com.example.HRApplication.Repositories.AnnouncementRepository;
import com.example.HRApplication.Repositories.StorageService;
import jdk.dynalink.NamedOperation;
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
    @Autowired
    private StorageService storageService;

    public Announcement uploadAnnouncement(String title, String description, MultipartFile file) throws IOException {
        storageService.store(file);

        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setDescription(description);
        announcement.setDisplayPicture(file.getBytes());

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

    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }

    public byte[] getImage(Long oid) {
        return announcementRepository.getImageByOid(oid);
    }

}

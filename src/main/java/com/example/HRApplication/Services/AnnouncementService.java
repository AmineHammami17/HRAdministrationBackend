package com.example.HRApplication.Services;

import com.example.HRApplication.Models.Announcement;
import com.example.HRApplication.Repositories.AnnouncementRepository;
import com.example.HRApplication.Repositories.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    public Announcement uploadAnnouncement(String title, String description, MultipartFile file, LocalDate date) throws IOException {
        String filename = file.getOriginalFilename();
        fileSystemStorageService.store(file);
        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setDescription(description);
        announcement.setDate(date);
        announcement.setDisplayPictureFilename(filename);

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
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if (announcement.isPresent()) {
            String filename = announcement.get().getDisplayPictureFilename();
            fileSystemStorageService.delete(filename);

            announcementRepository.deleteById(id);
        }
    }

    public byte[] getImage(String filename) {
        try {
            Resource resource = fileSystemStorageService.loadAsResource(filename);
            return resource.getInputStream().readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file", e);
        }
    }
}

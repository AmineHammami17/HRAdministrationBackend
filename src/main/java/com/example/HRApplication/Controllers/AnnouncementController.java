package com.example.HRApplication.Controllers;

import com.example.HRApplication.Models.Announcement;
import com.example.HRApplication.Repositories.AnnouncementRepository;
import com.example.HRApplication.Services.AnnouncementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@Tag(name = "Announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;
    private AnnouncementRepository announcementRepository;





    @GetMapping("/public/api/announcements")
    public ResponseEntity<List<Announcement>> getAllAnnouncements() {
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    @PostMapping(value="admins/api/announcements/upload" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Announcement> uploadAnnouncement(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file) throws IOException {
        Announcement announcement = announcementService.uploadAnnouncement(title, description, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(announcement);
    }



    @GetMapping("/public/api/announcement/{id}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Long id) {
        Optional<Announcement> announcement = announcementService.getAnnouncementById(id);
        return announcement.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/admins/api/announcement/{id}")
    public ResponseEntity<Announcement> updateAnnouncement(@PathVariable Long id, @RequestBody Announcement updatedAnnouncement) {
        Announcement announcement = announcementService.updateAnnouncement(id, updatedAnnouncement);
        if (announcement == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(announcement);
    }

    @DeleteMapping("/admins/api/announcement/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.HRApplication.Models;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="announcement_images",
    joinColumns = {
            @JoinColumn(name="announcement_id")
    },
    inverseJoinColumns = {
            @JoinColumn(name="image_id")
    })
    private Set<ImageModel> AnnouncementImages;

    public Announcement(Long id, String title, String description, Set<ImageModel> announcementImages) {
        this.id = id;
        this.title = title;
        this.description = description;
        AnnouncementImages = announcementImages;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ImageModel> getAnnouncementImages() {
        return AnnouncementImages;
    }

    public void setAnnouncementImages(Set<ImageModel> announcementImages) {
        AnnouncementImages = announcementImages;
    }
}

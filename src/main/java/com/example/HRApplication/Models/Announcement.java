package com.example.HRApplication.Models;

import io.swagger.v3.oas.annotations.info.Info;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "announcements")
@Data
@Builder
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;



    private LocalDate date;

    @Lob
    private Long displayPicture;
    private String displayPictureFilename;

    public Announcement(Long id, String title, String description, LocalDate date, Long displayPicture, String displayPictureFilename) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.displayPicture = displayPicture;
        this.displayPictureFilename = displayPictureFilename;
    }

    public String getDisplayPictureFilename() {
        return displayPictureFilename;
    }

    public void setDisplayPictureFilename(String displayPictureFilename) {
        this.displayPictureFilename = displayPictureFilename;
    }


    public Announcement() {
    }

    public Announcement(Long id, String title, String description, LocalDate date, Long displayPicture) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.displayPicture = displayPicture;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public Long getDisplayPicture() {
        return displayPicture;
    }

    public void setDisplayPicture(Long displayPicture) {
        this.displayPicture = displayPicture;
    }
}

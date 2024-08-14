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

    public Announcement(Long id, String title, String description, LocalDate date, byte[] displayPicture) {
        this.id = id;
        this.title = title;
        this.description = description;
        Date = date;
        this.displayPicture = displayPicture;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    private LocalDate Date;

    @Lob
    private byte[] displayPicture;


    public Announcement(Long id, String title, String description, byte[] displayPicture) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.displayPicture = displayPicture;
    }

    public Announcement() {
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

    public byte[] getDisplayPicture() {
        return displayPicture;
    }

    public void setDisplayPicture(byte[] displayPicture) {
        this.displayPicture = displayPicture;
    }
}

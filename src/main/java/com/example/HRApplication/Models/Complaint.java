package com.example.HRApplication.Models;

import com.example.HRApplication.Models.Enums.ComplaintStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Complaints")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Title")
    private String title;
    @Column(name = "Description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private ComplaintStatus status = ComplaintStatus.NOT_RESOLVED;
    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;
    @Column(name = "UpdatedDate")
    private LocalDateTime updatedDate;

    @Column(name = "ResolutionDate")
    private LocalDateTime resolutionDate;

    @ManyToOne
    @JoinColumn(name = "filed_by_id")
    private User filedBy;

    public Complaint() {
    }

    public Complaint(Long id, String title, String description, ComplaintStatus status, LocalDateTime createdDate, LocalDateTime updatedDate, LocalDateTime resolutionDate, User filedBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.resolutionDate = resolutionDate;
        this.filedBy = filedBy;
    }

    public Complaint(String title, String description, ComplaintStatus status, LocalDateTime createdDate, LocalDateTime updatedDate, LocalDateTime resolutionDate, User filedBy) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.resolutionDate = resolutionDate;
        this.filedBy = filedBy;
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


    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LocalDateTime getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(LocalDateTime resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public User getFiledBy() {
        return filedBy;
    }

    public void setFiledBy(User filedBy) {
        this.filedBy = filedBy;
    }

    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }
}

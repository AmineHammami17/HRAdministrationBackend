package com.example.HRApplication.Models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Holidays")
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", unique = true, nullable = false)
    private Date date;

    @Column(name = "name")
    private String name;

    public Holiday(Long id, Date date, String name) {
        this.id = id;
        this.date = date;
        this.name = name;
    }

    public Holiday() {
    }

   public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String name) {
        this.name = name;
    }
}

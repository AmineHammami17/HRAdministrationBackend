package com.example.HRApplication.Models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Holidays")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", unique = true, nullable = false)
    private LocalDate date;

    @Column(name = "name")
    private String name;

    public Holiday(Long id, LocalDate date, String name) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

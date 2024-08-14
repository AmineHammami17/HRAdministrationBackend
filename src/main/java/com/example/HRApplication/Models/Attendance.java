package com.example.HRApplication.Models;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name="Attendances")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long Attendance_id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name="start_time")
    private LocalTime start_time;

    @Column(name="end_time")
    private LocalTime end_time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Attendance(Long attendance_id, LocalDate date, LocalTime star_time, LocalTime end_time, User user) {
        Attendance_id = attendance_id;
        this.date = date;
        this.start_time = star_time;
        this.end_time = end_time;
        this.user = user;
    }

    public Attendance() {

    }

    public Long getAttendance_id() {
        return Attendance_id;
    }

    public void setAttendance_id(Long attendance_id) {
        Attendance_id = attendance_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStar_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalTime end_time) {
        this.end_time = end_time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

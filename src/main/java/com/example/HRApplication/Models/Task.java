package com.example.HRApplication.Models;


import jakarta.persistence.*;

@Entity
@Table(name = "Tasks")
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
}

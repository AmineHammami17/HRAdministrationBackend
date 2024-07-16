package com.example.HRApplication.Models;

import com.example.HRApplication.Models.Enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "Email",unique = true)
    private String email;

    @Column(name = "Password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "Role")
    private Roles role;

    public User(Integer id, String email, String password, Roles role, Double baseSalary, String firstname, String lastname, String job, LocalDate datejoined, String status, List<SalaryHistory> salaryHistories) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.baseSalary = baseSalary;
        this.firstname = firstname;
        this.lastname = lastname;
        this.job = job;
        this.datejoined = datejoined;
        this.status = status;
        this.salaryHistories = salaryHistories;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    @Column(name = "Base_Salary")
    private Double baseSalary;


    @Column(name = "First_Name")
    private String firstname;

    @Column(name = "Last_Name")
    private String lastname;

    @Column(name = "Job")
    private String job;

    @Column(name = "Date_Joined")
    private LocalDate datejoined;

    @Column(name = "Status")
    private String status;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private List<SalaryHistory> salaryHistories;

    public void setSalaryHistories(List<SalaryHistory> salaryHistories) {
        this.salaryHistories = salaryHistories;
    }

    public User(Integer id, String email, String password, Roles role, String firstname, String lastname, String job, LocalDate datejoined, String status, List<SalaryHistory> salaryHistories) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.job = job;
        this.datejoined = datejoined;
        this.status = status;
        this.salaryHistories = salaryHistories;
    }


    public User(Integer id, String email, String password, Roles role, String firstname, String lastname, String job, LocalDate datejoined, String status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.job = job;
        this.datejoined = datejoined;
        this.status = status;
    }

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public LocalDate getDatejoined() {
        return datejoined;
    }

    public void setDatejoined(LocalDate datejoined) {
        this.datejoined = datejoined;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

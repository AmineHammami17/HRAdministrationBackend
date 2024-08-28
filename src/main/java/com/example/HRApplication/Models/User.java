package com.example.HRApplication.Models;

import com.example.HRApplication.Models.Enums.Roles;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)

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

    private String resetToken;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<SalaryHistory> salaryHistories;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnoreProperties("tasks")
    private List<Task> tasks;


    @Lob
    private Long displayPicture;
    private String displayPictureFilename;

    public User(Integer id, String email, String password, Roles role, Double baseSalary, String firstname, String lastname, String job, LocalDate datejoined, String status, String resetToken, List<SalaryHistory> salaryHistories, List<Task> tasks, Long displayPicture, String displayPictureFilename) {
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
        this.resetToken = resetToken;
        this.salaryHistories = salaryHistories;
        this.tasks = tasks;
        this.displayPicture = displayPicture;
        this.displayPictureFilename = displayPictureFilename;
    }


    public List<SalaryHistory> getSalaryHistories() {
        return salaryHistories;
    }


    public void setSalaryHistories(List<SalaryHistory> salaryHistories) {
        this.salaryHistories = salaryHistories;
    }


    public User() {
    }

    @Override
    @JsonIgnore
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
    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Long getDisplayPicture() {
        return displayPicture;
    }

    public void setDisplayPicture(Long displayPicture) {
        this.displayPicture = displayPicture;
    }

    public String getDisplayPictureFilename() {
        return displayPictureFilename;
    }

    public void setDisplayPictureFilename(String displayPictureFilename) {
        this.displayPictureFilename = displayPictureFilename;
    }


}

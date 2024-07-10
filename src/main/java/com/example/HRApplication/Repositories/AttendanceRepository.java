package com.example.HRApplication.Repositories;

import com.example.HRApplication.Models.Attendance;
import com.example.HRApplication.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUser(User user);
    List<Attendance> findByUserId(Integer userId);
    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByUserIdAndDateBetween(Integer userId, LocalDate startDate, LocalDate endDate);
    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate);
}

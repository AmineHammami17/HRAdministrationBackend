package com.example.HRApplication.Services;

import com.example.HRApplication.Models.Attendance;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Repositories.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public Attendance punchIn(User user) {
        LocalDate today = LocalDate.now();
        if (attendanceRepository.findByUserIdAndDateBetween(user.getId(), today, today).isEmpty()) {
            Attendance attendance = new Attendance();
            attendance.setUser(user);
            attendance.setDate(today);
            attendance.setStar_time(LocalTime.now());
            return attendanceRepository.save(attendance);
        } else {
            throw new RuntimeException("Punch-in already recorded for today.");
        }
    }

    public Attendance punchOut(User user) {
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByUserIdAndDateBetween(user.getId(), today, today).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No punch-in record found for today"));

        if (attendance.getEnd_time() == null) {
            attendance.setEnd_time(LocalTime.now());
            return attendanceRepository.save(attendance);
        } else {
            throw new RuntimeException("Punch-out already recorded for today.");
        }
    }

    public List<Attendance> viewAttendanceForConnectedUser(Integer userId) {
        return attendanceRepository.findByUserId(userId);
    }

    public List<Attendance> viewAttendanceByUserId(Integer userId) {
        return attendanceRepository.findByUserId(userId);
    }

    public List<Attendance> viewAllAttendances() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> viewAttendancesPerMonth(Integer userId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

    public List<Attendance> viewAttendanceByMonth(LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByDateBetween(startDate, endDate);
    }

    public void deleteAttendance(Long attendanceId) {
        attendanceRepository.deleteById(attendanceId);
    }
}

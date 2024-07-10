package com.example.HRApplication.Controllers;

import com.example.HRApplication.Models.Attendance;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("public/api/attendance/punchin")
    public ResponseEntity<Attendance> punchIn(@AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = (User) userDetails;
        Attendance attendance;
        try {
            attendance = attendanceService.punchIn(currentUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(attendance);
    }

    @PostMapping("public/api/attendance/punchout")
    public ResponseEntity<Attendance> punchOut(@AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = (User) userDetails;
        Attendance attendance;
        try {
            attendance = attendanceService.punchOut(currentUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(attendance);
    }

    @GetMapping("public/api/attendance/connectedUser")
    public ResponseEntity<List<Attendance>> viewAttendanceForConnectedUser(@AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = (User) userDetails;
        List<Attendance> attendances = attendanceService.viewAttendanceForConnectedUser(currentUser.getId());
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("admins/api/attendance/user/{userId}")
    public ResponseEntity<List<Attendance>> viewAttendanceByUserId(@PathVariable Integer userId) {
        List<Attendance> attendances = attendanceService.viewAttendanceByUserId(userId);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("admins/api/attendance/all")
    public ResponseEntity<List<Attendance>> viewAllAttendances() {
        List<Attendance> attendances = attendanceService.viewAllAttendances();
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("public/api/attendance/user/{userId}/month")
    public ResponseEntity<List<Attendance>> viewAttendancesPerMonth(@PathVariable Integer userId,
                                                                    @RequestParam LocalDate startDate,
                                                                    @RequestParam LocalDate endDate) {
        List<Attendance> attendances = attendanceService.viewAttendancesPerMonth(userId, startDate, endDate);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/connectedUser/month")
    public ResponseEntity<List<Attendance>> viewAttendanceByMonthForConnectedUser(@AuthenticationPrincipal UserDetails userDetails,
                                                                                  @RequestParam LocalDate startDate,
                                                                                  @RequestParam LocalDate endDate) {
        User currentUser = (User) userDetails;
        List<Attendance> attendances = attendanceService.viewAttendancesPerMonth(currentUser.getId(), startDate, endDate);
        return ResponseEntity.ok(attendances);
    }

    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long attendanceId) {
        try {
            attendanceService.deleteAttendance(attendanceId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

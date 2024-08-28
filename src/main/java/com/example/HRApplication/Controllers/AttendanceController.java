package com.example.HRApplication.Controllers;

import com.example.HRApplication.Models.Attendance;
import com.example.HRApplication.Models.Complaint;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Services.AttendanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@Tag(name="Attendance")

public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/punchin")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')|| hasRole('EMPLOYEE')")

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

    @PostMapping("/punchout")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')|| hasRole('EMPLOYEE')")

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

    @GetMapping("/connectedUser")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')|| hasRole('EMPLOYEE')")

    public ResponseEntity<List<Attendance>> viewAttendanceForConnectedUser(@AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = (User) userDetails;
        List<Attendance> attendances = attendanceService.viewAttendanceForConnectedUser(currentUser.getId());
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")
    public ResponseEntity<List<Attendance>> viewAttendanceByUserId(@PathVariable Integer userId) {
        List<Attendance> attendances = attendanceService.viewAttendanceByUserId(userId);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")
    public ResponseEntity<List<Attendance>> viewAllAttendances() {
        List<Attendance> attendances = attendanceService.viewAllAttendances();
        return ResponseEntity.ok(attendances);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Long id) {
        Attendance attendance = attendanceService.getAttendanceById(id);
        if (attendance == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(attendance);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable Long id, @RequestBody Attendance updatedAttendance) {
        Attendance attendance = attendanceService.updateAttendance(id, updatedAttendance);
        if (attendance == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/user/{userId}/month")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')|| hasRole('EMPLOYEE')")
    public ResponseEntity<List<Attendance>> viewAttendancesPerMonth(@PathVariable Integer userId,
                                                                    @RequestParam LocalDate startDate,
                                                                    @RequestParam LocalDate endDate) {
        List<Attendance> attendances = attendanceService.viewAttendancesPerMonth(userId, startDate, endDate);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/connectedUser/month")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')|| hasRole('EMPLOYEE')")
    public ResponseEntity<List<Attendance>> viewAttendanceByMonthForConnectedUser(@AuthenticationPrincipal UserDetails userDetails,
                                                                                  @RequestParam LocalDate startDate,
                                                                                  @RequestParam LocalDate endDate) {
        User currentUser = (User) userDetails;
        List<Attendance> attendances = attendanceService.viewAttendancesPerMonth(currentUser.getId(), startDate, endDate);
        return ResponseEntity.ok(attendances);
    }

    @DeleteMapping("/{attendanceId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long attendanceId) {
        try {
            attendanceService.deleteAttendance(attendanceId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count-attendances")
    public ResponseEntity<Long> countTotalAttendances() {
        long attendancesCount = attendanceService.countTotalAttendances();
        return ResponseEntity.ok(attendancesCount);
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')|| hasRole('EMPLOYEE')")
    public Boolean testAttendance(@AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = (User) userDetails;
        return attendanceService.testAttendanceByUser(currentUser);
    }
    @GetMapping("/getByDate")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')|| hasRole('EMPLOYEE')")
    public List<Attendance> getAttendanceByDate(@AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = (User) userDetails;
        return attendanceService.getAtendanceBydate(currentUser);
    }
}

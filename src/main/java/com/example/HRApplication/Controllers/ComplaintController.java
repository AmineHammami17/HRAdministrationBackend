package com.example.HRApplication.Controllers;

import com.example.HRApplication.Models.Complaint;
import com.example.HRApplication.Models.Enums.Roles;
import com.example.HRApplication.Models.LeaveRequest;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Services.AuthService;
import com.example.HRApplication.Services.ComplaintService;
import com.example.HRApplication.Services.UserDetailsServ;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name="Complaints")
@RequestMapping("/api/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private AuthService authService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('EMPLOYEE') || hasRole('USER')")
    public ResponseEntity<Complaint> addComplaint(@RequestBody Complaint complaint, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User currentUser = (User) userDetails;
        boolean isAdmin = currentUser.getRole().equals(Roles.ROLE_ADMIN);

        if (!isAdmin) {
            complaint.setFiledBy(currentUser);
        } else {
            if (complaint.getFiledBy() == null || complaint.getFiledBy().getId() == 0) {
                return ResponseEntity.badRequest().body(null);
            }
            User assignedUser = authService.getUserById(complaint.getFiledBy().getId());
            if (assignedUser == null) {
                return ResponseEntity.badRequest().body(null);
            }
            complaint.setFiledBy(assignedUser);
        }

        Complaint createdComplaint = complaintService.addComplaint(complaint);
        return new ResponseEntity<>(createdComplaint, HttpStatus.CREATED);
    }




    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")

    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable Long id) {
        Complaint complaint = complaintService.getComplaintById(id);
        if (complaint == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(complaint);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")
    public ResponseEntity<Complaint> updateComplaint(@PathVariable Long id, @RequestBody Complaint updatedComplaint) {
        Complaint complaint = complaintService.updateComplaint(id, updatedComplaint);
        if (complaint == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(complaint);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")
    public ResponseEntity<Void> deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR') || hasRole('EMPLOYEE')")
    public ResponseEntity<List<Complaint>> getComplaintsByUserId(@PathVariable Integer userId) {
        List<Complaint> complaints = complaintService.getComplaintsByUserId(userId);
        return ResponseEntity.ok(complaints);
    }


}

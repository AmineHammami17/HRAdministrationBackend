package com.example.HRApplication.Controllers;

import com.example.HRApplication.Models.Complaint;
import com.example.HRApplication.Models.LeaveRequest;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Services.ComplaintService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="Complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("public/api/complaint/add")
    public ResponseEntity<Complaint> addComplaint(@RequestBody Complaint complaint,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User currentUser = (User) userDetails;
        complaint.setFiledBy(currentUser);
        Complaint createdComplaint = complaintService.addComplaint(complaint);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComplaint);
    }




    @GetMapping("admins/api/complaint")

    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }

    @GetMapping("admins/api/complaint/{id}")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable Long id) {
        Complaint complaint = complaintService.getComplaintById(id);
        if (complaint == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(complaint);
    }

    @PutMapping("admins/api/complaint/{id}")
    public ResponseEntity<Complaint> updateComplaint(@PathVariable Long id, @RequestBody Complaint updatedComplaint) {
        Complaint complaint = complaintService.updateComplaint(id, updatedComplaint);
        if (complaint == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(complaint);
    }

    @DeleteMapping("admins/api/complaint/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("public/api/complaint/user")
    public ResponseEntity<List<Complaint>> getComplaintsForLoggedInUser(@AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = (User) userDetails;
        List<Complaint> complaints = complaintService.getComplaintsByUser(currentUser);
        return ResponseEntity.ok(complaints);
    }

}

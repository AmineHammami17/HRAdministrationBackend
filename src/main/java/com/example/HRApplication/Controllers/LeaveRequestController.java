package com.example.HRApplication.Controllers;

import com.example.HRApplication.Models.Enums.LeaveRequestStatus;
import com.example.HRApplication.Models.LeaveRequest;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Models.Enums.LeaveReason;
import com.example.HRApplication.Services.LeaveRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/leave-requests")
@Tag(name = "Leave Requests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR') || hasRole('EMPLOYEE')")
    public ResponseEntity<?> createLeaveRequest(
            @RequestBody LeaveRequest leaveRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
        User currentUser = (User) userDetails;
        LeaveRequest createdRequest = leaveRequestService.createLeaveRequest(
                currentUser, leaveRequest.getStartDate(), leaveRequest.getEndDate(),
                leaveRequest.getReason(), LeaveRequestStatus.PENDING);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);}
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{requestId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR') || hasRole('EMPLOYEE')")
    public ResponseEntity<LeaveRequest> updateLeaveRequest(
            @PathVariable Long requestId,
            @RequestBody LeaveRequest leaveRequest) {
        LeaveRequest updatedRequest = leaveRequestService.updateLeaveRequest(
                requestId, leaveRequest.getStartDate(),
                leaveRequest.getEndDate(), leaveRequest.getReason());
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("/{requestId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR') || hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Long requestId) {
        leaveRequestService.deleteLeaveRequest(requestId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/approve/{requestId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")
    public ResponseEntity<?> approveLeaveRequest(@PathVariable Long requestId) {
        try {
            LeaveRequest approvedRequest = leaveRequestService.approveLeaveRequest(requestId);
            return ResponseEntity.ok(approvedRequest);
        }
                catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/deny/{requestId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")
    public ResponseEntity<LeaveRequest> denyLeaveRequest(@PathVariable Long requestId) {
        LeaveRequest deniedRequest = leaveRequestService.denyLeaveRequest(requestId);
        return ResponseEntity.ok(deniedRequest);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests() {
        List<LeaveRequest> leaveRequests = leaveRequestService.getAllLeaveRequests();
        return ResponseEntity.ok(leaveRequests);
    }

    @GetMapping("/{requestId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")
    public ResponseEntity<LeaveRequest> findLeaveRequestById(@PathVariable Long requestId) {
        LeaveRequest leaveRequest = leaveRequestService.findLeaveRequestById(requestId);
        return ResponseEntity.ok(leaveRequest);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByUserId(@PathVariable Integer userId) {
        List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestByUserById(userId);
        return ResponseEntity.ok(leaveRequests);
    }
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR') || hasRole('EMPLOYEE')")
    @GetMapping("/details")
    public Map<String, Integer> getLeaveDetails(
            @RequestParam Integer userId,
            @RequestParam String reason) {

        LeaveReason leaveReason = LeaveReason.fromString(reason);
        return leaveRequestService.getLeaveDetailsForUserAndReason(userId, leaveReason);
    }
    @GetMapping("/admins/leave-days-for-all-users")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")
    public ResponseEntity<Map<Integer, Integer>> getLeaveDaysForAllUsers() {
        Map<Integer, Integer> leaveDaysForAllUsers = leaveRequestService.getLeaveDaysForAllUsers();
        return ResponseEntity.ok(leaveDaysForAllUsers);
    }

    @GetMapping("/employees-on-leave")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")
    public List<User> getEmployeesCurrentlyOnLeave() {
        return leaveRequestService.getEmployeesCurrentlyOnLeave();
    }
}

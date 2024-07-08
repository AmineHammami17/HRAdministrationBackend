package com.example.HRApplication.Controllers;

import com.example.HRApplication.Models.LeaveRequest;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Services.LeaveRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
@Tag(name="Leave Requests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping("public/api/leave-requests/request")
    public ResponseEntity<LeaveRequest> createLeaveRequest(
            @RequestBody LeaveRequest leaveRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = (User) userDetails;
        LeaveRequest createdRequest = leaveRequestService.createLeaveRequest(
                currentUser, leaveRequest.getStartDate(), leaveRequest.getEndDate(),
                leaveRequest.getReason(), "Pending");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @PutMapping("admins/api/leave-requests/{requestId}")
    public ResponseEntity<LeaveRequest> updateLeaveRequest(
            @PathVariable Long requestId,
            @RequestBody LeaveRequest leaveRequest) {
        LeaveRequest updatedRequest = leaveRequestService.updateLeaveRequest(
                requestId, leaveRequest.getStartDate(),
                leaveRequest.getEndDate(), leaveRequest.getReason());
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("admins/api/leave-requests/{requestId}")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Long requestId) {
        leaveRequestService.deleteLeaveRequest(requestId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("hradmin/api/leave-requests/approve/{requestId}")
    public ResponseEntity<LeaveRequest> approveLeaveRequest(@PathVariable Long requestId) {
        LeaveRequest approvedRequest = leaveRequestService.approveLeaveRequest(requestId);
        return ResponseEntity.ok(approvedRequest);
    }

    @PutMapping("hradmin/api/leave-requests/deny/{requestId}")
    public ResponseEntity<LeaveRequest> denyLeaveRequest(@PathVariable Long requestId) {
        LeaveRequest deniedRequest = leaveRequestService.denyLeaveRequest(requestId);
        return ResponseEntity.ok(deniedRequest);
    }

    @GetMapping("admin/api/leave-requests")
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests() {
        List<LeaveRequest> leaveRequests = leaveRequestService.getAllLeaveRequests();
        return ResponseEntity.ok(leaveRequests);
    }

    @GetMapping("admins/api/leave-requests/{requestId}")
    public ResponseEntity<LeaveRequest> findLeaveRequestById(@PathVariable Long requestId) {
        LeaveRequest leaveRequest = leaveRequestService.findLeaveRequestById(requestId);
        return ResponseEntity.ok(leaveRequest);
    }
    @GetMapping("admins/leave-days-for-all-users")
    public ResponseEntity<Map<Long, Integer>> getLeaveDaysForAllUsers() {
        Map<Long, Integer> leaveDaysForAllUsers = leaveRequestService.getLeaveDaysForAllUsers();
        return ResponseEntity.ok(leaveDaysForAllUsers);
    }

}

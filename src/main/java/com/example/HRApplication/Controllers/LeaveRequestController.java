package com.example.HRApplication.Controllers;

import com.example.HRApplication.Models.LeaveRequest;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Services.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping("public/api/leave-requests/request")
    public ResponseEntity<LeaveRequest> createLeaveRequest(
            @RequestBody LeaveRequest leaveRequest) {
        LeaveRequest createdRequest = leaveRequestService.createLeaveRequest(
                leaveRequest.getUser(), leaveRequest.getStartDate(), leaveRequest.getEndDate(),
                leaveRequest.getReason(), "Pending");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @PutMapping("admins/api/leave-requests/{requestId}")
    public ResponseEntity<LeaveRequest> updateLeaveRequest(
            @PathVariable Long requestId,
            @RequestBody LeaveRequest leaveRequest) {
        LeaveRequest updatedRequest = leaveRequestService.updateLeaveRequest(
                requestId, leaveRequest.getStartDate(), leaveRequest.getEndDate(), leaveRequest.getReason());
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

    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests() {
        List<LeaveRequest> leaveRequests = leaveRequestService.getAllLeaveRequests();
        return ResponseEntity.ok(leaveRequests);
    }

    @GetMapping("admins/api/leave-requests/{requestId}")
    public ResponseEntity<LeaveRequest> findLeaveRequestById(@PathVariable Long requestId) {
        LeaveRequest leaveRequest = leaveRequestService.findLeaveRequestById(requestId);
        return ResponseEntity.ok(leaveRequest);
    }
}

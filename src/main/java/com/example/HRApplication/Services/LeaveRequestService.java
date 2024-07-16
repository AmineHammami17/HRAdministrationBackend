package com.example.HRApplication.Services;

import com.example.HRApplication.Models.LeaveRequest;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Repositories.LeaveRequestRepository;
import com.example.HRApplication.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public LeaveRequest createLeaveRequest(User user, LocalDate startDate, LocalDate endDate, String reason , String status) {
        LeaveRequest leaveRequest = new LeaveRequest(user, startDate, endDate, reason, "Pending");
        return leaveRequestRepository.save(leaveRequest);
    }


    public LeaveRequest updateLeaveRequest(Long requestId, LocalDate startDate, LocalDate endDate, String reason) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));


        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setReason(reason);

        return leaveRequestRepository.save(leaveRequest);
    }



    public void deleteLeaveRequest(Long requestId) {
        leaveRequestRepository.deleteById(requestId);
    }

    public LeaveRequest approveLeaveRequest(Long requestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

        leaveRequest.setStatus("Approved");

        return leaveRequestRepository.save(leaveRequest);
    }

    public LeaveRequest denyLeaveRequest(Long requestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

        leaveRequest.setStatus("Denied");

        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }
    public LeaveRequest findLeaveRequestById(Long requestId) {
        return leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));
    }

    public Map<Long, Integer> getLeaveDaysForAllUsers() {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();
        Map<Long, Integer> leaveDaysForAllUsers = new HashMap<>();

        for (LeaveRequest leaveRequest : leaveRequests) {
            Integer userId = leaveRequest.getUser().getId();
            int days = (int) (leaveRequest.getEndDate().toEpochDay() - leaveRequest.getStartDate().toEpochDay());

            leaveDaysForAllUsers.put(Long.valueOf(userId), leaveDaysForAllUsers.getOrDefault(userId, 0) + days);
        }
        return leaveDaysForAllUsers;
    }

}


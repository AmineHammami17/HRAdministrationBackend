package com.example.HRApplication.Services;

import com.example.HRApplication.Models.Enums.LeaveReason;
import com.example.HRApplication.Models.LeaveRequest;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Models.Enums.LeaveRequestStatus;
import com.example.HRApplication.Repositories.LeaveRequestRepository;
import com.example.HRApplication.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public LeaveRequest createLeaveRequest(User user, LocalDate startDate, LocalDate endDate, LeaveReason reason, LeaveRequestStatus status) {
        if (exceedsMaxDaysForUser(user, startDate, endDate, reason)) {
            throw new IllegalArgumentException("Leave request exceeds maximum allowed days for reason: " + reason);
        }
        LeaveRequest leaveRequest = new LeaveRequest(user, startDate, endDate, reason, status);
        return leaveRequestRepository.save(leaveRequest);
    }

    public LeaveRequest updateLeaveRequest(Long requestId, LocalDate startDate, LocalDate endDate, LeaveReason reason) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

        if (exceedsMaxDaysForUser(leaveRequest.getUser(), startDate, endDate, reason)) {
            throw new IllegalArgumentException("Leave request exceeds maximum allowed days for reason: " + reason);
        }

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

        long daysBetween = ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1;

        boolean exceedsMaxDays = exceedsMaxDaysForUser(leaveRequest.getUser(), leaveRequest.getStartDate(), leaveRequest.getEndDate(), leaveRequest.getReason());
        if (exceedsMaxDays) {
            throw new IllegalArgumentException("Approving this leave request would exceed the maximum allowed days for reason: " + leaveRequest.getReason());
        }

        leaveRequest.setStatus(LeaveRequestStatus.APPROVED);
        return leaveRequestRepository.save(leaveRequest);
    }


    public LeaveRequest denyLeaveRequest(Long requestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

        leaveRequest.setStatus(LeaveRequestStatus.REJECTED);

        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public LeaveRequest findLeaveRequestById(Long requestId) {
        return leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));
    }

    public List<LeaveRequest> getLeaveRequestByUserById(Integer userId) {
        return leaveRequestRepository.findByUserId(userId);
    }

    public Map<Integer, Integer> getLeaveDaysForAllUsers() {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();
        Map<Integer, Integer> leaveDaysForAllUsers = new HashMap<>();

        for (LeaveRequest leaveRequest : leaveRequests) {
            Integer userId = leaveRequest.getUser().getId();
            int days = (int) (leaveRequest.getEndDate().toEpochDay() - leaveRequest.getStartDate().toEpochDay() + 1);

            leaveDaysForAllUsers.put(userId, leaveDaysForAllUsers.getOrDefault(userId, 0) + days);
        }
        return leaveDaysForAllUsers;
    }
    public Map<String, Integer> getLeaveDetailsForUserAndReason(Integer userId, LeaveReason reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<LeaveRequest> existingRequests = leaveRequestRepository.findByUserAndReasonAndStatus(user, reason, LeaveRequestStatus.APPROVED);

        int totalDaysTaken = existingRequests.stream()
                .mapToInt(r -> (int) (ChronoUnit.DAYS.between(r.getStartDate(), r.getEndDate()) + 1))
                .sum();

        int maxDays = reason.getMaxDays();
        Map<String, Integer> result = new HashMap<>();
        result.put("currentDays", totalDaysTaken);
        result.put("maxDays", maxDays);

        return result;
    }
    private boolean exceedsMaxDaysForUser(User user, LocalDate startDate, LocalDate endDate, LeaveReason reason) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        int maxDays = reason.getMaxDays();

        if (maxDays == 0) {
            return false;
        }

        List<LeaveRequest> existingRequests = leaveRequestRepository.findByUserAndReasonAndStatus(user, reason, LeaveRequestStatus.APPROVED);
        int totalDaysTaken = existingRequests.stream()
                .mapToInt(r -> (int) (ChronoUnit.DAYS.between(r.getStartDate(), r.getEndDate()) + 1))
                .sum();
        totalDaysTaken += daysBetween;

        return totalDaysTaken > maxDays;
    }

    public List<User> getEmployeesCurrentlyOnLeave() {
        LocalDate today = LocalDate.now();

        List<LeaveRequest> currentLeaveRequests = leaveRequestRepository.findAll().stream()
                .filter(leaveRequest -> leaveRequest.getStatus() == LeaveRequestStatus.APPROVED &&
                        !leaveRequest.getStartDate().isAfter(today) &&
                        !leaveRequest.getEndDate().isBefore(today))
                .collect(Collectors.toList());

        return currentLeaveRequests.stream()
                .map(LeaveRequest::getUser)
                .distinct()
                .collect(Collectors.toList());
    }
}


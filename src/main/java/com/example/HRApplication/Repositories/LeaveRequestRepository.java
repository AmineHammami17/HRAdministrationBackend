package com.example.HRApplication.Repositories;

import com.example.HRApplication.Models.Enums.LeaveReason;
import com.example.HRApplication.Models.Enums.LeaveRequestStatus;
import com.example.HRApplication.Models.LeaveRequest;
import com.example.HRApplication.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByUserId(Integer userId);
    List<LeaveRequest> findByUser(User user);

    List<LeaveRequest> findByUserAndReasonAndStatus(User user , LeaveReason reason , LeaveRequestStatus status);


}
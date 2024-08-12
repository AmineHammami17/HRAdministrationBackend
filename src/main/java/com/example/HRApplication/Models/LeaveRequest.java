package com.example.HRApplication.Models;

import com.example.HRApplication.Models.Enums.LeaveReason;
import com.example.HRApplication.Models.Enums.LeaveRequestStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "LeaveRequests")
public class LeaveRequest {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public LeaveRequest(User user, LocalDate startDate, LocalDate endDate, LeaveReason reason, LeaveRequestStatus status) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "reason")
    private LeaveReason reason;


    public LeaveRequestStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveRequestStatus status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="status")

    private LeaveRequestStatus status;

    public LeaveRequest(Long requestId, User user, LocalDate startDate, LocalDate endDate, LeaveReason reason) {
        this.requestId = requestId;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    public LeaveRequest() {
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LeaveReason getReason() {
        return reason;
    }

    public void setReason(LeaveReason reason) {
        this.reason = reason;
    }
}

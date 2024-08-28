package com.example.HRApplication.Services;

import com.example.HRApplication.Models.Attendance;
import com.example.HRApplication.Models.Complaint;
import com.example.HRApplication.Models.Enums.ComplaintStatus;
import com.example.HRApplication.Models.User;
import com.example.HRApplication.Repositories.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public Complaint addComplaint(Complaint complaint) {
        complaint.setStatus(ComplaintStatus.IN_PROGRESS);
        complaint.setCreatedDate(LocalDateTime.now());
        return complaintRepository.save(complaint);
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint getComplaintById(Long id) {
        Optional<Complaint> complaintOptional = complaintRepository.findById(id);
        return complaintOptional.orElse(null);
    }

    public Complaint updateComplaint(Long id, Complaint updatedComplaint) {
        Optional<Complaint> complaintOptional = complaintRepository.findById(id);
        if (complaintOptional.isPresent()) {
            Complaint complaint = complaintOptional.get();
            complaint.setTitle(updatedComplaint.getTitle());
            complaint.setDescription(updatedComplaint.getDescription());
            complaint.setStatus(updatedComplaint.getStatus());
            complaint.setUpdatedDate(LocalDateTime.now());
            if (updatedComplaint.getStatus() == ComplaintStatus.RESOLVED) {
                complaint.setResolutionDate(LocalDateTime.now());
            }
            return complaintRepository.save(complaint);
        }
        return null;
    }
    public List<Complaint> getComplaintsByUserId(Integer userId) {
        return complaintRepository.findByFiledById(userId);
    }

    public void deleteComplaint(Long id) {
        complaintRepository.deleteById(id);
    }
}

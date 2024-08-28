package com.example.HRApplication.Repositories;

import com.example.HRApplication.Models.Complaint;
import com.example.HRApplication.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
    List<Complaint> findByFiledById(Integer userId);


}

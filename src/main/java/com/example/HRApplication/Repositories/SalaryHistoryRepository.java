package com.example.HRApplication.Repositories;

import com.example.HRApplication.Models.SalaryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryHistoryRepository extends JpaRepository<SalaryHistory, Integer> {
    List<SalaryHistory> findByUserId(Integer userId);
}

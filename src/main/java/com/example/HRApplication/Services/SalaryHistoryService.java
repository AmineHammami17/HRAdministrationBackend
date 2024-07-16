package com.example.HRApplication.Services;

import com.example.HRApplication.Models.SalaryHistory;
import com.example.HRApplication.Repositories.SalaryHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryHistoryService {
    @Autowired
    private SalaryHistoryRepository salaryHistoryRepository;

    public List<SalaryHistory> getSalaryHistoryByUserId(Integer userId) {
        return salaryHistoryRepository.findByUserId(userId);
    }

    public SalaryHistory saveSalaryHistory(SalaryHistory salaryHistory) {
        return salaryHistoryRepository.save(salaryHistory);
    }
}

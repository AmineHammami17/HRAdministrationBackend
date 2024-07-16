package com.example.HRApplication.Controllers;

import com.example.HRApplication.Models.SalaryHistory;
import com.example.HRApplication.Services.SalaryHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SalaryHistoryController {
    @Autowired
    private SalaryHistoryService salaryHistoryService;

    @GetMapping("public/salary-history/user/{userId}")
    public ResponseEntity<List<SalaryHistory>> getSalaryHistoryByUserId(@PathVariable Integer userId) {
        List<SalaryHistory> salaryHistories = salaryHistoryService.getSalaryHistoryByUserId(userId);
        return ResponseEntity.ok(salaryHistories);
    }

    @PostMapping("admins/salary-history")
    public ResponseEntity<SalaryHistory> addSalaryHistory(@RequestBody SalaryHistory salaryHistory) {
        SalaryHistory savedHistory = salaryHistoryService.saveSalaryHistory(salaryHistory);
        return ResponseEntity.ok(savedHistory);
    }
}

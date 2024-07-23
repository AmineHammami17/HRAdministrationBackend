package com.example.HRApplication.Controllers;

import com.example.HRApplication.Models.SalaryHistory;
import com.example.HRApplication.Services.SalaryHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salary-history")
@Tag(name="Salary History")

public class SalaryHistoryController {
    @Autowired
    private SalaryHistoryService salaryHistoryService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR') || hasRole('EMPLOYEE') ")

    public ResponseEntity<List<SalaryHistory>> getSalaryHistoryByUserId(@PathVariable Integer userId) {
        List<SalaryHistory> salaryHistories = salaryHistoryService.getSalaryHistoryByUserId(userId);
        return ResponseEntity.ok(salaryHistories);
    }

    @PostMapping("/salary-history")
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR')")

    public ResponseEntity<SalaryHistory> addSalaryHistory(@RequestBody SalaryHistory salaryHistory) {
        SalaryHistory savedHistory = salaryHistoryService.saveSalaryHistory(salaryHistory);
        return ResponseEntity.ok(savedHistory);
    }
}

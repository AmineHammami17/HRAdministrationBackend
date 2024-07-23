package com.example.HRApplication.Controllers;

import com.example.HRApplication.Models.Holiday;
import com.example.HRApplication.Services.HolidayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holidays")
@Tag(name="Holidays , Calendar")

public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINHR') || hasRole('EMPLOYEE')")

    public ResponseEntity<List<Holiday>> getAllHolidays() {
        List<Holiday> holidays = holidayService.getAllHolidays();
        return ResponseEntity.ok(holidays);
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<Holiday> addHoliday(@RequestBody Holiday holiday) {
        Holiday savedHoliday = holidayService.addHoliday(holiday);
        return ResponseEntity.ok(savedHoliday);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        holidayService.deleteHoliday(id);
        return ResponseEntity.noContent().build();
    }
}

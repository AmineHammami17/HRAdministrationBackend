package com.example.HRApplication.Controllers;

import com.example.HRApplication.Models.Holiday;
import com.example.HRApplication.Models.Project;
import com.example.HRApplication.Services.HolidayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/holidays")
@Tag(name="Holidays , Calendar")

public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMINHR') or hasRole('ROLE_EMPLOYEE')")

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


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Holiday> updateHoliday(@PathVariable Long id, @RequestBody Holiday updatedHoliday) {
        try {
            Holiday holiday = holidayService.updateHoliday(id, updatedHoliday);
            return new ResponseEntity<>(holiday, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Holiday> getHolidayById(@PathVariable Long id) {
        Optional<Holiday> holiday = holidayService.getHolidayById(id);
        return holiday.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/upcoming-holidays")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMINHR') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<List<Holiday>> getUpcomingHolidays() {
        List<Holiday> holidays = holidayService.getUpcomingHolidays();
        return ResponseEntity.ok(holidays);
    }



}

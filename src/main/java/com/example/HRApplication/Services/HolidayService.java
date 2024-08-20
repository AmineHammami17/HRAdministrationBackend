package com.example.HRApplication.Services;

import com.example.HRApplication.Models.Holiday;
import com.example.HRApplication.Repositories.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }

    public Holiday addHoliday(Holiday holiday) {
        return holidayRepository.save(holiday);
    }

    public void deleteHoliday(Long id) {
        holidayRepository.deleteById(id);
    }

    public Holiday updateHoliday(Long id, Holiday updatedHoliday) {
        if (holidayRepository.existsById(id)) {
            updatedHoliday.setId(id);
            return holidayRepository.save(updatedHoliday);
        } else {
            throw new RuntimeException("Holiday not found with id: " + id);
        }
    }

    public Optional<Holiday> getHolidayById(Long id) {
        return holidayRepository.findById(id);
    }
    public List<Holiday> getUpcomingHolidays() {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(31);
        return holidayRepository.findHolidaysBetweenDates(today, endDate);
    }
}

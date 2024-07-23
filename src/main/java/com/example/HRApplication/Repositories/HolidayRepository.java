package com.example.HRApplication.Repositories;

import com.example.HRApplication.Models.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<Holiday> findByDate(Date date);
}

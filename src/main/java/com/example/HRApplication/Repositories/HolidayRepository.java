package com.example.HRApplication.Repositories;

import com.example.HRApplication.Models.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<Holiday> findByDate(LocalDate date);

    @Query("SELECT h FROM Holiday h WHERE h.date BETWEEN :startDate AND :endDate")
    List<Holiday> findHolidaysBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}

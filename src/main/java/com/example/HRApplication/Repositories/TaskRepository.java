package com.example.HRApplication.Repositories;

import com.example.HRApplication.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Integer userId);

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.date BETWEEN :startDate AND :endDate")
    List<Task> findTasksByUserIdAndDateRange(@Param("userId") Integer userId,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);
}

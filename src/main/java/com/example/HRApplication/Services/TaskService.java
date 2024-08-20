package com.example.HRApplication.Services;

import com.example.HRApplication.Models.Holiday;
import com.example.HRApplication.Models.Task;
import com.example.HRApplication.Repositories.HolidayRepository;
import com.example.HRApplication.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private HolidayRepository holidayRepository;

    public Task createTask(Task task) {
        if (isWorkDay(task.getDate())) {
            return taskRepository.save(task);
        } else {
            throw new IllegalArgumentException("Cannot add task on non-working day");
        }
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByUser(Integer userId) {
        return taskRepository.findByUserId(userId);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(Long id, Task updatedTask) {
        if (taskRepository.existsById(id)) {
            if (isWorkDay(updatedTask.getDate())) {
                updatedTask.setId(id);
                return taskRepository.save(updatedTask);
            } else {
                throw new IllegalArgumentException("Cannot update task on non-working day");
            }
        } else {
            throw new RuntimeException("Task not found with id: " + id);
        }
    }

    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Task not found with id: " + id);
        }
    }

    private boolean isWorkDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return false;
        }

        List<Holiday> holidays = holidayRepository.findByDate(date);
        return holidays.isEmpty();
    }

    public List<Task> getTasksForWeek(LocalDate date, Integer userId){
        LocalDate startOfWeek=date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek=date.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));

        return taskRepository.findTasksByUserIdAndDateRange(userId,startOfWeek,endOfWeek);

    }

}

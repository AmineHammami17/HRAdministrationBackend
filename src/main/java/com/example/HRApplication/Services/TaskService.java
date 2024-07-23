package com.example.HRApplication.Services;

import com.example.HRApplication.Models.Holiday;
import com.example.HRApplication.Models.Task;
import com.example.HRApplication.Repositories.HolidayRepository;
import com.example.HRApplication.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private HolidayRepository holidayRepository;

    // Create a new task
    public Task createTask(Task task) {
        if (isWorkDay(task.getDate())) {
            return taskRepository.save(task);
        } else {
            throw new IllegalArgumentException("Cannot add task on non-working day");

        }
    }

    // Retrieve all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Retrieve tasks by user ID
    public List<Task> getTasksByUser(Integer userId) {
        return taskRepository.findByUserId(userId);
    }

    // Retrieve a task by its ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Update an existing task
    public Task updateTask(Long id, Task updatedTask) {
        if (isWorkDay(updatedTask.getDate())) {
            if (taskRepository.existsById(id)) {
            updatedTask.setId(id);
            return taskRepository.save(updatedTask);}
            else{
                throw new IllegalArgumentException("Cannot update task on non-working day");

            }
        } else {
            throw new RuntimeException("Task not found with id: " + id);
        }
    }

    // Delete a task by its ID
    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Task not found with id: " + id);
        }
    }

// Check if the day is workday or a holiday
    private boolean isWorkDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return false;
        }

        List<Holiday> holidays = holidayRepository.findByDate(date);
        return holidays.isEmpty();
    }

}

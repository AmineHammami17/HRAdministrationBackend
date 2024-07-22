package com.example.HRApplication.Services;

import com.example.HRApplication.Models.Task;
import com.example.HRApplication.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Create a new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
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
        if (taskRepository.existsById(id)) {
            updatedTask.setId(id); // Ensure the ID is set for update
            return taskRepository.save(updatedTask);
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
}

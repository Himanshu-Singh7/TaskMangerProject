package com.task.management.Service;

import com.task.management.Entity.Task;

import java.util.List;

public interface TaskService {
     // Create
    Task createTask(Task task);
    // Update Tasks
    Task updateTask(Long id, Task task);
    //Get all Task
    List<Task> getAllTasks();
    // Get taskById
    Task getTaskId(Long id);

    // delete Task
    void deleteTask(Long id);
}

package com.task.management.Service.Impl;

import com.task.management.Entity.Task;
import com.task.management.Exception.ResourceNotFoundException;
import com.task.management.Repository.TaskRepository;
import com.task.management.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	// Create
	@Override
	public Task createTask(Task task) {

		return taskRepository.save(task);
	}

	// Update
	@Override
	public Task updateTask(Long id, Task task) {
		Task task1 = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task is not found by id :", "Task id", id));
		task1.setTitle(task.getTitle());
		task1.setDescription(task.getDescription());
		task1.setDueDate(task.getDueDate());
		task1.setStatus(task.getStatus());
		Task updateTask = taskRepository.save(task1);
		return updateTask;
	}

	// Read all Task
	@Override
	public List<Task> getAllTasks() {
		List<Task> findAll = taskRepository.findAll();
		List<Task> findAlltask = findAll.stream().map(task -> task).collect(Collectors.toList());
		return findAlltask;
	}

	// Read Task By ID
	@Override
	public Task getTaskId(Long id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task is not found by id :", "Task id", id));
		return task;
	}

	// Delete Task
	@Override
	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}
}

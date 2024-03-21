package com.task.management.Controller;

import com.task.management.Entity.Task;
import com.task.management.Payload.ApiResponse;
import com.task.management.Service.TaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    
    // http://localhost:9191/api/tasks/savetask
    //Save Task
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/savetask")
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task createTask = this.taskService.createTask(task);
        return new ResponseEntity<>(createTask, HttpStatus.CREATED);
    }
    
    // http://localhost:9191/api/tasks/update/id
    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id , @RequestBody Task task){
		Task updateTask = this.taskService.updateTask(id, task);
    	return new ResponseEntity<Task>(updateTask,HttpStatus.OK);
    	
    }
    
    // http://localhost:9191/api/tasks/
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTask(){
    	List<Task> allTasks = this.taskService.getAllTasks();
    	return new ResponseEntity<List<Task>>(allTasks, HttpStatus.OK);
    }
    
   // http://localhost:9191/api/tasks/id
    
    @GetMapping("/{id}") 
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id){
    	 Task taskbyId = this.taskService.getTaskId(id);
    	 return new ResponseEntity<Task>(taskbyId,HttpStatus.OK);
     }
    
    // http://localhost:9191/api/tasks/id
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable ("id")Long id){
    	this.taskService.deleteTask(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Task is successfully deleted" , true), HttpStatus.OK);
    }

}

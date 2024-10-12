package com.fall2024devops.taskmanager.tasks.controller;

import com.fall2024devops.taskmanager.tasks.dto.TaskDTO.TaskDTOOutput;
import com.fall2024devops.taskmanager.common.response.GenericResponse;
import com.fall2024devops.taskmanager.tasks.dto.CreateTaskDTO;
import com.fall2024devops.taskmanager.tasks.dto.ListTaskDTO;
import com.fall2024devops.taskmanager.tasks.dto.TaskDTO;
import com.fall2024devops.taskmanager.tasks.service.TaskService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<GenericResponse<CreateTaskDTO.Output>> createTask(@RequestBody @Valid CreateTaskDTO.Input input) {
        CreateTaskDTO.Output createdTask = taskService.createTask(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>("Task created successfully", createdTask));
    }

    /* 
     * Mark: This was previously named getTasksById but it only returned one task.
     * So I've revised it's name to match its output
     */
    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<ListTaskDTO.Output>> getTaskById(@PathVariable Long id) {
        ListTaskDTO.Output task = taskService.getTaskById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("Task retrieved successfully", task));
    }

    /* 
     * Mark: Utilizing the Tasks DTOs to return all the tasks. 
     */ 
    @GetMapping
    public ResponseEntity<GenericResponse<List<TaskDTO.Output>>> getAllTasks() {
        List<TaskDTO.Output> tasks = taskService.getAllTasks();
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("Tasks retrieved successfully", tasks));
    }
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<GenericResponse<TaskDTO.TaskDTOOutput>> updateTask(@PathVariable Long id, @RequestBody TaskDTO.TaskDTOInput taskDto) {
//        TaskDTO.TaskDTOOutput updatedTask = taskService.updateTask(id, taskDto);
//        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("Task updated successfully", updatedTask));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
//        taskService.deleteTask(id);
//        return ResponseEntity.noContent().build();
//    }
}

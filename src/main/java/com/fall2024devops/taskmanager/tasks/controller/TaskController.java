package com.fall2024devops.taskmanager.tasks.controller;

import com.fall2024devops.taskmanager.tasks.dto.TaskDTO.TaskDTOOutput;
import com.fall2024devops.taskmanager.common.response.GenericResponse;
import com.fall2024devops.taskmanager.tasks.dto.CreateTaskDTO;
import com.fall2024devops.taskmanager.tasks.dto.ListTasksDTO;
import com.fall2024devops.taskmanager.tasks.dto.ListTasksDTO.Output;
import com.fall2024devops.taskmanager.tasks.service.TaskService;
import jakarta.validation.Valid;
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

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<ListTasksDTO.Output>> getTaskById(@PathVariable Long id) {
        ListTasksDTO.Output task = taskService.getTaskById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("Task retrieved successfully", task));
    }

   @GetMapping
   public ResponseEntity<GenericResponse<List<Output>>> getAllTasks() {
       List<Output> tasks = taskService.getAllTasks();
       return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("Tasks retrieved successfully", tasks));
   }

   @PatchMapping("/{id}")
   public ResponseEntity<GenericResponse<com.fall2024devops.taskmanager.tasks.dto.UpdateTaskDTO.Output>> updateTask(@PathVariable Long id, @RequestBody TaskDTOOutput taskDto) {
       com.fall2024devops.taskmanager.tasks.dto.UpdateTaskDTO.Output updatedTask = taskService.updateTask(id, taskDto);
       return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("Task updated successfully", updatedTask));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
       taskService.deleteTask(id);
       return ResponseEntity.noContent().build();
   }
}

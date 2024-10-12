package com.fall2024devops.taskmanager.tasks.service;

import com.fall2024devops.taskmanager.common.exception.NotFoundException;
import com.fall2024devops.taskmanager.common.exception.UnauthorizedException;
import com.fall2024devops.taskmanager.common.utils.SecurityUtils;
import com.fall2024devops.taskmanager.tasks.dto.CreateTaskDTO;
import com.fall2024devops.taskmanager.tasks.dto.ListTasksDTO;
import com.fall2024devops.taskmanager.tasks.dto.TaskDTO;
import com.fall2024devops.taskmanager.tasks.entity.Task;
import com.fall2024devops.taskmanager.tasks.repository.TaskRepository;
import com.fall2024devops.taskmanager.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public CreateTaskDTO.Output createTask(CreateTaskDTO.Input input) {
        User currentUser = SecurityUtils.getCurrentUser()
                .orElseThrow(() -> new UnauthorizedException("User not authenticated"));

        Task task = Task.builder()
                .title(input.getTitle())
                .description(input.getDescription())
                .status("IN_PROGRESS") // Default status
                .user(currentUser)
                .build();

        Task savedTask = taskRepository.save(task);

        return new CreateTaskDTO.Output(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getStatus(),
                savedTask.getUser().getId(),
                savedTask.getCreatedAt(),
                savedTask.getUpdatedAt(),
                savedTask.getDeletedAt()
        );
    }

    public ListTasksDTO.Output getTaskById(Long id) {
        Task foundTask = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));
        return new ListTasksDTO.Output(
                foundTask.getId(),
                foundTask.getTitle(),
                foundTask.getDescription(),
                foundTask.getStatus(),
                foundTask.getUser().getId(),
                foundTask.getCreatedAt(),
                foundTask.getUpdatedAt()
        );
    }

    public List<TaskDTO.Output> getAllTasks() {
        User currentUser = SecurityUtils.getCurrentUser()
                .orElseThrow(() -> new UnauthorizedException("User not authenticated"));

        List<Task> tasks = taskRepository.findByUser(currentUser); // Assuming a method to find tasks by user

        return tasks.stream().map(task -> new TaskDTO.Output(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getUser().getId(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        )).collect(Collectors.toList());
    }

    public TaskDTO.Output updateTask(Long id, TaskDTO.Input input) {
        User currentUser = SecurityUtils.getCurrentUser()
                .orElseThrow(() -> new UnauthorizedException("User not authenticated"));
    
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));
    
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to update this task");
        }
    
        task.setTitle(input.getTitle());
        task.setDescription(input.getDescription());
        task.setStatus(input.getStatus()); // Ensure this method is available in the Task entity
    
        Task updatedTask = taskRepository.save(task);
    
        return new TaskDTO.Output(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getStatus(),
                updatedTask.getUser().getId(),
                updatedTask.getCreatedAt(),
                updatedTask.getUpdatedAt()
        );
    }
    

    public void deleteTask(Long id) {
        User currentUser = SecurityUtils.getCurrentUser()
                .orElseThrow(() -> new UnauthorizedException("User not authenticated"));

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to delete this task");
        }

        taskRepository.delete(task);
    }
}

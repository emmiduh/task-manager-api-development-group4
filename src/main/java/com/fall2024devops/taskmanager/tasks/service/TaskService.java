package com.fall2024devops.taskmanager.tasks.service;

import com.fall2024devops.taskmanager.common.exception.NotFoundException;
import com.fall2024devops.taskmanager.common.exception.UnauthorizedException;
import com.fall2024devops.taskmanager.common.utils.SecurityUtils;
import com.fall2024devops.taskmanager.tasks.dto.CreateTaskDTO;
import com.fall2024devops.taskmanager.tasks.dto.ListTaskDTO;
import com.fall2024devops.taskmanager.tasks.dto.TaskDTO;
import com.fall2024devops.taskmanager.tasks.entity.Task;
import com.fall2024devops.taskmanager.tasks.repository.TaskRepository;
import com.fall2024devops.taskmanager.user.entity.User;

import java.util.ArrayList;
import java.util.List;

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
                .status("IN_PROGRESS")
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
    /* Mark: This was previously named getTasksById but it only returned one task.
     So I've revised it's name to match its output */
    public ListTaskDTO.Output getTaskById(Long id) {
        Task foundTask = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        return new ListTaskDTO.Output(
                foundTask.getId(),
                foundTask.getTitle(),
                foundTask.getDescription(),
                foundTask.getStatus(),
                foundTask.getUser().getId(),
                foundTask.getCreatedAt(),
                foundTask.getUpdatedAt()
        );
    }

    public List<TaskDTO.Output> getAllTasks(){
        List<Task> foundTasks = taskRepository.findAll();
        List<TaskDTO.Output> outputTasks = new ArrayList<>();
        for(Task task : foundTasks){
            outputTasks.add(new TaskDTO.Output(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getStatus(),
                    task.getUser().getId(),
                    task.getCreatedAt(),
                    task.getUpdatedAt()
            ));
        }

        return outputTasks;
    }

}

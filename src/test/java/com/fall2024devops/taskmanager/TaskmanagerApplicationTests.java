package com.fall2024devops.taskmanager;

import com.fall2024devops.taskmanager.common.response.GenericResponse;
import com.fall2024devops.taskmanager.tasks.controller.TaskController;
import com.fall2024devops.taskmanager.tasks.dto.CreateTaskDTO;
import com.fall2024devops.taskmanager.tasks.dto.ListTasksDTO;
import com.fall2024devops.taskmanager.tasks.dto.TaskDTO;
import com.fall2024devops.taskmanager.tasks.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_ShouldReturnCreatedTask() {
        // Arrange
        CreateTaskDTO.Input input = new CreateTaskDTO.Input();
        CreateTaskDTO.Output output = new CreateTaskDTO.Output();
        when(taskService.createTask(any(CreateTaskDTO.Input.class))).thenReturn(output);

        // Act
        ResponseEntity<GenericResponse<CreateTaskDTO.Output>> response = taskController.createTask(input);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Task created successfully", response.getBody().getMessage());
        assertEquals(output, response.getBody().getData());
        verify(taskService, times(1)).createTask(input);
    }

    @Test
    void getTaskById_ShouldReturnTask() {
        // Arrange
        Long taskId = 1L;
        ListTasksDTO.Output task = new ListTasksDTO.Output();
        when(taskService.getTaskById(taskId)).thenReturn(task);

        // Act
        ResponseEntity<GenericResponse<ListTasksDTO.Output>> response = taskController.getTaskById(taskId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Task retrieved successfully", response.getBody().getMessage());
        assertEquals(task, response.getBody().getData());
        verify(taskService, times(1)).getTaskById(taskId);
    }

    @Test
    void getAllTasks_ShouldReturnListOfTasks() {
        // Arrange
        List<ListTasksDTO.Output> tasks = Collections.singletonList(new ListTasksDTO.Output());
        when(taskService.getAllTasks()).thenReturn(tasks);

        // Act
        ResponseEntity<GenericResponse<List<ListTasksDTO.Output>>> response = taskController.getAllTasks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tasks retrieved successfully", response.getBody().getMessage());
        assertEquals(tasks, response.getBody().getData());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() {
        // Arrange
        Long taskId = 1L;
        com.fall2024devops.taskmanager.tasks.dto.TaskDTO.TaskDTOOutput taskDto = new com.fall2024devops.taskmanager.tasks.dto.TaskDTO.TaskDTOOutput();
        com.fall2024devops.taskmanager.tasks.dto.UpdateTaskDTO.Output updatedTask = new com.fall2024devops.taskmanager.tasks.dto.UpdateTaskDTO.Output();
        
        when(taskService.updateTask(eq(taskId), any(com.fall2024devops.taskmanager.tasks.dto.TaskDTO.TaskDTOOutput.class))).thenReturn(updatedTask);

        // Act
        ResponseEntity<GenericResponse<com.fall2024devops.taskmanager.tasks.dto.UpdateTaskDTO.Output>> response = taskController.updateTask(taskId, taskDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Task updated successfully", response.getBody().getMessage());
        assertEquals(updatedTask, response.getBody().getData());
        verify(taskService, times(1)).updateTask(eq(taskId), any(com.fall2024devops.taskmanager.tasks.dto.TaskDTO.TaskDTOOutput.class));
    }

    @Test
    void deleteTask_ShouldReturnNoContent() {
        // Arrange
        Long taskId = 1L;

        // Act
        ResponseEntity<Void> response = taskController.deleteTask(taskId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(taskId);
    }
}

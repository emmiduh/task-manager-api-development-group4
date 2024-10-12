package com.fall2024devops.taskmanager.tasks.controller;

import com.fall2024devops.taskmanager.common.response.GenericResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import com.fall2024devops.taskmanager.tasks.dto.CreateTaskDTO;
import com.fall2024devops.taskmanager.tasks.dto.ListTasksDTO;
import com.fall2024devops.taskmanager.tasks.dto.TaskDTO;
import com.fall2024devops.taskmanager.tasks.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createTask_ShouldReturnCreatedTask() throws Exception {
        // Arrange
        CreateTaskDTO.Input input = new CreateTaskDTO.Input("Test Task", "Description of test task");
        CreateTaskDTO.Output output = new CreateTaskDTO.Output(1L, "Test Task", "Description of test task", "IN_PROGRESS", 1L, LocalDateTime.now(), LocalDateTime.now(), null);

        when(taskService.createTask(any(CreateTaskDTO.Input.class))).thenReturn(output);

        // Act & Assert
        mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Task created successfully"))
                .andExpect(jsonPath("$.payload.title").value("Test Task"));
    }

    @Test
    public void getTaskById_ShouldReturnTask() throws Exception {
        // Arrange
        ListTasksDTO.Output output = new ListTasksDTO.Output(1L, "Test Task", "Description of test task", "IN_PROGRESS", 1L, LocalDateTime.now(), LocalDateTime.now());

        when(taskService.getTaskById(1L)).thenReturn(output);

        // Act & Assert
        mockMvc.perform(get("/api/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Task retrieved successfully"))
                .andExpect(jsonPath("$.payload.title").value("Test Task"));
    }

    @Test
    public void getAllTasks_ShouldReturnListOfTasks() throws Exception {
        // Arrange
        TaskDTO.Output task1 = new TaskDTO.Output(1L, "Task 1", "Description of task 1", "IN_PROGRESS", 1L, LocalDateTime.now(), LocalDateTime.now());
        TaskDTO.Output task2 = new TaskDTO.Output(2L, "Task 2", "Description of task 2", "IN_PROGRESS", 1L, LocalDateTime.now(), LocalDateTime.now());
        
        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        // Act & Assert
        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Tasks retrieved successfully"))
                .andExpect(jsonPath("$.payload.length()").value(2));
    }
    
    
    @Test
    public void updateTask_ShouldReturnUpdatedTask() throws Exception {
        // Arrange
        TaskDTO.Input input = new TaskDTO.Input("Updated Task", "Updated Description", "IN_PROGRESS");
        TaskDTO.Output output = new TaskDTO.Output(1L, "Updated Task", "Updated Description", "IN_PROGRESS", 1L, LocalDateTime.now(), LocalDateTime.now());

        // Mock the service method
        when(taskService.updateTask(eq(1L), any(TaskDTO.Input.class))).thenReturn(output);

        // Act & Assert
        mockMvc.perform(put("/api/v1/tasks/1")  // Ensure this is using PUT
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Task updated successfully"))
            .andExpect(jsonPath("$.payload.title").value("Updated Task"));
    }

    
    @Test
    public void deleteTask_ShouldReturnNoContent() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/v1/tasks/1"))
                .andExpect(status().isNoContent());
    }
}

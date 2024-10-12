package com.fall2024devops.taskmanager.tasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*  
 * Mark: We can utilize this DTO for not just the GetAllTasks service, but any other useful
 * ones such as DeleteTask.
 */
public class TaskDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Output {
        private Long id;
        private String title;
        private String description;
        private String status;
        private Long userId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}



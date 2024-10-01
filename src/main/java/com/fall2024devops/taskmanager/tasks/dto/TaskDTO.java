package com.fall2024devops.taskmanager.tasks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class TaskDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Input {
        private String title;
        private String description;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Output {
        private Long id;
        private String title;
        private String description;
        private String status;
        private Long userId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    public class TaskDTOOutput {

        public String getTitle() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getTitle'");
        }

		public String getDescription() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Unimplemented method 'getDescription'");
		}

		public String getStatus() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Unimplemented method 'getStatus'");
		}
    }
}
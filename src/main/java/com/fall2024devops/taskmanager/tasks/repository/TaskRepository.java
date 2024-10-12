package com.fall2024devops.taskmanager.tasks.repository;

import com.fall2024devops.taskmanager.tasks.entity.Task;
import com.fall2024devops.taskmanager.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Method to find all tasks by a specific user
    List<Task> findByUser(User user);
}

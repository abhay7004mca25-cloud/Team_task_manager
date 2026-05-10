package com.taskmanager.backend.controller;

import com.taskmanager.backend.model.Status;
import com.taskmanager.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired private TaskRepository taskRepository;

    @GetMapping
    public Map<String, Long> getDashboardStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalTasks", taskRepository.countTotalTasks());
        stats.put("todoTasks", taskRepository.countTasksByStatus(Status.TODO));
        stats.put("inProgressTasks", taskRepository.countTasksByStatus(Status.IN_PROGRESS));
        stats.put("doneTasks", taskRepository.countTasksByStatus(Status.DONE));
        stats.put("overdueTasks", taskRepository.countOverdueTasks(LocalDate.now()));
        return stats;
    }
}

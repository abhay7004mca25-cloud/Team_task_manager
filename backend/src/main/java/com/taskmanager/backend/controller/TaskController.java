package com.taskmanager.backend.controller;

import com.taskmanager.backend.dto.TaskDto;
import com.taskmanager.backend.model.*;
import com.taskmanager.backend.repository.*;
import com.taskmanager.backend.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired private TaskRepository taskRepository;
    @Autowired private ProjectRepository projectRepository;
    @Autowired private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDto dto, Authentication auth) {
        Project project = projectRepository.findById(dto.getProjectId()).orElseThrow();
        User admin = userRepository.findById(((UserDetailsImpl) auth.getPrincipal()).getId()).orElseThrow();
        
        // Admin (Creator) validation
        if(!project.getCreatedBy().getId().equals(admin.getId())) {
            return ResponseEntity.status(403).body("Only the project admin (creator) can create tasks for this project");
        }

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setPriority(dto.getPriority());
        task.setStatus(Status.TODO);
        task.setProject(project);
        
        if (dto.getAssignedToId() != null) {
            User assignee = userRepository.findById(dto.getAssignedToId()).orElse(null);
            task.setAssignedTo(assignee);
        }
        
        taskRepository.save(task);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(@RequestParam(required = false) Long projectId, Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();

        if (projectId != null) {
            return ResponseEntity.ok(taskRepository.findByProjectId(projectId));
        }

        if (user.getRole() == Role.ADMIN) {
            return ResponseEntity.ok(taskRepository.findAll());
        } else {
            return ResponseEntity.ok(taskRepository.findByAssignedToId(user.getId()));
        }
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long taskId, @RequestBody Map<String, String> statusMap, Authentication auth) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        User user = userRepository.findById(((UserDetailsImpl) auth.getPrincipal()).getId()).orElseThrow();
        
        if (user.getRole() != Role.ADMIN && (task.getAssignedTo() == null || !task.getAssignedTo().getId().equals(user.getId()))) {
            return ResponseEntity.status(403).body("Cannot update unassigned task");
        }

        task.setStatus(Status.valueOf(statusMap.get("status")));
        taskRepository.save(task);
        return ResponseEntity.ok(task);
    }
}

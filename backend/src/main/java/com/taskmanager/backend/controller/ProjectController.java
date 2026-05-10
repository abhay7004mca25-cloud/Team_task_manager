package com.taskmanager.backend.controller;

import com.taskmanager.backend.dto.ProjectDto;
import com.taskmanager.backend.model.Project;
import com.taskmanager.backend.model.Role;
import com.taskmanager.backend.model.User;
import com.taskmanager.backend.repository.ProjectRepository;
import com.taskmanager.backend.repository.UserRepository;
import com.taskmanager.backend.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired private ProjectRepository projectRepository;
    @Autowired private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectDto dto, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();
        
        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setCreatedBy(user);
        project.getMembers().add(user);
        projectRepository.save(project);
        return ResponseEntity.ok(project);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Project> projects = projectRepository.findByMembersId(userDetails.getId());
        return ResponseEntity.ok(projects);
    }
    
    @PostMapping("/{projectId}/members/{userId}")
    public ResponseEntity<?> addMember(@PathVariable Long projectId, @PathVariable Long userId, Authentication auth) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        User admin = userRepository.findById(((UserDetailsImpl) auth.getPrincipal()).getId()).orElseThrow();
        if(!project.getCreatedBy().getId().equals(admin.getId()) && admin.getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).body("Only admin can add members");
        }
        User userToAdd = userRepository.findById(userId).orElseThrow();
        project.getMembers().add(userToAdd);
        projectRepository.save(project);
        return ResponseEntity.ok("Member added");
    }
    @PutMapping("/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable Long projectId, @RequestBody ProjectDto dto, Authentication auth) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        User admin = userRepository.findById(((UserDetailsImpl) auth.getPrincipal()).getId()).orElseThrow();
        if(!project.getCreatedBy().getId().equals(admin.getId()) && admin.getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).body("Only admin can edit projects");
        }
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        projectRepository.save(project);
        return ResponseEntity.ok(project);
    }
}

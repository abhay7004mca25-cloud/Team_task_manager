package com.taskmanager.backend.dto;
import com.taskmanager.backend.model.Priority;
import com.taskmanager.backend.model.Status;
import java.time.LocalDate;
public class TaskDto {
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private Long projectId;
    private Long assignedToId;
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public Long getAssignedToId() { return assignedToId; }
    public void setAssignedToId(Long assignedToId) { this.assignedToId = assignedToId; }
}

package com.taskmanager.backend.repository;
import com.taskmanager.backend.model.Task;
import com.taskmanager.backend.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);
    List<Task> findByAssignedToId(Long userId);
    
    @Query("SELECT COUNT(t) FROM Task t")
    long countTotalTasks();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.status = :status")
    long countTasksByStatus(@Param("status") Status status);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignedTo.id = :userId")
    long countTasksByUser(@Param("userId") Long userId);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.dueDate < :date AND t.status != 'DONE'")
    long countOverdueTasks(@Param("date") LocalDate date);
}

package com.rob.repositories.repository;

import com.rob.models.Project;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByProjectName(@NotEmpty @Size(max=255) String projectName);

    List<Project> findByProjectNameContainingIgnoreCase(@NotEmpty @Size(max=255) String projectName);

    @Query("SELECT MAX(p.projectId) FROM Project p")
    Long findMaxProjectId();
}
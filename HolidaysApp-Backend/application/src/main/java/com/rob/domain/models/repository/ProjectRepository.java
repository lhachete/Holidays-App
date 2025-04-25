package com.rob.domain.models.repository;

import com.rob.domain.models.entities.Project;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByProjectName(@NotEmpty @Size(max=255) String projectName);
}
package com.rob.application.services;

import com.rob.application.ports.driven.ProjectRepositoryPort;
import com.rob.application.ports.driving.ProjectServicePort;
import com.rob.domain.models.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectUseCase implements ProjectServicePort {

    private final ProjectRepositoryPort projectRepositoryPort;

    @Override
    public List<Project> getProjectsByName(String name) {
        if (name == null || name.isEmpty()) {
            return projectRepositoryPort.findAll();
        }
        return projectRepositoryPort.findByNameContaining(name);
    }
}

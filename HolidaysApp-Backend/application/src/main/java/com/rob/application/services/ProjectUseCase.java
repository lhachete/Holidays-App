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
        return projectRepositoryPort.findByNameContaining(name);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepositoryPort.findAll();
    }
}

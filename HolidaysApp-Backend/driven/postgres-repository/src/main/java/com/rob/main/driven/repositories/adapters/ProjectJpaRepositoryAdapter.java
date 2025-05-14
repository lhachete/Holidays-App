package com.rob.main.driven.repositories.adapters;

import com.rob.application.ports.driven.ProjectRepositoryPort;
import com.rob.domain.models.Project;
import com.rob.main.driven.repositories.ProjectMOJpaRepository;
import com.rob.main.driven.repositories.mappers.ProjectMOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class ProjectJpaRepositoryAdapter implements ProjectRepositoryPort {

    private final ProjectMOJpaRepository projectRepository;
    private final ProjectMOMapper projectMOMapper;

    @Override
    public List<Project> findByNameContaining(String name) {
        return projectRepository.findByNameContaining(name).
                stream().
                map(projectMOMapper::toProject).
                toList();
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll().
                stream().
                map(projectMOMapper::toProject).
                toList();
    }
}

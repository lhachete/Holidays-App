package com.rob.main.driven.repositories.adapters;

import com.rob.application.ports.driven.ProjectRepositoryPort;
import com.rob.domain.models.Project;
import com.rob.main.driven.repositories.ProjectMOJpaRepository;
import com.rob.main.driven.repositories.mappers.ProjectMOMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ProjectJpaRepositoryAdapter implements ProjectRepositoryPort {

    private final ProjectMOJpaRepository projectRepository;
    private final ProjectMOMapper projectMOMapper;

    @Override
    public List<Project> findByNameContaining(String name) {
        log.info("Se van a buscar proyectos que contengan el nombre: {}", name);
        return projectRepository.findByNameContaining(name).
                stream().
                map(projectMOMapper::toProject).
                toList();
    }

    @Override
    public List<Project> findAll() {
    log.info("Se van a obtener todos los proyectos");
        return projectRepository.findAll().
                stream().
                map(projectMOMapper::toProject).
                toList();
    }
}

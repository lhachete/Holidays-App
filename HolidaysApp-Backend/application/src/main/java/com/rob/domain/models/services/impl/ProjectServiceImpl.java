package com.rob.domain.models.services.impl;

import com.rob.domain.models.dtos.ProjectDTO;
import com.rob.domain.models.repository.ProjectRepository;
import com.rob.models.Project;
import com.rob.domain.models.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Optional<List<Project>> findProjectByName(String name) {
        if(name != null) {
            Optional<List<Project>> projects = Optional.of(projectRepository.findByProjectNameContainingIgnoreCase(name));
            if (projects.isPresent())
                return projects;
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Project> findProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent())
            return project;
        return Optional.empty();
    }

    @Override
    public Optional<List<Project>> getAllProjects() {
        Optional<List<Project>> projects = Optional.of(projectRepository.findAll());
        if (projects.isPresent())
            return projects;
        return Optional.empty();
    }

    @Override
    public Optional<Project> saveProject(ProjectDTO project) throws Exception {
        if (!projectRepository.existsByProjectName((project.getProjectName()))) {
            //Project newProject = new Project(project);
            //newProject.setProjectId(projectRepository.findMaxProjectId() + 1);
            //newProject.setName(project.getName());
            //newProject.setDescription(project.getDescription());
            //return Optional.of(projectRepository.save(newProject));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Project> deleteProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            projectRepository.deleteById(id);
            return project;
        }
        return Optional.empty();
    }
}

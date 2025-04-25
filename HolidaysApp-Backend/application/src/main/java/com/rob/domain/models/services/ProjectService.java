package com.rob.domain.models.services;

import com.rob.domain.models.dtos.ProjectDTO;
import com.rob.domain.models.entities.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {


     Optional<Project> findProjectById(Long id);
     Optional<List<Project>> getAllProjects();
     Optional<Project> saveProject(ProjectDTO project) throws Exception;
     //Optional<Project> updateProject(ProjectUpdateDTO project) throws Exception;
     Optional<Project> deleteProjectById(Long id);
}

package com.rob.application.ports.driving;

import com.rob.domain.models.Project;

import java.util.List;

public interface ProjectServicePort {
    List<Project> getProjectsByName(String name);

    List<Project> getAllProjects();
}

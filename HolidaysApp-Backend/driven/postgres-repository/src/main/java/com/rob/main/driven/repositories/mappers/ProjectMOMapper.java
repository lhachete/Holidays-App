package com.rob.main.driven.repositories.mappers;

import com.rob.domain.models.Project;
import com.rob.domain.models.Role;
import com.rob.main.driven.repositories.models.ProjectMO;
import com.rob.main.driven.repositories.models.RoleMO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface ProjectMOMapper {

    default ProjectMO toProjectMO(Project project) {
        return ProjectMO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .startDate(project.getStartDate())
                .expectedEndDate(project.getExpectedEndDate())
                .realEndDate(project.getRealEndDate())
                .projectState(project.getProjectState())
                .projectType(project.getProjectType())
                .build();
    }

    default Project toProject(ProjectMO projectMO) {
        return Project.builder()
                .id(projectMO.getId())
                .name(projectMO.getName())
                .description(projectMO.getDescription())
                .startDate(projectMO.getStartDate())
                .expectedEndDate(projectMO.getExpectedEndDate())
                .realEndDate(projectMO.getRealEndDate())
                .projectState(projectMO.getProjectState())
                .projectType(projectMO.getProjectType())
                .build();
    }
}

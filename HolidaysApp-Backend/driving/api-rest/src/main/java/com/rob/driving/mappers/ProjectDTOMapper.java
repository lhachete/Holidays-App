package com.rob.driving.mappers;

import com.rob.domain.models.Project;
import com.rob.domain.models.Role;
import com.rob.driving.dtos.ProjectDTO;
import com.rob.driving.dtos.RoleDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ProjectDTOMapper {

    default ProjectDTO toProjectDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setStartDate(project.getStartDate());
        projectDTO.setExpectedEndDate(project.getExpectedEndDate());
        projectDTO.setRealEndDate(project.getRealEndDate());
        projectDTO.setProjectState(project.getProjectState());
        projectDTO.setProjectType(project.getProjectType());
        return projectDTO;
    }

    // funcion que utiliza el patron builder para crear un objeto OrganizationDTO
    default Project toProject(ProjectDTO projectDTO ) {
        return Project.builder()
                .id(projectDTO.getId())
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .startDate(projectDTO.getStartDate())
                .expectedEndDate(projectDTO.getExpectedEndDate())
                .realEndDate(projectDTO.getRealEndDate())
                .projectState(projectDTO.getProjectState())
                .projectType(projectDTO.getProjectType())
                .build();
    }
}

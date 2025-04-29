package com.rob.domain.models.dtos;

import com.rob.models.Project;
import com.rob.models.enums.ProjectState;
import com.rob.models.enums.ProjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private String projectName;
    private String projectDescription;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate realEndDate;
    private ProjectType projectType;
    private ProjectState projectState;

    public ProjectDTO(Project project) {
        this.projectName = project.getProjectName();
        this.projectDescription = project.getProjectDescription();
        this.startDate = project.getStartDate();
        this.expectedEndDate = project.getExpectedEndDate();
        this.realEndDate = project.getRealEndDate();
        this.projectType = project.getProjectType();
        this.projectState = project.getProjectState();
    }
}

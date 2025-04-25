package com.rob.domain.models.dtos;

import com.rob.domain.models.enums.ProjectState;
import com.rob.domain.models.enums.ProjectType;
import com.rob.domain.models.enums.VacationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private String projectName;
    private String projectDescription;
    private Date startDate;
    private Date expectedEndDate;
    private Date realEndDate;
    private ProjectType projectType;
    private ProjectState projectState;
    private VacationType vacationType;
}

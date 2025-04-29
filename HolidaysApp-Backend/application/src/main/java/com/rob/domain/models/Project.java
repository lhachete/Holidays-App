package com.rob.domain.models;

import com.rob.domain.models.dtos.ProjectDTO;
import com.rob.domain.models.enums.ProjectState;
import com.rob.domain.models.enums.ProjectType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    private Long projectId;
    private String projectName;
    private String projectDescription;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate realEndDate;
    private ProjectType projectType;
    private ProjectState projectState;
}

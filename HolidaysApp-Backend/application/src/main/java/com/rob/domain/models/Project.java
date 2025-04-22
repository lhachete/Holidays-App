package com.rob.domain.models;

import com.rob.domain.models.enums.ProjectState;
import com.rob.domain.models.enums.ProjectType;
import com.rob.domain.models.enums.VacationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String projectName;
    private String projectDescription;
    private Date startDate;
    private Date expectedEndDate;
    private Date realEndDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "project_type", nullable = false)
    private ProjectType projectType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "project_state", nullable = false)
    private ProjectState projectState;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "vacation_type", nullable = false)
    private VacationType vacationType;
}

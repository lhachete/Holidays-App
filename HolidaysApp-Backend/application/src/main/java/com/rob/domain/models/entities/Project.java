package com.rob.domain.models.entities;

import com.rob.domain.models.enums.ProjectState;
import com.rob.domain.models.enums.ProjectType;
import com.rob.domain.models.enums.VacationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank
    @Min(value = 0)
    @Max(value = 99999)
    private Long projectId;

    @NotEmpty
    @Size(max=255)
    @Column(name = "name", nullable = false)
    private String projectName;

    @NotEmpty
    @Size(min = 3,max=255)
    @Column(name = "description", nullable = true)
    private String projectDescription;

    @NotNull
    @FutureOrPresent
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @NotNull
    @FutureOrPresent
    @Column(name = "expected_end_date", nullable = true)
    private Date expectedEndDate;

    @Column(name = "real_end_date", nullable = true)
    private Date realEndDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "project_type", nullable = false)
    @NotNull
    private ProjectType projectType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "project_state", nullable = false)
    private ProjectState projectState;
}

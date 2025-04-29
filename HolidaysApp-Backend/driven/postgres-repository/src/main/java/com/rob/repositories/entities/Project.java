package com.rob.repositories.entities;

import com.rob.models.enums.ProjectState;
import com.rob.models.enums.ProjectType;
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
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent
    @Column(name = "expected_end_date", nullable = true)
    private LocalDate expectedEndDate;

    @Column(name = "real_end_date", nullable = true)
    private LocalDate realEndDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "project_type", name = "project_type", nullable = false)
    @NotNull
    private ProjectType projectType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "project_state", name = "project_state", nullable = false)
    private ProjectState projectState;
}

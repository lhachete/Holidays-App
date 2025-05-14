package com.rob.main.driven.repositories.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "projects")
public class ProjectMO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    //@NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    //@NotNull
    @Column(name = "description", nullable = false)
    private String description;

    //@NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "expected_end_date")
    private LocalDate expectedEndDate;

    @Column(name = "real_end_date")
    private LocalDate realEndDate;

    @Size(max = 50)
    //@NotNull
    @Column(name = "project_state", nullable = false, length = 50)
    private String projectState;

    @Size(max = 50)
    //@NotNull
    @Column(name = "project_type", nullable = false, length = 50)
    private String projectType;

}
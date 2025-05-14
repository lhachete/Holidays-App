package com.rob.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    private Integer id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate expectedEndDate;
    private LocalDate realEndDate;
    private String projectState;
    private String projectType;
}

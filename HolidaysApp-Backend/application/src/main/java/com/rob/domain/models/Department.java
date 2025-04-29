package com.rob.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    private Long departmentId;
    private Organization organization;
    private String name;
    private String businessUnit;
    private String division;
    private String costCenter;
    private String location;
    private String timeZone;
}

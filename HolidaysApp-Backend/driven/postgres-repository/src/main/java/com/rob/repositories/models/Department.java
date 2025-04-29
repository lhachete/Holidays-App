package com.rob.models;


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
@Entity
@Table(name = "departments")
public class Department {

    @NotNull
    @Min(value = 0)
    @Max(value = 99999)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @NotEmpty
    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "business_unit",nullable = true)
    private String businessUnit;

    @Column(name = "division",nullable = true)
    private String division;

    @Column(name = "cost_center",nullable = true)
    private String costCenter;

    @NotNull
    @Column(name = "location",nullable = false)
    private String location;

    @Column(name = "time_zone",nullable = true)
    private String timeZone;
}

package com.rob.domain.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "business_unit",nullable = true)
    private String businessUnit;

    @Column(name = "division",nullable = true)
    private String division;

    @Column(name = "cost_center",nullable = true)
    private String costCenter;

    @Column(name = "location",nullable = false)
    private String location;

    @Column(name = "time_zone",nullable = true)
    private String timeZone;
}

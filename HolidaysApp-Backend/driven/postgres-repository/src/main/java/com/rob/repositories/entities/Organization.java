package com.rob.repositories.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 0)
    @Max(value = 99999)
    @Column(name = "org_id", nullable = false)
    private Long orgId;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    public Organization(String name) {
        this.name = name;
    }
}

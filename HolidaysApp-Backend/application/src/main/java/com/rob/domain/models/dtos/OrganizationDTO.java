package com.rob.domain.models.dtos;

import com.rob.repositories.entities.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {

    private String name;

    public OrganizationDTO(Organization organization) {
        this.name = organization.getName();
    }
}

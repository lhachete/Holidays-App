package com.rob.domain.models.dtos;

import com.rob.models.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationUpdateDTO extends OrganizationDTO {

    private String newOrganizationName;

//    public OrganizationUpdateDTO(Organization organization) {
//        organization.setName(this.newOrganizationName);
//    }
}

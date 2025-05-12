package com.rob.driving.mappers;

import com.rob.domain.models.Organization;
import com.rob.driving.dtos.OrganizationDTO;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;

@Component
@Mapper(componentModel = "spring")
public interface OrganizationDTOMapper {

    default OrganizationDTO toOrganizationDTO(Organization organization) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(organization.getId());
        organizationDTO.setName(organization.getName());
        return organizationDTO;
    }

    default Organization toOrganization(OrganizationDTO organizationDTO) {
        return Organization.builder()
                .id(organizationDTO.getId())
                .name(organizationDTO.getName())
                .build();
    }
}

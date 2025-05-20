package com.rob.driving.mappers;

import com.rob.domain.models.Organization;
import com.rob.driving.dtos.OrganizationCreateDTO;
import com.rob.driving.dtos.OrganizationDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationDTOMapper {

    default OrganizationDTO toOrganizationDTO(Organization organization) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(organization.getId());
        organizationDTO.setName(organization.getName());
        return organizationDTO;
    }

    // funcion que utiliza el patron builder para crear un objeto OrganizationDTO
    default Organization toOrganization(OrganizationDTO organizationDTO) {
        return Organization.builder()
                .id(organizationDTO.getId())
                .name(organizationDTO.getName())
                .build();
    }

    default Organization toOrganization(@Valid OrganizationCreateDTO organizationCreateDTO) {
        return Organization.builder()
                .name(organizationCreateDTO.getName())
                .build();
    }
}

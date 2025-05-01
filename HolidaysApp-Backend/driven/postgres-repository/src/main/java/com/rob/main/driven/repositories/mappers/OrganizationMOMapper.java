package com.rob.main.driven.repositories.mappers;

import com.rob.domain.models.Organization;
import com.rob.main.driven.repositories.models.OrganizationMO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMOMapper {

    default OrganizationMO toOrganizationEntity(Organization organization) {
        return OrganizationMO.builder()
                .id(organization.getId())
                .name(organization.getName())
                .build();
    }

    default Organization toOrganization(OrganizationMO organizationMO) {
        return Organization.builder()
                .id(organizationMO.getId())
                .name(organizationMO.getName())
                .build();
    }
}

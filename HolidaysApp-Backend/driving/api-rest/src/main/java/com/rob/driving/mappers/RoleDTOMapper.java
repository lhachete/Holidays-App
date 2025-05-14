package com.rob.driving.mappers;

import com.rob.domain.models.Organization;
import com.rob.domain.models.Role;
import com.rob.driving.dtos.OrganizationDTO;
import com.rob.driving.dtos.RoleDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface RoleDTOMapper {

    default RoleDTO toRoleDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    // funcion que utiliza el patron builder para crear un objeto OrganizationDTO
    default Role toRole(RoleDTO roleDTO) {
        return Role.builder()
                .id(roleDTO.getId())
                .name(roleDTO.getName())
                .build();
    }
}

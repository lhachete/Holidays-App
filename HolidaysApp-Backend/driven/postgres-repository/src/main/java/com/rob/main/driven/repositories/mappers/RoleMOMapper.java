package com.rob.main.driven.repositories.mappers;

import com.rob.domain.models.Role;
import com.rob.main.driven.repositories.models.RoleMO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface RoleMOMapper {

    default RoleMO toRoleMO(Role role) {
        return RoleMO.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    default Role toRole(RoleMO roleMO) {
        return Role.builder()
                .id(roleMO.getId())
                .name(roleMO.getName())
                .build();
    }
}

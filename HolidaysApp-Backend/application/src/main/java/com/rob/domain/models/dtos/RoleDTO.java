package com.rob.domain.models.dtos;

import com.rob.repositories.entities.Role;
import com.rob.models.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private Roles roleType;

    public RoleDTO(Role role) {
        this.roleType = role.getRole();
    }
}

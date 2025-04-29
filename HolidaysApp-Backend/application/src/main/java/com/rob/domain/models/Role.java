package com.rob.domain.models;

import com.rob.domain.models.dtos.RoleDTO;
import com.rob.domain.models.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    private Long roleId;
    private Roles role;
}

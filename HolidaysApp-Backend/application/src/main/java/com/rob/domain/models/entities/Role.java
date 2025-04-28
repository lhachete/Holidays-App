package com.rob.domain.models.entities;

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
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 0)
    @Max(value = 99999)
    @Column(name = "rol_id")
    private Long roleId;

    @Column(columnDefinition = "role_type",name = "rol", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Roles role;

    public Role(RoleDTO roleDTO) {
        this.role = roleDTO.getRoleType();
    }
}

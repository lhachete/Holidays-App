package com.rob.domain.models.entities;

import com.rob.domain.models.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank
    @NotNull
    @Min(value = 0)
    @Max(value = 99999)
    @Column(name = "rol_id")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "role_type",name = "rol", nullable = false)
    @NotNull
    @NotEmpty
    private Roles role;

}

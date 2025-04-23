package com.rob.domain.models.entities;

import com.rob.domain.models.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
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
    @NotNull
    @Min(value=0)
    @PositiveOrZero
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "role_type", nullable = false)
    @NotNull
    @NotEmpty
    private Roles role;

}

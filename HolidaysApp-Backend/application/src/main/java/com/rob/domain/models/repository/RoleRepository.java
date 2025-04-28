package com.rob.domain.models.repository;

import com.rob.domain.models.dtos.OrganizationDTO;
import com.rob.domain.models.dtos.RoleDTO;
import com.rob.domain.models.entities.Organization;
import com.rob.domain.models.entities.Role;
import com.rob.domain.models.enums.Roles;
import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends org.springframework.data.jpa.repository.JpaRepository<Role, Long> {

    Optional<Role> save(RoleDTO roleDTO);

    List<Role> findAll();

    boolean existsByRole(@NotNull @NotEmpty Roles role);

    @Query(value = "SELECT * FROM roles WHERE rol = CAST(:role AS role_type)", nativeQuery = true)
    Optional<Role> findByRole(@Param("role") String role);
}
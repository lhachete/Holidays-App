package com.rob.domain.models.repository;

import com.rob.domain.models.dtos.RoleDTO;
import com.rob.domain.models.enums.Roles;
import com.rob.models.Role;
import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> save(RoleDTO roleDTO);

    List<Role> findAll();

    boolean existsByRole(@NotNull @NotEmpty Roles role);

    @Query(value = "SELECT * FROM roles WHERE rol = CAST(:role AS role_type)", nativeQuery = true)
    Optional<Role> findByRole(@Param("role") String role);
}
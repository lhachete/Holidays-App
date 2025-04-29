package com.rob.repositories.repository;

import com.rob.repositories.entities.Role;
import com.rob.models.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role save(Role role);

    List<Role> findAll();

    boolean existsByRole(Roles role);

    @Query(value = "SELECT * FROM roles WHERE rol = CAST(:role AS role_type)", nativeQuery = true)
    Optional<Role> findByRole(@Param("role") String role);
}
package com.rob.domain.models.repository;

import com.rob.domain.models.entities.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends org.springframework.data.jpa.repository.JpaRepository<Role, Long> {
}
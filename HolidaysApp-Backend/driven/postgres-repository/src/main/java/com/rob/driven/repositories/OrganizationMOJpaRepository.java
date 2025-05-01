package com.rob.driven.repositories;

import com.rob.driven.repositories.models.OrganizationMO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationMOJpaRepository extends JpaRepository<OrganizationMO, Integer> {
}
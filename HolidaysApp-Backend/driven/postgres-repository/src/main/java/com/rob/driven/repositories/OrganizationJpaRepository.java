package com.rob.driven.repositories;

import com.rob.driven.repositories.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationJpaRepository extends JpaRepository<Organization, Integer> {
}
package com.rob.api_rest.main.driven.repositories;

import com.rob.api_rest.main.driven.repositories.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationJpaRepository extends JpaRepository<Organization, Integer> {
}
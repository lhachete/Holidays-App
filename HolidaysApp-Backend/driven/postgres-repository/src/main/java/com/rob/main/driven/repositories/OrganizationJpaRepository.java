package com.rob.main.driven.repositories;

import com.rob.main.driven.repositories.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationJpaRepository extends JpaRepository<Organization, Integer> {
}
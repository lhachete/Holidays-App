package com.rob.api_rest.main.application.ports.driven;

import com.rob.api_rest.main.driven.repositories.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepositoryPort extends JpaRepository<Organization, Integer> {
}
package com.rob.application.ports.driven;

import com.rob.driven.repositories.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepositoryPort extends JpaRepository<Organization, Integer> {
}
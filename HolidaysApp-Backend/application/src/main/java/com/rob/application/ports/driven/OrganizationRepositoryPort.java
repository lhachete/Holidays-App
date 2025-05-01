package com.rob.application.ports.driven;

import com.rob.main.driven.repositories.models.OrganizationMO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepositoryPort extends JpaRepository<OrganizationMO, Integer> {
}
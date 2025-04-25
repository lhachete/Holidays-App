package com.rob.domain.models.repository;

import com.rob.domain.models.dtos.OrganizationDTO;
import com.rob.domain.models.entities.Organization;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByName(String name);
    Optional<Organization> save(OrganizationDTO organization);
    List<Organization> findAll();
    List<Organization> findByNameContainingIgnoreCase(@NotEmpty String name);
}
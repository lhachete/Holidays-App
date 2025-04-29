package com.rob.repositories.repository;

import com.rob.repositories.entities.Organization;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByName(String name);
    Organization save(Organization organization);
    List<Organization> findAll();
    List<Organization> findByNameContainingIgnoreCase(@NotEmpty String name);
}
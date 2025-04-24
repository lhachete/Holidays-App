package com.rob.domain.models.repository;

import com.rob.domain.models.entities.Organization;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    List<Organization> findByName(String name);
}
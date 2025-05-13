package com.rob.application.ports.driving;

import com.rob.domain.models.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationServicePort {
    List<Organization> getAllOrganizations();

    Optional<Organization> getOrganizationById(Integer id);

    List<Organization> getOrganizationsByName(String name);
}

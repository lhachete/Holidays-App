package com.rob.application.ports.driving;

import com.rob.domain.models.Organization;

import java.util.List;

public interface OrganizationServicePort {
    List<Organization> getAllOrganizations();
}

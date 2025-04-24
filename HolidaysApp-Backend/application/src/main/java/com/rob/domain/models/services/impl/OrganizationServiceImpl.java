package com.rob.domain.models.services.impl;

import com.rob.domain.models.entities.Organization;
import com.rob.domain.models.services.OrganizationService;

import java.util.List;
import java.util.Optional;

public class OrganizationServiceImpl implements OrganizationService {
    @Override
    public Optional<Organization> saveOrganization(Organization organization) throws Exception {
        Optional<Organization> existingOrganization = findOrganizationById(organization.getId());
    }

    @Override
    public Optional<Organization> findOrganizationById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Organization>> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Organization> updateOrganization(Organization organization) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Organization> deleteOrganizationById(Long id) {
        return Optional.empty();
    }
}

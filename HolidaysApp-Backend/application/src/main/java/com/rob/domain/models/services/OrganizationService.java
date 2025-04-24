package com.rob.domain.models.services;

import com.rob.domain.models.entities.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {
    public Optional<List<Organization>> getAllOrganizations();
    public Optional<Organization> saveOrganization(Organization organization) throws Exception;
    public Optional<Organization> findOrganizationById(Long id);
    public Optional<List<Organization>> findByName(String name);
    public Optional<Organization> updateOrganization(Organization organization) throws Exception;
    public Optional<Organization> deleteOrganizationById(Long id);
}

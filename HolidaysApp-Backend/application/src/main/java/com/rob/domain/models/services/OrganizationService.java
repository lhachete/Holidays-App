package com.rob.domain.models.services;

import com.rob.domain.models.dtos.OrganizationDTO;
import com.rob.domain.models.dtos.OrganizationUpdateDTO;
import com.rob.domain.models.entities.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {
    public Optional<List<Organization>> getAllOrganizations();
    public Optional<Organization> saveOrganization(OrganizationDTO organization) throws Exception;
    public Optional<Organization> findOrganizationById(Long id);
    public Optional<Organization> findOrganizationEntityById(Long id);
    public Optional<Organization> findByName(String name);
    public Optional<Organization> updateOrganization(OrganizationUpdateDTO organization) throws Exception;
    public Optional<Organization> deleteOrganizationById(Long id);
    public Optional<List<Organization>> findByContainingName(String name);
}

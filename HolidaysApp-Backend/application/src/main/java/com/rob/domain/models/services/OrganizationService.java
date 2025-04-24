package com.rob.domain.models.services;

import com.rob.domain.models.dtos.OrganizationDTO;
import com.rob.domain.models.entities.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {
    public Optional<List<OrganizationDTO>> getAllOrganizations();
    public Optional<OrganizationDTO> saveOrganization(OrganizationDTO organization) throws Exception;
    public Optional<OrganizationDTO> findOrganizationById(Long id);
    public Optional<Organization> findOrganizationEntityById(Long id);
    public Optional<OrganizationDTO> findByName(String name);
    public Optional<OrganizationDTO> updateOrganization(Organization organization) throws Exception;
    public Optional<OrganizationDTO> deleteOrganizationById(Long id);
}

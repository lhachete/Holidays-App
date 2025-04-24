package com.rob.domain.models.services.impl;

import com.rob.domain.models.dtos.OrganizationDTO;
import com.rob.domain.models.entities.Organization;
import com.rob.domain.models.repository.OrganizationRepository;
import com.rob.domain.models.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public Optional<List<OrganizationDTO>> getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        List<OrganizationDTO> organizationDTOs = new ArrayList<>();
        for (Organization organization : organizations) {
            organizationDTOs.add(new OrganizationDTO(organization));
        }
        return Optional.of(organizationDTOs);
    }

    @Override
    public Optional<OrganizationDTO> saveOrganization(OrganizationDTO organization) throws Exception {
        Optional<OrganizationDTO> existingOrganization = findByName(organization.getName());
        if(existingOrganization.isEmpty()) {
            Organization savedOrg = organizationRepository.save(new Organization(organization.getName()));
            return Optional.of(new OrganizationDTO(savedOrg));
        }
        return Optional.empty();
    }

    @Override
    public Optional<OrganizationDTO> findOrganizationById(Long id) {
        /* MAKES A RECURSIVE LOOP
        Optional<Organization> existingOrganization = findOrganizationById(id);
        return existingOrganization;
        */
        Optional<Organization> organization = organizationRepository.findById(id);
        if(organization.isPresent())
            return Optional.of(new OrganizationDTO(organization.get()));
        return Optional.empty();
    }

    @Override
    public Optional<Organization> findOrganizationEntityById(Long id) {
        Optional<Organization> organization = organizationRepository.findById(id);
        if(organization.isPresent())
            return Optional.of(organization.get());
        return Optional.empty();
    }

    @Override
    public Optional<OrganizationDTO> findByName(String name) {
        Organization organization = organizationRepository.findByName(name);
        if(organization != null)
            return Optional.of(new OrganizationDTO(organization));
        return Optional.empty();
    }

    @Override
    public Optional<OrganizationDTO> updateOrganization(Organization organization) throws Exception {
        Optional<OrganizationDTO> existingOrganization = findOrganizationById(organization.getOrgId());
        if(existingOrganization.isPresent()) {
            organizationRepository.save(organization);
            return Optional.of(new OrganizationDTO(organization));
        }
        return Optional.empty();
    }

    @Override
    public Optional<OrganizationDTO> deleteOrganizationById(Long id) {
        Optional<OrganizationDTO> existingOrganization = findOrganizationById(id);
        if(existingOrganization.isPresent()) {
            OrganizationDTO copy = new OrganizationDTO(existingOrganization.get().getName());
            organizationRepository.deleteById(id);
            return Optional.ofNullable(copy);
        }
        return Optional.empty();
    }
}

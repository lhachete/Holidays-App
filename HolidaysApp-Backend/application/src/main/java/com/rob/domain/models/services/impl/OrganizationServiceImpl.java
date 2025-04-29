package com.rob.domain.models.services.impl;

import com.rob.domain.models.dtos.OrganizationDTO;
import com.rob.domain.models.dtos.OrganizationUpdateDTO;
import com.rob.domain.models.repository.OrganizationRepository;
import com.rob.models.Organization;
import com.rob.domain.models.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public Optional<List<Organization>> getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        if(!organizations.isEmpty())
            return Optional.of(organizations);
        return Optional.empty();
    }

    @Override
    public Optional<Organization> saveOrganization(OrganizationDTO organization) throws Exception {
        Optional<Organization> existingOrganization = findByName(organization.getName());
        if(existingOrganization.isEmpty()) {
            Organization savedOrg = organizationRepository.save(new Organization(organization.getName()));
            return Optional.of(savedOrg);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Organization> findOrganizationById(Long id) {
        /* MAKES A RECURSIVE LOOP
        Optional<Organization> existingOrganization = findOrganizationById(id);
        return existingOrganization;
        */
        Optional<Organization> organization = organizationRepository.findById(id);
        if(organization.isPresent())
            return Optional.of(organization.get());
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
    public Optional<Organization> findByName(String name) {
        Optional<Organization> organization = organizationRepository.findByName(name);
        if(organization.isPresent())
            return organization;
        return Optional.empty();
    }

    @Override
    public Optional<Organization> updateOrganization(OrganizationUpdateDTO organization) throws Exception {
        Optional<Organization> existingOrganization = findByName(organization.getName());
        if(existingOrganization.isPresent()) {
            existingOrganization.get().setName(organization.getNewOrganizationName());
            Organization updatedOrg = organizationRepository.save(existingOrganization.get());
            return Optional.of(updatedOrg);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Organization> deleteOrganizationById(Long id) {
        Optional<Organization> existingOrganization = findOrganizationById(id);
        if(existingOrganization.isPresent()) {
            Organization copy = new Organization(existingOrganization.get().getOrgId(),existingOrganization.get().getName());
            organizationRepository.deleteById(id);
            return Optional.ofNullable(copy);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Organization>> findByContainingName(String name) {
        List<Organization> organization = organizationRepository.findByNameContainingIgnoreCase(name);
        if(!organization.isEmpty()) {
            return Optional.of(organization);
        }
        return Optional.empty();
    }
}

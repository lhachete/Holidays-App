package com.rob.domain.models.services.impl;

import com.rob.domain.models.entities.Organization;
import com.rob.domain.models.repository.OrganizationRepository;
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
        return Optional.of(organizationRepository.findAll());
    }

    @Override
    public Optional<Organization> saveOrganization(Organization organization) throws Exception {
        Optional<Organization> existingOrganization = findOrganizationById(organization.getOrgId());
        if(existingOrganization.isEmpty()) {
            organizationRepository.save(organization);
            return Optional.of(organization);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Organization> findOrganizationById(Long id) {
        Optional<Organization> existingOrganization = findOrganizationById(id);
        return existingOrganization;
    }

    @Override
    public Optional<List<Organization>> findByName(String name) {
        List<Organization> organizations = organizationRepository.findByName(name);
        return Optional.of(organizations);
    }

    @Override
    public Optional<Organization> updateOrganization(Organization organization) throws Exception {
        Optional<Organization> existingOrganization = findOrganizationById(organization.getOrgId());
        if(existingOrganization.isPresent()) {
            organizationRepository.save(organization);
            return Optional.of(organization);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Organization> deleteOrganizationById(Long id) {
        Optional<Organization> existingOrganization = findOrganizationById(id);
        if(existingOrganization.isPresent()) {
            organizationRepository.deleteById(id);
            return existingOrganization;
        }
        return Optional.empty();
    }
}

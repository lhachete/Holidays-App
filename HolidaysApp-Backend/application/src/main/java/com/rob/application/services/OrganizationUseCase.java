package com.rob.application.services;

import com.rob.application.ports.driven.OrganizationRepositoryPort;
import com.rob.application.ports.driving.OrganizationServicePort;
import com.rob.domain.models.Organization;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OrganizationUseCase implements OrganizationServicePort {

    OrganizationRepositoryPort organizationJpaRepositoryPort;

    public List<Organization> getAllOrganizations() {
        return organizationJpaRepositoryPort.findAll();
    }

    @Override
    public Optional<Organization> getOrganizationById(Integer id) {
        return organizationJpaRepositoryPort.findById(id);
    }

    @Override
    public List<Organization> getOrganizationsByName(String name) {
        return organizationJpaRepositoryPort.findByNameContaining(name);
    }

    @Override
    public Organization createOrganization(Organization organization) {
        return organizationJpaRepositoryPort.save(organization);
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        return organizationJpaRepositoryPort.update(organization);
    }
}

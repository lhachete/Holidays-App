package com.rob.application.services;

import com.rob.application.ports.driven.OrganizationRepositoryPort;
import com.rob.application.ports.driving.OrganizationServicePort;
import com.rob.domain.models.Organization;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrganizationUseCase implements OrganizationServicePort {

    OrganizationRepositoryPort organizationJpaRepositoryPort;

    public List<Organization> getAllOrganizations() {
        return organizationJpaRepositoryPort.findAll();
    }
}

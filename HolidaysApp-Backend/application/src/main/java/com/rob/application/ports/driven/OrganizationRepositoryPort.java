package com.rob.application.ports.driven;

import com.rob.domain.models.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepositoryPort {
    List<Organization> findAll();

    Organization findById(Integer id);

    List<Organization> findByNameContaining(String name);

    Organization save(Organization organization);

    Organization update(Organization organization);

    Organization deleteById(Integer id);
}
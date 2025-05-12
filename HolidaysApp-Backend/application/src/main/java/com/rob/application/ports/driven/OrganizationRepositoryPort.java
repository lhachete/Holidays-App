package com.rob.application.ports.driven;

import com.rob.domain.models.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepositoryPort {
    List<Organization> findAll();
    Optional<Organization> findById(Integer id);
}
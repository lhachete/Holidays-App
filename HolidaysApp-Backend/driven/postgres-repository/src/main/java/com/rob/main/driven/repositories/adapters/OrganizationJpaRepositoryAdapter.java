package com.rob.main.driven.repositories.adapters;

import com.rob.application.ports.driven.OrganizationRepositoryPort;
import com.rob.domain.models.Organization;
import com.rob.main.driven.repositories.OrganizationMOJpaRepository;
import com.rob.main.driven.repositories.mappers.OrganizationMOMapper;
import com.rob.main.driven.repositories.models.OrganizationMO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
@RequiredArgsConstructor
public class OrganizationJpaRepositoryAdapter implements OrganizationRepositoryPort {


    private final OrganizationMOJpaRepository organizationMOJpaRepository;
    private final OrganizationMOMapper organizationMOMapper;


    public List<Organization> findAll() {
        return organizationMOJpaRepository.findAll()
                .stream()
                .map(organizationMO -> {
                    return organizationMOMapper.toOrganization(organizationMO);
                })
                .toList();
    }

    @Override
    public Optional<Organization> findById(Integer id) {
        return Optional.ofNullable(organizationMOMapper.toOrganization(organizationMOJpaRepository.findById(id).get()));
    }

    @Override
    public List<Organization> findByNameContaining(String name) {
        return organizationMOJpaRepository.findByNameContaining(name)
                .stream()
                .map(organizationMO -> {
                    return organizationMOMapper.toOrganization(organizationMO);
                })
                .toList();
    }

    @Override
    public Organization save(Organization organization) {
        OrganizationMO organizationMO = organizationMOMapper.toOrganizationEntity(organization);
        return organizationMOMapper.toOrganization(organizationMOJpaRepository.save(organizationMO));
    }

    @Override
    public Organization update(Organization organization) {
        OrganizationMO organizationMO = organizationMOMapper.toOrganizationEntity(organization);
        return organizationMOMapper.toOrganization(organizationMOJpaRepository.save(organizationMO));
    }
}

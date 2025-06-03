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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@Repository
@RequiredArgsConstructor
public class OrganizationJpaRepositoryAdapter implements OrganizationRepositoryPort {

    private final OrganizationMOJpaRepository organizationMOJpaRepository;
    private final OrganizationMOMapper organizationMOMapper;

    public List<Organization> findAll() {
        log.info("Se van a obtener todas las organizaciones");
        return organizationMOJpaRepository.findAll()
                .stream()
                .map(organizationMO -> {
                    return organizationMOMapper.toOrganization(organizationMO);
                })
                .toList();
    }

    @Override
    public Organization findById(Integer id) {
        log.info("Se va a buscar la organización con ID: {}", id);
        return organizationMOMapper.toOrganization(organizationMOJpaRepository.findById(id).get());
    }

    @Override
    public List<Organization> findByNameContaining(String name) {
        log.info("Se van a buscar organizaciones que contengan el nombre: {}", name);
        return organizationMOJpaRepository.findByNameContaining(name)
                .stream()
                .map(organizationMO -> {
                    return organizationMOMapper.toOrganization(organizationMO);
                })
                .toList();
    }

    @Override
    public Organization save(Organization organization) {
        log.info("Se va a guardar la organización: {}", organization);
        OrganizationMO organizationMO = organizationMOMapper.toOrganizationEntity(organization);
        return organizationMOMapper.toOrganization(organizationMOJpaRepository.save(organizationMO));
    }

    @Override
    public Organization update(Organization organization) {
        log.info("Se va a actualizar la organización: {}", organization);
        OrganizationMO organizationMO = organizationMOMapper.toOrganizationEntity(organization);
        return organizationMOMapper.toOrganization(organizationMOJpaRepository.save(organizationMO));
    }

    @Override
    public Organization deleteById(Integer id) {
        log.info("Se va a eliminar la organización con ID: {}", id);
        OrganizationMO organizationMO = organizationMOJpaRepository.findById(id).get();
        organizationMOJpaRepository.deleteById(id);
        return organizationMOMapper.toOrganization(organizationMO);
    }
}

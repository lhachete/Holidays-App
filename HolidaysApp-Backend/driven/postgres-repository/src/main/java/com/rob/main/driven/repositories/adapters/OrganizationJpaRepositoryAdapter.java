package com.rob.main.driven.repositories.adapters;

import com.rob.application.ports.driven.OrganizationRepositoryPort;
import com.rob.domain.models.Organization;
import com.rob.main.driven.repositories.OrganizationMOJpaRepository;
import com.rob.main.driven.repositories.mappers.OrganizationMOMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@AllArgsConstructor
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
}

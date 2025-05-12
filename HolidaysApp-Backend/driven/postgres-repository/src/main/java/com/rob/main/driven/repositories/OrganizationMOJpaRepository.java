package com.rob.main.driven.repositories;

import com.rob.main.driven.repositories.models.OrganizationMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationMOJpaRepository extends JpaRepository<OrganizationMO, Integer> {
    Optional<OrganizationMO> findById(Integer id);
}
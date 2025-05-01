package com.rob.main.driven.repositories.adapters;

import com.rob.main.driven.repositories.OrganizationMOJpaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrganizationJpaRepositoryAdapter {

    OrganizationMOJpaRepository organizationMOJpaRepository;
}

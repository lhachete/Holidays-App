package com.rob.application.services;

import com.rob.application.ports.driven.OrganizationRepositoryPort;
import com.rob.application.ports.driving.OrganizationServicePort;

public class OrganizationUseCase implements OrganizationServicePort {

    OrganizationRepositoryPort organizationJpaRepositoryPort;
}

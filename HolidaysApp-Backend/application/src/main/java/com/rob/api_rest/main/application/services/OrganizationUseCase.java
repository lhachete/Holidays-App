package com.rob.api_rest.main.application.services;

import com.rob.api_rest.main.application.ports.driven.OrganizationRepositoryPort;
import com.rob.api_rest.main.application.ports.driving.OrganizationServicePort;

public class OrganizationUseCase implements OrganizationServicePort {

    OrganizationRepositoryPort organizationJpaRepositoryPort;
}

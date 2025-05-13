package com.rob.application.services;

import com.rob.application.ports.driven.OrganizationRepositoryPort;
import com.rob.application.ports.driven.RoleRepositoryPort;
import com.rob.application.ports.driving.RoleServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleUseCase implements RoleServicePort {

    private final RoleRepositoryPort roleRepositoryPort;
}

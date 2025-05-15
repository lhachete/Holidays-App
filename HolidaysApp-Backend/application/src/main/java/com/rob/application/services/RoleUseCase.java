package com.rob.application.services;

import com.rob.application.ports.driven.OrganizationRepositoryPort;
import com.rob.application.ports.driven.RoleRepositoryPort;
import com.rob.application.ports.driving.RoleServicePort;
import com.rob.domain.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleUseCase implements RoleServicePort {

    private final RoleRepositoryPort roleRepositoryPort;

    @Override
    public List<Role> getRolesByName(String name) {
        if (name == null || name.isEmpty()) {
            return roleRepositoryPort.findAll();
        }
        return roleRepositoryPort.findByNameContaining(name);
    }
}

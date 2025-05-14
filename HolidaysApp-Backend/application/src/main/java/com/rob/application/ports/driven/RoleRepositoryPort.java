package com.rob.application.ports.driven;

import com.rob.domain.models.Role;

import java.util.List;

public interface RoleRepositoryPort {
    List<Role> findByNameContaining(String name);

    List<Role> findAll();
}

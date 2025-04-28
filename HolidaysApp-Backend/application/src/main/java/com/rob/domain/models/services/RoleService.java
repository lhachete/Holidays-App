package com.rob.domain.models.services;

import com.rob.domain.models.dtos.RoleDTO;
import com.rob.domain.models.dtos.RoleUpdateDTO;
import com.rob.domain.models.entities.Role;
import com.rob.domain.models.enums.Roles;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    public Optional<List<Role>> getAllRoles();
    public Optional<Role> saveRole(Role role) throws Exception;
    public Optional<Role> findRoleById(Long id);
    public Optional<Role> findByRole(String role);
    public Optional<Role> updateRole(RoleUpdateDTO role) throws Exception;
    public Optional<Role> deleteRoleById(Long id);
}

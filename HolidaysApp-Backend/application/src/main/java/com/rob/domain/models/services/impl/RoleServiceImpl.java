package com.rob.domain.models.services.impl;

import com.rob.domain.models.dtos.RoleDTO;
import com.rob.domain.models.dtos.RoleUpdateDTO;
import com.rob.domain.models.repository.RoleRepository;
import com.rob.models.Role;
import com.rob.domain.models.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<List<Role>> getAllRoles() {
        Optional<List<Role>> roles = Optional.of(roleRepository.findAll());
        if (roles.isPresent())
            return roles;
        return Optional.empty();
    }

    @Override
    public Optional<Role> saveRole(RoleDTO role) throws Exception {
        List<Role> existingRoles = roleRepository.findAll();
        for (Role existingRole : existingRoles) {
            if (existingRole.getRole().equals(role.getRoleType())) {
                return Optional.empty();
            }
        }
        //return Optional.of(roleRepository.save(new Role(role)));
        return Optional.empty();
    }

    @Override
    public Optional<Role> findRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent())
            return role;
        return Optional.empty();
    }

    @Override
    public Optional<Role> findByRole(String role) {
        Optional<Role> existingRole = roleRepository.findByRole(role);
        if (existingRole.isPresent())
            return existingRole;
        return Optional.empty();
    }

    @Override
    public Optional<Role> updateRole(RoleUpdateDTO role) throws Exception {
        Optional<Role> roleOptional = roleRepository.findByRole(String.valueOf(role.getRoleType()));
        if (roleOptional.isPresent()) {
            Role roleToUpdate = roleOptional.get();
            roleToUpdate.setRole(role.getRoleType());
            return Optional.of(roleRepository.save(roleToUpdate));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Role> deleteRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            roleRepository.delete(role.get());
            return role;
        }
        return Optional.empty();
    }
}

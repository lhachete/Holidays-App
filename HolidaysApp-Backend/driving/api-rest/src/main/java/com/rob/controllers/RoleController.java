package com.rob.controllers;

import com.rob.domain.models.dtos.RoleDTO;
import com.rob.domain.models.entities.Role;
import com.rob.domain.models.enums.Roles;
import com.rob.domain.models.repository.RoleRepository;
import com.rob.domain.models.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles(@RequestParam(name = "roleType",required = false) String roleType) {
        if(roleType != null) {
            Optional<Role> role = roleService.findByRole(roleType);
            if (role.isPresent()) {
                return ResponseEntity.ok(List.of(new RoleDTO(role.get().getRole())));
            }
            return ResponseEntity.notFound().build();
        }
        Optional<List<Role>> roles = roleService.getAllRoles();
        if (!roles.isEmpty() && roles != null) {
            List<RoleDTO> roleDTOS = roles.get().stream().map(role -> new RoleDTO(role.getRole())).toList();
            return ResponseEntity.ok(roleDTOS);
        } else {
            return ResponseEntity.noContent().build();

        }
    }
}

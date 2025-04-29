package com.rob.controllers;

import com.rob.domain.models.dtos.RoleDTO;
import com.rob.repositories.entities.Role;
import com.rob.domain.models.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable ("id") Long id) {
        Optional<Role> role = roleService.findRoleById(id);
        if (role.isPresent()) {
            return ResponseEntity.ok(new RoleDTO(role.get().getRole()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<RoleDTO> saveRole(@RequestBody RoleDTO roleDTO) {
        try {
            Optional<Role> role = roleService.saveRole(roleDTO);
            if (role.isPresent()) {
                return ResponseEntity.ok(new RoleDTO(role.get().getRole()));
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoleDTO> deleteRole(@PathVariable("id") Long id) {
        try {
            Optional<Role> role = roleService.deleteRoleById(id);
            if (role.isPresent()) {
                return ResponseEntity.ok(new RoleDTO(role.get().getRole()));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

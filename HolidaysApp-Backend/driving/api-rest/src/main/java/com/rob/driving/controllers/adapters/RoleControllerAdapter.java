package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.RoleServicePort;
import com.rob.domain.models.Organization;
import com.rob.domain.models.Role;
import com.rob.driving.api.RolesApi;
import com.rob.driving.dtos.OrganizationDTO;
import com.rob.driving.dtos.RoleDTO;
import com.rob.driving.mappers.RoleDTOMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api-rest/roles")
public class RoleControllerAdapter implements RolesApi {

    private final RoleServicePort roleServicePort;
    private final RoleDTOMapper roleDTOMapper;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles(
            @RequestParam(name = "name", required = false) String name) {
        log.info("Se ha recibido una solicitud GET a /roles con el parámetro name: {}", name);
        List<RoleDTO> rolesDtos = new ArrayList<>();
        List<Role> roles = roleServicePort.getRolesByName(name);
        for (Role role : roles) {
            rolesDtos.add(roleDTOMapper.toRoleDTO(role));
        }
        log.debug("Se han encontrado {} roles", rolesDtos.size());
        return ResponseEntity.ok(rolesDtos);
    }
}

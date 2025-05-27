package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.OrganizationServicePort;
import com.rob.domain.models.Organization;
import com.rob.driving.dtos.OrganizationCreateDTO;
import com.rob.driving.mappers.OrganizationDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rob.driving.api.OrganizationsApi;
import com.rob.driving.dtos.OrganizationDTO;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api-rest/organizations")
public class OrganizationControllerAdapter implements OrganizationsApi {

    // se pone allargs constructor y private final para que spring lo inyecte
    private final OrganizationDTOMapper organizationDTOMapper;

    private final OrganizationServicePort organizationServicePort;

//    @PostConstruct
//    public void init() {
//        System.out.println("Controller loaded!");
//    }

    @GetMapping
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations(@RequestParam(name = "name",required = false) String name) {
        log.info("Se va a realizar una solicitud GET a /organizations con el parámetro name: {}", name);
        List<OrganizationDTO> organizationsDtos = new ArrayList<>();
        List<Organization> organizations = organizationServicePort.getOrganizationsByName(name);
        for (Organization organization : organizations) {
            organizationsDtos.add(organizationDTOMapper.toOrganizationDTO(organization));
        }
        log.debug("Se han encontrado {} organizaciones", organizationsDtos.size());
        return ResponseEntity.ok(organizationsDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable(value = "id") Integer id) {
        log.info("Se ha recibido una solicitud GET a /organizations/{} para obtener la organización por ID", id);
        Organization organization = organizationServicePort.getOrganizationById(id);
        log.debug("Organización encontrada: {}", organization);
        return ResponseEntity.ok(organizationDTOMapper.toOrganizationDTO(organization));
    }

    @PostMapping
    public ResponseEntity<OrganizationDTO> createOrganization(@RequestBody OrganizationCreateDTO organizationCreateDTO) {
        log.info("Se ha recibido una solicitud POST a /organizations para crear una nueva organización: {}", organizationCreateDTO);
        Organization organization = organizationDTOMapper.toOrganization(organizationCreateDTO);
        Organization createdOrganization = organizationServicePort.createOrganization(organization);
        log.debug("Organización creada: {}", createdOrganization);
        return ResponseEntity.ok(organizationDTOMapper.toOrganizationDTO(createdOrganization));
    }

    @PutMapping()
    public ResponseEntity<OrganizationDTO> updateOrganization(@Validated @RequestBody OrganizationDTO organizationDTO) {
        log.info("Se ha recibido una solicitud PUT a /organizations para actualizar la organización: {}", organizationDTO);
        Organization organization = organizationDTOMapper.toOrganization(organizationDTO);
        Organization updatedOrganization = organizationServicePort.updateOrganization(organization);
        log.debug("Organización actualizada: {}", updatedOrganization);
        return ResponseEntity.ok(organizationDTOMapper.toOrganizationDTO(updatedOrganization));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrganizationDTO> deleteOrganization(@PathVariable(value = "id") Integer id) {
        log.info("Se ha recibido una solicitud DELETE a /organizations/{} para eliminar la organización por ID", id);
        Organization deletedOrganization = organizationServicePort.deleteOrganizationById(id);
        log.debug("Organización eliminada: {}", deletedOrganization);
        return ResponseEntity.ok(organizationDTOMapper.toOrganizationDTO(deletedOrganization));
    }
}

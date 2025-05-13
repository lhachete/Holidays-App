package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.OrganizationServicePort;
import com.rob.domain.models.Organization;
import com.rob.driving.dtos.OrganizationCreateDTO;
import com.rob.driving.mappers.OrganizationDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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



@RestController
// inyeccion por constructor
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
        List<OrganizationDTO> organizationsDtos = new ArrayList<>();

        if(name != null) {
            List<Organization> organizations = organizationServicePort.getOrganizationsByName(name);
            for (Organization organization : organizations) {
                organizationsDtos.add(organizationDTOMapper.toOrganizationDTO(organization));
            }
            return ResponseEntity.ok(organizationsDtos);
        }
        List<Organization> organizations = organizationServicePort.getAllOrganizations();
        for (Organization organization : organizations) {
//            OrganizationDTO organizationDTO = new OrganizationDTO();
//            organizationDTO.setId(organization.getId());
//            organizationDTO.setName(organization.getName());
            organizationsDtos.add(organizationDTOMapper.toOrganizationDTO(organization));
        }
        return ResponseEntity.ok(organizationsDtos);
        // try catch con el error que lo coga de los use case
        // ej: error 404 y un mensaje
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable(value = "id") Integer id) {
        Optional<Organization> organization = organizationServicePort.getOrganizationById(id);
        if(organization.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(organizationDTOMapper.toOrganizationDTO(organization.get()));
    }

    @PostMapping
    public ResponseEntity<OrganizationDTO> createOrganization(@Validated @RequestBody OrganizationCreateDTO organizationCreateDTO) {
        Organization organization = organizationDTOMapper.toOrganization(organizationCreateDTO);
        Organization createdOrganization = organizationServicePort.createOrganization(organization);
        return ResponseEntity.ok(organizationDTOMapper.toOrganizationDTO(createdOrganization));
    }

    @PutMapping()
    public ResponseEntity<OrganizationDTO> updateOrganization(@Validated @RequestBody OrganizationDTO organizationDTO) {
        Organization organization = organizationDTOMapper.toOrganization(organizationDTO);
        Organization updatedOrganization = organizationServicePort.updateOrganization(organization);
        return ResponseEntity.ok(organizationDTOMapper.toOrganizationDTO(updatedOrganization));
    }
}

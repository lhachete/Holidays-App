package com.rob.controllers;

import com.rob.domain.models.dtos.OrganizationDTO;
import com.rob.domain.models.dtos.OrganizationUpdateDTO;
import com.rob.domain.models.entities.Organization;
import com.rob.domain.models.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api-rest/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<List<OrganizationDTO>> getOrganizationsByName(@RequestParam(name = "name",required = false) String name) {
        if(name != null) {
            Optional<List<Organization>> organizations = organizationService.findByContainingName(name);
            if (organizations.isPresent()) {
                List<OrganizationDTO> organizationDTOS = organizations.get().stream().map(OrganizationDTO::new).toList();
                return ResponseEntity.ok(organizationDTOS);
            }
            return ResponseEntity.notFound().build();
        }
        Optional<List<Organization>> organizations = organizationService.getAllOrganizations();
        if (organizations.isPresent()) {
            List<OrganizationDTO> organizationDTOS = organizations.get().stream().map(OrganizationDTO::new).collect(Collectors.toList());
            return ResponseEntity.ok(organizationDTOS);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable("id") Long id) {
        Optional<Organization> organization = organizationService.findOrganizationById(id);
        if (organization.isPresent()) {
            return ResponseEntity.ok(new OrganizationDTO(organization.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrganizationDTO> deleteOrganization(@PathVariable(name = "id", required = true) Long id) {
        Optional<Organization> deletedOrganization = organizationService.deleteOrganizationById(id);
        if (deletedOrganization.isPresent()) {
            return ResponseEntity.ok().body(new OrganizationDTO(deletedOrganization.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OrganizationDTO> saveOrganization(@RequestBody OrganizationDTO organization) {
        try {
            Optional<Organization> savedOrganization = organizationService.saveOrganization(organization);
            if (savedOrganization.isPresent()) {
                return ResponseEntity.ok(new OrganizationDTO(savedOrganization.get()));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping()
    public ResponseEntity<OrganizationDTO> updateOrganization(@RequestBody OrganizationUpdateDTO organization) {
        try {
            Optional<Organization> updatedOrganization = organizationService.updateOrganization(organization);
            if(updatedOrganization.isPresent()) {
                return ResponseEntity.ok(new OrganizationDTO(updatedOrganization.get()));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<OrganizationDTO> updateOrganizationById(@PathVariable(name = "id", required = true) Long id, @RequestBody OrganizationUpdateDTO organization) {
//        try {
//            Optional<Organization> updatedOrganization = organizationService.updateOrganizationById(id, organization);
//            if(updatedOrganization.isPresent()) {
//                return ResponseEntity.ok(new OrganizationDTO(updatedOrganization.get()));
//            }
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
}

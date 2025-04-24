package com.rob.controllers;

import com.rob.domain.models.dtos.OrganizationDTO;
import com.rob.domain.models.entities.Organization;
import com.rob.domain.models.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<List<OrganizationDTO>> getOrganizationsByName(@RequestParam(name = "name",required = false) String name) {
        if(name != null) {
            Optional<OrganizationDTO> organization = organizationService.findByName(name);
            if (organization.isPresent()) {
                return ResponseEntity.ok(List.of(organization.get()));
            }
            return ResponseEntity.notFound().build();
        }
        Optional<List<OrganizationDTO>> organizations = organizationService.getAllOrganizations();
        if (organizations.isPresent()) {
            return ResponseEntity.ok(organizations.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable("id") Long id) {
        Optional<OrganizationDTO> organization = organizationService.findOrganizationById(id);
        if (organization.isPresent()) {
            return ResponseEntity.ok(organization.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrganizationDTO> deleteOrganization(@PathVariable(name = "id", required = true) Long id) {
        Optional<OrganizationDTO> deletedOrganization = organizationService.deleteOrganizationById(id);
        if (deletedOrganization.isPresent()) {
            return ResponseEntity.ok().body(deletedOrganization.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OrganizationDTO> saveOrganization(@RequestBody OrganizationDTO organization) {
        try {
            Optional<OrganizationDTO> savedOrganization = organizationService.saveOrganization(organization);
            if (savedOrganization.isPresent()) {
                return ResponseEntity.ok(savedOrganization.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping()
    public ResponseEntity<OrganizationDTO> updateOrganization(@RequestBody Organization organization) {
        try {
            Optional<OrganizationDTO> updatedOrganization = organizationService.updateOrganization(organization);
            if(updatedOrganization.isPresent()) {
                return ResponseEntity.ok(updatedOrganization.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

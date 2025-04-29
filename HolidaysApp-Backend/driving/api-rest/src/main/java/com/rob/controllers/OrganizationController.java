package com.rob.controllers;

import com.rob.domain.models.dtos.OrganizationDTO;
import com.rob.domain.models.dtos.OrganizationUpdateDTO;
import com.rob.repositories.entities.Organization;
import com.rob.domain.models.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api-rest/organizations")
@Tag(name = "Organization Management", description = "Operations related to organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Operation(summary = "Get organizations by name or all", description = "Returns a list of organizations filtered by name if provided, or all organizations")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organizations found"),
            @ApiResponse(responseCode = "404", description = "No organizations found")
    })
    @GetMapping
    public ResponseEntity<List<OrganizationDTO>> getOrganizationsByName(@RequestParam(name = "name", required = false) String name) {
        if(name != null) {
            Optional<List<Organization>> organizations = organizationService.findByContainingName(name);
            return organizations.map(orgs -> ResponseEntity.ok(orgs.stream().map(OrganizationDTO::new).toList()))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        Optional<List<Organization>> organizations = organizationService.getAllOrganizations();
        return organizations.map(orgs -> ResponseEntity.ok(orgs.stream().map(OrganizationDTO::new).toList()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get organization by ID", description = "Returns a single organization by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organization found"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable("id") Long id) {
        return organizationService.findOrganizationById(id)
                .map(org -> ResponseEntity.ok(new OrganizationDTO(org)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete organization by ID", description = "Deletes an organization by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organization deleted"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<OrganizationDTO> deleteOrganization(@PathVariable(name = "id") Long id) {
        return organizationService.deleteOrganizationById(id)
                .map(org -> ResponseEntity.ok(new OrganizationDTO(org)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create new organization", description = "Saves a new organization")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organization created"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    @PostMapping
    public ResponseEntity<OrganizationDTO> saveOrganization(@RequestBody OrganizationDTO organization) {
        try {
            return organizationService.saveOrganization(organization)
                    .map(org -> ResponseEntity.ok(new OrganizationDTO(org)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Update an organization", description = "Updates an existing organization")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organization updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "404", description = "Organization not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    @PutMapping
    public ResponseEntity<OrganizationDTO> updateOrganization(@RequestBody OrganizationUpdateDTO organization) {
        try {
            return organizationService.updateOrganization(organization)
                    .map(org -> ResponseEntity.ok(new OrganizationDTO(org)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
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

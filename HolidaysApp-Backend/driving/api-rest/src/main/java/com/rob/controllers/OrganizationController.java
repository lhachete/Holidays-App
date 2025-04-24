package com.rob.controllers;

import com.rob.domain.models.entities.Organization;
import com.rob.domain.models.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<List<Organization>> getAllOrganizations() {
        Optional<List<Organization>> organizations = organizationService.getAllOrganizations();
        if (organizations.isPresent()) {
            return ResponseEntity.ok(organizations.get());
        }
        return ResponseEntity.notFound().build();
    }
}

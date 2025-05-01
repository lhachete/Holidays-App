package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.OrganizationServicePort;
import com.rob.domain.models.Organization;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api-rest/organizations")
public class OrganizationControllerAdapter {

    OrganizationServicePort organizationServicePort;

    @PostConstruct
    public void init() {
        System.out.println("Controller loaded!");
    }

    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationServicePort.getAllOrganizations();
    }
}

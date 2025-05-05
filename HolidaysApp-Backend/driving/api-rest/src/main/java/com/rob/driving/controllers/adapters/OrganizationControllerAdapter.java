package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.OrganizationServicePort;
import com.rob.driving.dtos.OrganizationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.rob.driving.controllers.OrganizationsApi;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequiredArgsConstructor
@RequestMapping("/api-rest/organizations")
public class OrganizationControllerAdapter implements OrganizationsApi {

    @Autowired
    OrganizationServicePort organizationServicePort;

    @PostConstruct
    public void init() {
        System.out.println("Controller loaded!");
    }

    @Tag(name = "get", description = "Get all organizations of Organizations APIs")
    @Operation(operationId = "getAllOrganizations",summary = "Get all organizations", description = "Get all organizations of Organizations APIs")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Wrong Request – invalid parameters"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Not authorized – invalid or expired token"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Denied – no permission to access the resource"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Organization not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations() {
        List<OrganizationDTO> organizationDTOS = new ArrayList<>();
        organizationServicePort.getAllOrganizations().stream().map(organization -> {
            OrganizationDTO organizationDTO = new OrganizationDTO();
            organizationDTO.setId(organization.getId());
            organizationDTO.setName(organization.getName());
            organizationDTOS.add(organizationDTO);
            return organizationDTO;
        });
        return ResponseEntity.ok(organizationDTOS);
        // try catch con el error que lo coga de los use case
        // ej: error 404 y un mensaje
    }
}

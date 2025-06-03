package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.DepartmentServicePort;
import com.rob.domain.models.Department;
import com.rob.driving.api.DepartmentsApi;
import com.rob.driving.dtos.DepartmentDTO;
import com.rob.driving.mappers.DepartmentDTOMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api-rest/departments")
public class DepartmentControllerAdapter implements DepartmentsApi {

    private final DepartmentServicePort departmentServicePort;
    private final DepartmentDTOMapper departmentDTOMapper;
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(@RequestParam(name = "name", required = false) String name) {
        log.info("Se ha recibido una solicitud GET a /departments con el parámetro name: {}", name);
        List<DepartmentDTO> departmentsDtos = new ArrayList<>();
        List<Department> departments = departmentServicePort.getDepartmentsByName(name);
        System.out.println("departments: " + departments);
        for (Department department : departments) {
            departmentsDtos.add(departmentDTOMapper.toDepartmentDTO(department));
        }
        log.debug("Se han encontrado {} departamentos", departmentsDtos.size());
        return ResponseEntity.ok(departmentsDtos);
    }
}

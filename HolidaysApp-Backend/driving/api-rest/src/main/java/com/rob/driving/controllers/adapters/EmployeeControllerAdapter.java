package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.EmployeeServicePort;
import com.rob.driving.api.EmployeesApi;
import com.rob.driving.dtos.EmployeeDTO;
import com.rob.driving.mappers.EmployeeDTOMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api-rest/employees")
@RequiredArgsConstructor
public class EmployeeControllerAdapter implements EmployeesApi {

    private final EmployeeServicePort employeeServicePort;
    private final EmployeeDTOMapper employeeDTOMapper;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(name = "name", required = false) String name) {
        log.info("Se ha recibido una solicitud GET a /employees con el parámetro name: {}", name);
        return ResponseEntity.ok(
                employeeServicePort.getAllEmployees(name)
                        .stream()
                        .map(employeeDTOMapper::toEmployeeDTO)
                        .toList());
    }
}

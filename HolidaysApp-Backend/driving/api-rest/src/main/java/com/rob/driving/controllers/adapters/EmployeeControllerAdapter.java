package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.EmployeeServicePort;
import com.rob.driving.api.EmployeesApi;
import com.rob.driving.dtos.EmployeeDTO;
import com.rob.driving.mappers.EmployeeDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-rest/employees")
@RequiredArgsConstructor
public class EmployeeControllerAdapter implements EmployeesApi {

    private final EmployeeServicePort employeeServicePort;
    private final EmployeeDTOMapper employeeDTOMapper;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(name = "name", required = false) String name) {
        return ResponseEntity.ok(
                employeeServicePort.getAllEmployees(name)
                        .stream()
                        .map(employeeDTOMapper::toEmployeeDTO)
                        .toList());
    }
}

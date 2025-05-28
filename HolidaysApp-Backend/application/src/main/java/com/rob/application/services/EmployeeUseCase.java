package com.rob.application.services;

import com.rob.application.ports.driven.EmployeeRepositoryPort;
import com.rob.application.ports.driving.EmployeeServicePort;
import com.rob.domain.models.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeUseCase implements EmployeeServicePort {

    private final EmployeeRepositoryPort employeeRepositoryPort;

    @Override
    public List<Employee> getAllEmployees(String name) {
        if(name == null || name.isEmpty()) {
            return employeeRepositoryPort.findAllEmployees();
        } else {
            return employeeRepositoryPort.findAllEmployeesByName(name);
        }
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepositoryPort.saveEmployee(employee);
    }
}

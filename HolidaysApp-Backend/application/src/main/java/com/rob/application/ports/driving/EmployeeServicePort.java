package com.rob.application.ports.driving;

import com.rob.domain.models.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeServicePort {
    List<Employee> getAllEmployees(String name);
}

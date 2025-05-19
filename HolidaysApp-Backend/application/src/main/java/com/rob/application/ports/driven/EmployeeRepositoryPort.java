package com.rob.application.ports.driven;

import com.rob.domain.models.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepositoryPort {

    List<Employee> findAllEmployees();

    List<Employee> findAllEmployeesByName(String name);
}

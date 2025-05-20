package com.rob.main.driven.repositories.adapters;

import com.rob.application.ports.driven.EmployeeRepositoryPort;
import com.rob.domain.models.Employee;
import com.rob.main.driven.repositories.EmployeeMOJpaRepository;
import com.rob.main.driven.repositories.mappers.EmployeeMOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeJpaRepositoryAdapter implements EmployeeRepositoryPort {

    private final EmployeeMOJpaRepository employeeMOJpaRepository;
    private final EmployeeMOMapper employeeMOMapper;


    @Override
    public List<Employee> findAllEmployees() {
        return employeeMOJpaRepository.findAll().stream().map(employeeMOMapper::toEmployee).toList();
    }

    @Override
    public List<Employee> findAllEmployeesByName(String name) {
        return employeeMOJpaRepository.findByFirstNameOrLastNameContaining(name, name)
                .stream()
                .map(employeeMOMapper::toEmployee)
                .toList();
    }
}

package com.rob.main.driven.repositories.adapters;

import com.rob.application.ports.driven.EmployeeRepositoryPort;
import com.rob.domain.models.Employee;
import com.rob.main.driven.repositories.EmployeeMOJpaRepository;
import com.rob.main.driven.repositories.mappers.EmployeeMOMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EmployeeJpaRepositoryAdapter implements EmployeeRepositoryPort {

    private final EmployeeMOJpaRepository employeeMOJpaRepository;
    private final EmployeeMOMapper employeeMOMapper;


    @Override
    public List<Employee> findAllEmployees() {
        log.info("Se van a obtener todos los empleados");
        return employeeMOJpaRepository.findAll().stream().map(employeeMOMapper::toEmployee).toList();
    }

    @Override
    public List<Employee> findAllEmployeesByName(String name) {
        log.info("Se van a buscar empleados por nombre: {}", name);
        return employeeMOJpaRepository.findByFirstNameOrLastNameContaining(name, name)
                .stream()
                .map(employeeMOMapper::toEmployee)
                .toList();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        log.info("Se va a guardar el empleado: {}", employee);
        return employeeMOMapper.toEmployee(employeeMOJpaRepository.save(employeeMOMapper.toEmployeeMO(employee)));
    }
}

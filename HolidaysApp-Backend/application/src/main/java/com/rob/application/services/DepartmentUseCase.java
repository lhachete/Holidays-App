package com.rob.application.services;

import com.rob.application.ports.driven.DepartmentRepositoryPort;
import com.rob.application.ports.driving.DepartmentServicePort;
import com.rob.domain.models.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DepartmentUseCase implements DepartmentServicePort {

    private final DepartmentRepositoryPort departmentRepositoryPort;

    @Override
    public List<Department> getDepartmentsByName(String name) {
        if(name == null || name.isEmpty()) {
            List<Department> departments = departmentRepositoryPort.findAll();
            System.out.println("departments: " + departments);
            return departmentRepositoryPort.findAll();
        }
        List<Department> departments = departmentRepositoryPort.findByNameContaining(name);;
        System.out.println("departments: " + departments);
        return departments;
    }
}

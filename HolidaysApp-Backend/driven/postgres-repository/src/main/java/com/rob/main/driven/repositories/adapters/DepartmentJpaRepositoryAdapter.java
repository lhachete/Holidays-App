package com.rob.main.driven.repositories.adapters;

import com.rob.application.ports.driven.DepartmentRepositoryPort;
import com.rob.domain.models.Department;
import com.rob.domain.models.Organization;
import com.rob.main.driven.repositories.DepartmentMOJpaRepository;
import com.rob.main.driven.repositories.mappers.DepartmentMOMapper;
import com.rob.main.driven.repositories.models.DepartmentMO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DepartmentJpaRepositoryAdapter implements DepartmentRepositoryPort {

    private final DepartmentMOJpaRepository departmentRepository;
    private final DepartmentMOMapper departmentMOMapper;

    @Override
    public List<Department> findAll() {
        List<DepartmentMO> departmentMOS = departmentRepository.findAll();
        System.out.println("DepartmentMO: " + departmentMOS);
        List<Department> departments = departmentMOS.stream().map(departmentMOMapper::toDepartment).toList();
        System.out.println("Department: " + departments);
        return departments;
//        return departmentRepository.findAll()
//                .stream()
//                .map(departmentMO -> {
//                    return departmentMOMapper.toDepartment(departmentMO);
//                })
//                .toList();
    }

    @Override
    public List<Department> findByNameContaining(String name) {
        return departmentRepository.findByNameContaining(name)
                .stream()
                .map(departmentMO -> {
                    return departmentMOMapper.toDepartment(departmentMO);
                })
                .toList();
    }
}

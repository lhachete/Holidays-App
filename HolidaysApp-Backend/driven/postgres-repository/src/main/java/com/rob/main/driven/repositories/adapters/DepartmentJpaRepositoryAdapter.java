package com.rob.main.driven.repositories.adapters;

import com.rob.application.ports.driven.DepartmentRepositoryPort;
import com.rob.domain.models.Department;
import com.rob.domain.models.Organization;
import com.rob.main.driven.repositories.DepartmentMOJpaRepository;
import com.rob.main.driven.repositories.mappers.DepartmentMOMapper;
import com.rob.main.driven.repositories.models.DepartmentMO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DepartmentJpaRepositoryAdapter implements DepartmentRepositoryPort {

    private final DepartmentMOJpaRepository departmentRepository;
    private final DepartmentMOMapper departmentMOMapper;

    @Override
    public List<Department> findAll() {
        log.info("Se van a obtener todos los departamentos");
        List<DepartmentMO> departmentMOS = departmentRepository.findAll();
        log.info("Se van a mapear los departamentos a objetos de dominio");
        List<Department> departments = departmentMOS.stream().map(departmentMOMapper::toDepartment).toList();
        log.info("Se han obtenido {} departamentos", departments.size());
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
        log.info("Se van a buscar departamentos que contengan el nombre: {}", name);
        return departmentRepository.findByNameContaining(name)
                .stream()
                .map(departmentMO -> {
                    return departmentMOMapper.toDepartment(departmentMO);
                })
                .toList();
    }
}

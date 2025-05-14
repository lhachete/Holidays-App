package com.rob.application.ports.driven;

import com.rob.domain.models.Department;
import com.rob.domain.models.Organization;

import java.util.List;

public interface DepartmentRepositoryPort {
    List<Department> findAll();
    List<Department> findByNameContaining(String name);
}

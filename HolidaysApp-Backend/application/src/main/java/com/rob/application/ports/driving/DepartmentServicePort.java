package com.rob.application.ports.driving;

import com.rob.domain.models.Department;

import java.util.List;

public interface DepartmentServicePort {

    List<Department> getDepartmentsByName(String name);
}

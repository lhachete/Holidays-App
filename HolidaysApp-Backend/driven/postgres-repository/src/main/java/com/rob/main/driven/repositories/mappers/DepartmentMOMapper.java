package com.rob.main.driven.repositories.mappers;

import com.rob.domain.models.Department;
import com.rob.domain.models.Role;
import com.rob.main.driven.repositories.models.DepartmentMO;
import com.rob.main.driven.repositories.models.RoleMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

// Esto le dice a Spring que este es un componente y que lo escanee para inyectarlo donde sea necesario
@Mapper(componentModel = "spring", uses = {OrganizationMOMapper.class})
public interface DepartmentMOMapper {

    @Mapping(source = "organization", target = "organization")
    DepartmentMO toDepartmentMO(Department department);

    @Mapping(source = "organization", target = "organization")
    Department toDepartment(DepartmentMO departmentMO);
}

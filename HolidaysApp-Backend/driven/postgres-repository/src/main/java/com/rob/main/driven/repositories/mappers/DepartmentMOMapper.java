package com.rob.main.driven.repositories.mappers;

import com.rob.domain.models.Department;
import com.rob.domain.models.Role;
import com.rob.main.driven.repositories.models.DepartmentMO;
import com.rob.main.driven.repositories.models.RoleMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring", uses = {OrganizationMOMapper.class})
public interface DepartmentMOMapper {

    @Mapping(source = "organization", target = "organization")
    DepartmentMO toDepartmentMO(Department department);

    @Mapping(source = "organization", target = "organization")
    Department toDepartment(DepartmentMO departmentMO);
}

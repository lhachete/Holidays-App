package com.rob.driving.mappers;

import com.rob.domain.models.Department;
import com.rob.driving.dtos.DepartmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {OrganizationDTOMapper.class})
public interface DepartmentDTOMapper {

    @Mapping(source = "organization.id", target = "organizationId")
    DepartmentDTO toDepartmentDTO(Department department);

    @Mapping(source = "organizationId", target = "organization.id")
    Department toDepartment(DepartmentDTO departmentDTO);
}

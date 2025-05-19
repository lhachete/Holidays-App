package com.rob.driving.mappers;

import com.rob.domain.models.Department;
import com.rob.driving.dtos.DepartmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring", uses = {OrganizationDTOMapper.class})
public interface DepartmentDTOMapper {

    @Mapping(source = "organization", target = "organization")
    DepartmentDTO toDepartmentDTO(Department department);

    @Mapping(source = "organization", target = "organization")
    Department toDepartment(DepartmentDTO departmentDTO);
}

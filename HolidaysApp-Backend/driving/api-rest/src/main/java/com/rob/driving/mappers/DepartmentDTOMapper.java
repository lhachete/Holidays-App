package com.rob.driving.mappers;

import com.rob.domain.models.Department;
import com.rob.driving.dtos.DepartmentDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface DepartmentDTOMapper {

    DepartmentDTO toDepartmentDTO(Department department);
    Department toDepartment(DepartmentDTO departmentDTO);
}

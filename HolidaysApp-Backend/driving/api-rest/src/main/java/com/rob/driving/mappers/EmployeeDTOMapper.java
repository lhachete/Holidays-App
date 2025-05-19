package com.rob.driving.mappers;

import com.rob.domain.models.Employee;
import com.rob.driving.dtos.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        UserDTOMapper.class,
        DepartmentDTOMapper.class,
        ProjectDTOMapper.class
})
public interface EmployeeDTOMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(source = "project", target = "project")
    @Mapping(source = "dept", target = "dept")
    @Mapping(source = "id", target = "personId")
    EmployeeDTO toEmployeeDTO(Employee employee);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "project", target = "project")
    @Mapping(source = "dept", target = "dept")
    @Mapping(source = "personId", target = "id")
    Employee toEmployee(EmployeeDTO employeeDTO);
}

package com.rob.main.driven.repositories.mappers;

import com.rob.domain.models.Employee;
import com.rob.main.driven.repositories.models.EmployeeMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        UserMOMapper.class,
        DepartmentMOMapper.class,
        ProjectMOMapper.class
})
public interface EmployeeMOMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(source = "project", target = "project")
    @Mapping(source = "dept", target = "dept")
    EmployeeMO toEmployeeMO(Employee employee);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "project", target = "project")
    @Mapping(source = "dept", target = "dept")
    Employee toEmployee(EmployeeMO employeeMO);
}

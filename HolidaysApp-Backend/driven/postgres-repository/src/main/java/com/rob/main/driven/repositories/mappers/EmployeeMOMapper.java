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

    //@Mapping(source = "project", target = "project")
    //@Mapping(source = "dept", target = "dept")
    default EmployeeMO toEmployeeMO(Employee employee) {
        if(employee == null) {
            return null;
        }
        EmployeeMO employeeMO = new EmployeeMO();
        if(employee.getId() != null)
            employeeMO.setId(employee.getId());
        if(employeeMO.getDept() == null && employeeMO.getProject() == null) {
            employeeMO.setFirstName(employee.getFirstName());
            employeeMO.setLastName(employee.getLastName());
        }
        return employeeMO;
    }

//    @Mapping(source = "project", target = "project")
//    @Mapping(source = "dept", target = "dept")
    default Employee toEmployee(EmployeeMO employeeMO) {
        if(employeeMO == null) {
            return null;
        }
        Employee employee = new Employee();
        if(employeeMO.getId() != null)
            employee.setId(employeeMO.getId());
        if(employeeMO.getDept() == null && employeeMO.getProject() == null) {
            employee.setFirstName(employeeMO.getFirstName());
            employee.setLastName(employeeMO.getLastName());
        }
        return employee;
    }
}

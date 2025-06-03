package com.rob.driving.mappers;

import com.rob.domain.models.Employee;
import com.rob.driving.dtos.EmployeeDTO;
import com.rob.main.driven.repositories.models.EmployeeMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        UserDTOMapper.class,
        DepartmentDTOMapper.class,
        ProjectDTOMapper.class
})
public interface EmployeeDTOMapper {

    default EmployeeDTO toEmployeeDTO(Employee employee) {
        if(employee == null) {
            return null;
        }
        EmployeeDTO employeeDTO = new EmployeeDTO();
        if(employee.getId() != null)
            employeeDTO.setPersonId(employee.getId());
        if(employeeDTO.getDept() == null && employeeDTO.getProject() == null) {
            employeeDTO.setFirstName(employee.getFirstName());
            employeeDTO.setLastName(employee.getLastName());
        }
        return employeeDTO;
    }

    default Employee toEmployee(EmployeeDTO employeeDTO) {
        if(employeeDTO == null) {
            return null;
        }
        Employee employee = new Employee();
        if(employeeDTO.getPersonId() != null)
            employee.setId(employeeDTO.getPersonId());
        if(employeeDTO.getDept() == null && employeeDTO.getProject() == null) {
            employee.setFirstName(employeeDTO.getFirstName());
            employee.setLastName(employeeDTO.getLastName());
        }
        return employee;
    }
}

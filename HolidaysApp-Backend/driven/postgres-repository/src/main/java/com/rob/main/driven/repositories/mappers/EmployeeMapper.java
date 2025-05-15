package com.rob.main.driven.repositories.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring", uses = {OrganizationMOMapper.class, DepartmentMOMapper.class, ProjectMOMapper.class,})
public interface EmployeeMapper {
}

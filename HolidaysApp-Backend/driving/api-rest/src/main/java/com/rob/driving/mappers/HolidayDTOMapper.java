package com.rob.driving.mappers;

import com.rob.domain.models.Employee;
import com.rob.domain.models.Holiday;
import com.rob.driving.dtos.EmployeeDTO;
import com.rob.driving.dtos.HolidayDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserDTOMapper.class})
public interface HolidayDTOMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(source = "reviewedByAdmin", target = "reviewedByAdmin")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "deletedBy", target = "deletedBy")
    HolidayDTO toHolidayDTO(Holiday holiday);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "reviewedByAdmin", target = "reviewedByAdmin")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "deletedBy", target = "deletedBy")
    Holiday toHoliday(HolidayDTO holidayDTO);
}

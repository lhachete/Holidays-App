package com.rob.main.driven.repositories.mappers;

import com.rob.domain.models.Holiday;
import com.rob.main.driven.repositories.models.HolidayMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMOMapper.class})
public interface HolidayMOMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(source = "reviewedByAdmin", target = "reviewedByAdmin")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "deletedBy", target = "deletedBy")
    HolidayMO toHolidayMO(Holiday holiday);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "reviewedByAdmin", target = "reviewedByAdmin")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "deletedBy", target = "deletedBy")
    Holiday toHoliday(HolidayMO holidayMO);
}

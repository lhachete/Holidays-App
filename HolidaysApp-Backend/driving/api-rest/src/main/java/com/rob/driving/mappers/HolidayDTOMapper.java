package com.rob.driving.mappers;

import com.rob.domain.models.Holiday;
import com.rob.domain.models.User;
import com.rob.driving.dtos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserDTOMapper.class})
public interface HolidayDTOMapper {

    @Mapping(source = "id", target = "holidayId")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "reviewedByAdmin", target = "reviewedByAdmin")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "deletedBy", target = "deletedBy")
    HolidayDTO toHolidayDTO(Holiday holiday);

    @Mapping(source = "id", target = "data.holidayId")
    @Mapping(source = "user", target = "data.user")
    @Mapping(source = "reviewedByAdmin", target = "data.reviewedByAdmin")
    @Mapping(source = "createdBy", target = "data.createdBy")
    @Mapping(source = "updatedBy", target = "data.updatedBy")
    @Mapping(source = "deletedBy", target = "data.deletedBy")
    HolidayResponse toHolidayResponse(Holiday holiday);

    default HolidayCollectionResponse toHolidayCollectionResponse(List<Holiday> holidays) {
        List<HolidayDTO> userDTOList = holidays.stream()
                .map(this::toHolidayDTO)
                .collect(Collectors.toList());

        return new HolidayCollectionResponse().data(userDTOList);
    }

    @Mapping(source = "holidayId", target = "id")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "vacationType", target = "vacationType")
    @Mapping(source = "reviewedByAdmin", target = "reviewedByAdmin")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "deletedBy", target = "deletedBy")
    Holiday toHoliday(HolidayDTO holidayDTO);


    @Mapping(source = "holidayStartDate", target = "holidayStartDate")
    @Mapping(source = "holidayEndDate", target = "holidayEndDate")
    @Mapping(source = "vacationType", target = "vacationType")
    HolidayDTO holidayRequestDTOtoHolidayDTO(HolidayRequestDTO holidayRequestDTO);

    @Mapping(source = "holidayId", target = "id")
    @Mapping(source = "holidayStartDate", target = "holidayStartDate")
    @Mapping(source = "holidayEndDate", target = "holidayEndDate")
    @Mapping(source = "vacationType", target = "vacationType")
    Holiday holidayUpdateRequestDTOtoHoliday(HolidayUpdateRequestDTO holidayRequestDTO);

    @Mapping(source = "holidayId", target = "holidayId")
    @Mapping(source = "holidayStartDate", target = "holidayStartDate")
    @Mapping(source = "holidayEndDate", target = "holidayEndDate")
    @Mapping(source = "vacationType", target = "vacationType")
    HolidayDTO holidayUpdateRequestDTOtoHolidayDTO(HolidayUpdateRequestDTO holidayRequestDTO);
}

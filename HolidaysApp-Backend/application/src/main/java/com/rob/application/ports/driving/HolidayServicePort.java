package com.rob.application.ports.driving;

import com.rob.domain.models.Holiday;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HolidayServicePort {
    List<Holiday> getAllHolidaysOrByUser(boolean getAll, Integer userId);
    List<Holiday> getHolidaysByUserId(Integer id);
    Holiday addHoliday(Holiday holiday);
    Holiday getHolidayById(Integer id);
    Holiday deleteHolidayById(Integer holidayId);
    Holiday updateHoliday(Holiday holiday);
}

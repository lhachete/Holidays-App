package com.rob.application.ports.driving;

import com.rob.domain.models.Holiday;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HolidayServicePort {

    List<Holiday> getAllHolidays();
}

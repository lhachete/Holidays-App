package com.rob.application.services;

import com.rob.application.ports.driven.HolidayRepositoryPort;
import com.rob.application.ports.driving.HolidayServicePort;
import com.rob.domain.models.Holiday;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayUseCase implements HolidayServicePort {

    private final HolidayRepositoryPort holidayRepositoryPort;

    @Override
    public List<Holiday> getAllHolidays() {
        return holidayRepositoryPort.findAllHolidays();
    }
}

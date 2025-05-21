package com.rob.application.services;

import com.rob.application.ports.driven.HolidayRepositoryPort;
import com.rob.application.ports.driving.HolidayServicePort;
import com.rob.domain.models.Holiday;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayUseCase implements HolidayServicePort {

    private final HolidayRepositoryPort holidayRepositoryPort;

    @Override
    public List<Holiday> getHolidaysByUserId(Integer userId) {
        if(userId != null) {
            return holidayRepositoryPort.findByUserId(userId);
        }
        return holidayRepositoryPort.findAllHolidays();
    }

    @Override
    public Holiday addHoliday(Holiday holiday) {
        System.out.println("Holiday: " + holiday);
        if(holiday.getHolidayEndDate().isBefore(holiday.getHolidayStartDate())) {
            throw new IllegalArgumentException("La fecha de fin de vacaciones no puede ser anterior a la fecha de inicio.");
        }
        if(holidayRepositoryPort.countOverlappingVacations(holiday.getUser().getId(), holiday.getHolidayStartDate(), holiday.getHolidayEndDate()) > 0) {
            throw new IllegalArgumentException("Las fechas de vacaciones se solapan con otras vacaciones existentes.");
        }
        holiday.setCreatedAt(OffsetDateTime.now());
        holiday.setCreatedBy(holiday.getUser());
        holiday.setVacationState("APROBADA");
        holiday.setIsDeleted(false);
        return holidayRepositoryPort.save(holiday);
    }

    @Override
    public Holiday getHolidayById(Integer id) {
        return holidayRepositoryPort.findById(id);
    }

    @Override
    public Holiday deleteHolidayById(Integer holidayId) {
        return holidayRepositoryPort.deleteById(holidayId);
    }
}

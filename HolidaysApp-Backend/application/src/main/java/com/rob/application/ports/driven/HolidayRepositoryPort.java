package com.rob.application.ports.driven;

import com.rob.domain.models.Holiday;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HolidayRepositoryPort {
    List<Holiday> findAllHolidays();

    List<Holiday> findByUserId(Integer userId);

    Holiday save(Holiday holiday);

    long countOverlappingVacationsForCreation(Integer userId, LocalDate startDate,LocalDate endDate);

    long countOverlappingVacationsForUpdate(Integer userId, LocalDate startDate,LocalDate endDate, Integer holidayId);

    Holiday findById(Integer id);

    Holiday deleteById(Integer holidayId);

    Holiday findByIdAndUserId(Integer id, Integer userId);
}

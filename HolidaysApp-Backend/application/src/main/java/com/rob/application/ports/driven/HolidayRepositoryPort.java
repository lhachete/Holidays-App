package com.rob.application.ports.driven;

import com.rob.domain.models.Holiday;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepositoryPort {
    List<Holiday> findAllHolidays();

    List<Holiday> findByUserId(Integer userId);

    Holiday save(Holiday holiday);

    long countOverlappingVacations(Integer userId, LocalDate startDate,LocalDate endDate);
}

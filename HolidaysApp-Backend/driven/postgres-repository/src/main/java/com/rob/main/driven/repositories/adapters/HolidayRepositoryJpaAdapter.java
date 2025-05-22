package com.rob.main.driven.repositories.adapters;

import com.rob.application.ports.driven.HolidayRepositoryPort;
import com.rob.domain.models.Holiday;
import com.rob.main.driven.repositories.HolidayMOJpaRepository;
import com.rob.main.driven.repositories.mappers.HolidayMOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HolidayRepositoryJpaAdapter implements HolidayRepositoryPort {

    private final HolidayMOJpaRepository holidayMOJpaRepository;
    private final HolidayMOMapper holidayMOMapper;

    @Override
    public List<Holiday> findAllHolidays() {
        return holidayMOJpaRepository.findAll().stream().map(holidayMOMapper::toHoliday).toList();
    }

    @Override
    public List<Holiday> findByUserId(Integer userId) {
        return holidayMOJpaRepository.findByUser_Id(userId).stream().map(holidayMOMapper::toHoliday).toList();
    }

    @Override
    public Holiday save(Holiday holiday) {
        return holidayMOMapper.toHoliday(holidayMOJpaRepository.save(holidayMOMapper.toHolidayMO(holiday)));
    }

    @Override
    public long countOverlappingVacations(Integer userId, LocalDate startDate, LocalDate endDate, Integer holidayId) {
        return holidayMOJpaRepository.countOverlappingVacations(userId, startDate, endDate, holidayId);
    }

    @Override
    public Holiday findById(Integer id) {
        return holidayMOMapper.toHoliday(holidayMOJpaRepository.findById(id).get());
    }

    @Override
    public Holiday deleteById(Integer holidayId) {
        Holiday deletedHoliday = findById(holidayId);
        deletedHoliday.setIsDeleted(true);
        holidayMOJpaRepository.deleteById(holidayId);
        return deletedHoliday;
    }

    @Override
    public Holiday findByIdAndUserId(Integer id, Integer userId) {
        return holidayMOMapper.toHoliday(holidayMOJpaRepository.findByIdAndUser_Id(id, userId).orElse(null));
    }
}

package com.rob.main.driven.repositories.adapters;

import com.rob.application.ports.driven.HolidayRepositoryPort;
import com.rob.domain.models.Holiday;
import com.rob.main.driven.repositories.HolidayMOJpaRepository;
import com.rob.main.driven.repositories.mappers.HolidayMOMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HolidayRepositoryJpaAdapter implements HolidayRepositoryPort {

    private final HolidayMOJpaRepository holidayMOJpaRepository;
    private final HolidayMOMapper holidayMOMapper;

    @Override
    public List<Holiday> findAllHolidays() {
        log.info("Se van a obtener todas las vacaciones");
        return holidayMOJpaRepository.findAll().stream().map(holidayMOMapper::toHoliday).toList();
    }

    @Override
    public List<Holiday> findByUserId(Integer userId) {
        log.info("Se van a obtener las vacaciones del usuario con ID: {}", userId);
        return holidayMOJpaRepository.findByUser_Id(userId).stream().map(holidayMOMapper::toHoliday).toList();
    }

    @Override
    public Holiday save(Holiday holiday) {
        log.info("Se va a guardar la vacación: {}", holiday);
        return holidayMOMapper.toHoliday(holidayMOJpaRepository.save(holidayMOMapper.toHolidayMO(holiday)));
    }

    @Override
    public long countOverlappingVacations(Integer userId, LocalDate startDate, LocalDate endDate, Integer holidayId) {
        log.info("Se van a contar las vacaciones superpuestas para el usuario con ID: {}, desde: {}, hasta: {}, excluyendo ID de vacación: {}",
                 userId, startDate, endDate, holidayId);
        return holidayMOJpaRepository.countOverlappingVacations(userId, startDate, endDate, holidayId);
    }

    @Override
    public Holiday findById(Integer id) {
        log.info("Se va a buscar la vacación con ID: {}", id);
        return holidayMOMapper.toHoliday(holidayMOJpaRepository.findById(id).get());
    }

    @Override
    public Holiday deleteById(Integer holidayId) {
        log.info("Se va a eliminar la vacación con ID: {}", holidayId);
        Holiday deletedHoliday = findById(holidayId);
        deletedHoliday.setIsDeleted(true);
        holidayMOJpaRepository.deleteById(holidayId);
        return deletedHoliday;
    }

    @Override
    public Holiday findByIdAndUserId(Integer id, Integer userId) {
        log.info("Se va a buscar la vacación con ID: {} para el usuario con ID: {}", id, userId);
        return holidayMOMapper.toHoliday(holidayMOJpaRepository.findByIdAndUser_Id(id, userId).orElse(null));
    }
}

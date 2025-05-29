package com.rob.main.driven.repositories;

import com.rob.main.driven.repositories.models.HolidayMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HolidayMOJpaRepository extends JpaRepository<HolidayMO, Integer> {
    List<HolidayMO> findByUser_Id(Integer userId);

    @Query("SELECT COUNT(h) FROM HolidayMO h " +
            "WHERE h.user.id = :userId " +
            "AND h.holidayStartDate <= :endDate " +
            "AND h.holidayEndDate >= :startDate " +
            "AND h.id <> :holidayId ")
    long countOverlappingVacations(
            @Param("userId") Integer userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("holidayId") Integer holidayId
    );

    @Query("SELECT COUNT(h) FROM HolidayMO h " +
            "WHERE h.user.id = :userId " +
            "AND h.holidayStartDate <= :endDate " +
            "AND h.holidayEndDate >= :startDate")
    long countOverlappingVacationsForCreation(
            @Param("userId") Integer userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT COUNT(h) FROM HolidayMO h " +
            "WHERE h.user.id = :userId " +
            "AND h.holidayStartDate <= :endDate " +
            "AND h.holidayEndDate >= :startDate " +
            "AND h.id <> :holidayId")
    long countOverlappingVacationsForUpdate(
            @Param("userId") Integer userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("holidayId") Integer holidayId
    );

    Optional<HolidayMO> findById(Integer id);

    Optional<HolidayMO> findByIdAndUser_Id(Integer id, Integer userId);
}
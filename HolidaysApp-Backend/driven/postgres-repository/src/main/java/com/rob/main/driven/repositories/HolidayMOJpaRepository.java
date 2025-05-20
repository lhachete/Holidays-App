package com.rob.main.driven.repositories;

import com.rob.domain.models.Holiday;
import com.rob.main.driven.repositories.models.HolidayMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HolidayMOJpaRepository extends JpaRepository<HolidayMO, Integer> {
    List<HolidayMO> findByUser_Id(Integer userId);
}
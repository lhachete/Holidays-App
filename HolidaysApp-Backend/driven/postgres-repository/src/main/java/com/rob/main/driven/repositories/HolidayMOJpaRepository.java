package com.rob.main.driven.repositories;

import com.rob.main.driven.repositories.models.HolidayMO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayMOJpaRepository extends JpaRepository<HolidayMO, Integer> {
}
package com.rob.domain.models.repository;

import com.rob.domain.models.entities.Holidays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidaysRepository extends JpaRepository<Holidays, Long> {
}
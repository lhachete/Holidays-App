package com.rob.repositories.repository;

import com.rob.models.Holidays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidaysRepository extends JpaRepository<Holidays, Long> {
}
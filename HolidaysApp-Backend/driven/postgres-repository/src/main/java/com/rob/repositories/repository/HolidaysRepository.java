package com.rob.repositories.repository;

import com.rob.repositories.entities.Holidays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidaysRepository extends JpaRepository<Holidays, Long> {
}
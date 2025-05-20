package com.rob.main.driven.repositories;

import com.rob.main.driven.repositories.models.EmployeeMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeMOJpaRepository extends JpaRepository<EmployeeMO, Integer> {
  List<EmployeeMO> findByFirstNameOrLastNameContaining(String firstName, String lastName);
}
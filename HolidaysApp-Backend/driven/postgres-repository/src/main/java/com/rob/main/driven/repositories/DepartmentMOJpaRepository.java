package com.rob.main.driven.repositories;

import com.rob.main.driven.repositories.models.DepartmentMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentMOJpaRepository extends JpaRepository<DepartmentMO, Integer> {
  List<DepartmentMO> findAll();
  List<DepartmentMO> findByNameContaining(String name);
}
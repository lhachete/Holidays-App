package com.rob.main.driven.repositories;

import com.rob.main.driven.repositories.models.ProjectMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMOJpaRepository extends JpaRepository<ProjectMO, Integer> {

  List<ProjectMO> findByNameContaining(String name);
}
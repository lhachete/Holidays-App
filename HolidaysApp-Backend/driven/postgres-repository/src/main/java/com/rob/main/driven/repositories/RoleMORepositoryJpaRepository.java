package com.rob.main.driven.repositories;

import com.rob.main.driven.repositories.models.RoleMO;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleMORepositoryJpaRepository extends JpaRepository<RoleMO, Integer> {
  List<RoleMO> findByNameContaining(String rol);
  @NonNull List<RoleMO> findAll();
}
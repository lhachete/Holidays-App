package com.rob.main.driven.repositories;

import com.rob.main.driven.repositories.models.UserMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserMOJpaRepository extends JpaRepository<UserMO, Integer> {
    List<UserMO> findByUsernameContaining(String username);
    @NonNull List<UserMO> findAll();
}
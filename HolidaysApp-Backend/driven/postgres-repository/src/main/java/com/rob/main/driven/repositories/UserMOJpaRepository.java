package com.rob.main.driven.repositories;

import com.rob.main.driven.repositories.models.UserMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserMOJpaRepository extends JpaRepository<UserMO, Integer> {
    List<UserMO> findByUsernameContaining(String username);
    @NonNull List<UserMO> findAll();

    Optional<UserMO> findByUsernameOrEmailAndPassword(String username, String email, String password);
}
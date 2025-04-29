package com.rob.repositories.repository;

import com.rob.repositories.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
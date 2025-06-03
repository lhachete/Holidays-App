package com.rob.application.ports.driven;

import com.rob.domain.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryPort {
    List<User> findByUsernameContaining(String name);

    List<User> findAll();

    User findByUsername(String username);

    User findByEmail(String email);

    User save(User user);

    User findById(Integer userId);

    User findByUsernameOrEmail(String username, String email);

    boolean findByCodeColor(String colorCode);

    List<User> findByFullName(String nameAndLastName);
}

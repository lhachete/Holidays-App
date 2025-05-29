package com.rob.application.ports.driven;

import com.rob.domain.models.Role;
import com.rob.domain.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryPort {
    List<User> findByNameContaining(String name);

    List<User> findAll();

    User findByUsernameOrEmailAndHashedPassword(String usernameOrEmail, String password);

    User findByUsername(String username);

    User findByEmail(String email);

    User save(User user);

    User findById(Integer userId);

    User findByUsernameOrEmail(String username, String email);

    User findByCodeColor(String colorCode);
}

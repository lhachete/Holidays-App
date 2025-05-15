package com.rob.application.ports.driven;

import com.rob.domain.models.Role;
import com.rob.domain.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryPort {
    List<User> findByNameContaining(String name);

    List<User> findAll();

    User findByUsernameAndPassword(String username, String password);

    User save(User user);
}

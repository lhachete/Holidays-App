package com.rob.application.services;

import com.rob.application.ports.driven.UserRepositoryPort;
import com.rob.application.ports.driving.UserServicePort;
import com.rob.domain.models.User;
import com.rob.domain.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUseCase implements UserServicePort {

    private final UserRepositoryPort userRepositoryPort;


    @Override
    public List<User> getUsersByUsername(String username) {
        if(username == null || username.isEmpty()) {
            return userRepositoryPort.findAll();
        }
        return userRepositoryPort.findByNameContaining(username);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        return userRepositoryPort.findByUsernameAndPassword(username, password);
    }

    @Override
    public User register(String username, String password) {

        Role defaultRole = new Role(2, "USUARIO");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(defaultRole);
        user.setEnabled(true);

        // No se asigna ningún ID
       return userRepositoryPort.save(user);
    }
}

package com.rob.application.services;

import com.rob.application.ports.driven.UserRepositoryPort;
import com.rob.application.ports.driving.UserServicePort;
import com.rob.domain.models.User;
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
}

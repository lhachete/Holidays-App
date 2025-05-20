package com.rob.application.services;

import com.rob.application.ports.driven.UserRepositoryPort;
import com.rob.application.ports.driving.UserServicePort;
import com.rob.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public User getUserByUsernameOrEmailAndPassword(String usernameOrEmail, String password) {
        User user = userRepositoryPort.findByUsernameOrEmailAndPassword(usernameOrEmail, password);
        if(user == null) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username/email or password", null);
        }
        return user;
    }
}

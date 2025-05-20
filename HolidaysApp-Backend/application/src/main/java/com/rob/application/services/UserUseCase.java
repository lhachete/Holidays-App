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
        User userWithEmail= userRepositoryPort.findByEmail(usernameOrEmail);
        if(userWithEmail != null) {
            return user;
        }
        if(user == null || !user.getPassword().equals(password)) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username/email or password", null);
        }
        return user;
    }

    @Override
    public boolean usernameExists(String username) {
        User user = userRepositoryPort.findByUsername(username);
        if(user != null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username already in use", null);
        }
        return false;
    }

    @Override
    public boolean emailExists(String email) {
        User user = userRepositoryPort.findByEmail(email);
        if(user != null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email already in use", null);
        }
        return false;
    }

    @Override
    public User createUser(User user) {
        if(!usernameExists(user.getUsername()) && !emailExists(user.getEmail())) {
            user.setEnabled(true);
            return userRepositoryPort.save(user);
        }
        return null;
    }
}

package com.rob.application.services;

import com.rob.application.ports.driven.UserRepositoryPort;
import com.rob.application.ports.driving.UserServicePort;
import com.rob.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUseCase implements UserServicePort {

    private final UserRepositoryPort userRepositoryPort;
    private final JWTServiceUseCase jwtServiceUseCase;
    private final AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);

    @Override
    public List<User> getUsersByUsername(String username) {
        if(username == null || username.isEmpty()) {
            return userRepositoryPort.findAll();
        }
        return userRepositoryPort.findByNameContaining(username);
    }

    @Override
    public User getUserByUsernameOrEmailAndHashedPassword(String usernameOrEmail, String password) {
        User user = userRepositoryPort.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if(user == null || !encoder.matches(password, user.getHashedPassword())) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username/email o contraseñas invalidas", null);
        }
        return user;
    }

    @Override
    public boolean usernameExists(String username) {
        User user = userRepositoryPort.findByUsername(username);
        if(user != null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Nombre de usuario ya en uso", null);
        }
        return false;
    }

    @Override
    public boolean emailExists(String email) {
        User user = userRepositoryPort.findByEmail(email);
        if(user != null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ya en uso", null);
        }
        return false;
    }

    @Override
    public User createUser(User user) {
        if(!usernameExists(user.getUsername()) && !emailExists(user.getEmail())) {
            user.setHashedPassword(encoder.encode(user.getHashedPassword()));
            user.setEnabled(true);
            return userRepositoryPort.save(user);
        }
        return null;
    }

    @Override
    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getHashedPassword()));
        //System.out.println(authentication.getCredentials());
        if (authentication.isAuthenticated()) {
            return jwtServiceUseCase.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }

    @Override
    public User getUserById(Integer userId) {
        User user = userRepositoryPort.findById(userId);
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado", null);
        }
        return user;
    }
}

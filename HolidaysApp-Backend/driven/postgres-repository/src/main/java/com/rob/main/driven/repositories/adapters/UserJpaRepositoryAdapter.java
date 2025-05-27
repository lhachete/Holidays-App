package com.rob.main.driven.repositories.adapters;

import com.rob.application.ports.driven.UserRepositoryPort;
import com.rob.domain.models.Role;
import com.rob.domain.models.User;
import com.rob.main.driven.repositories.UserMOJpaRepository;
import com.rob.main.driven.repositories.mappers.UserMOMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Arrays.stream;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserJpaRepositoryAdapter implements UserRepositoryPort {

    private final UserMOJpaRepository userMOJpaRepository;
    private final UserMOMapper userMOMapper;


    @Override
    public List<User> findByNameContaining(String name) {
        log.info("Se van a buscar usuarios que contengan el nombre: {}", name);
        return userMOJpaRepository.findByUsernameContaining(name)
                .stream()
                .map(userMOMapper::toUser)
                .toList();
    }

    @Override
    public List<User> findAll() {
        log.info("Se van a obtener todos los usuarios");
        return userMOJpaRepository.findAll()
                .stream()
                .map(userMOMapper::toUser)
                .toList();
    }

    @Override
    public User findByUsernameOrEmailAndHashedPassword(String usernameOrEmail, String password) {
        log.info("Se va a buscar un usuario por nombre de usuario o email y contraseña");
        return userMOMapper.toUser(userMOJpaRepository.findByUsernameOrEmailAndHashedPassword(usernameOrEmail,usernameOrEmail,password));
    }

    @Override
    public User findByUsername(String username) {
        log.info("Se va a buscar un usuario por nombre de usuario: {}", username);
        return userMOMapper.toUser(userMOJpaRepository.findByUsername(username));
    }

    @Override
    public User findByEmail(String email) {
        log.info("Se va a buscar un usuario por email: {}", email);
        return userMOMapper.toUser(userMOJpaRepository.findByEmail(email));
    }

    @Override
    public User save(User user) {
        log.info("Se va a guardar un usuario: {}", user.getUsername());
        return userMOMapper.toUser(userMOJpaRepository.save(userMOMapper.toUserMO(user)));
    }

    @Override
    public User findById(Integer userId) {
        log.info("Se va a buscar un usuario por ID: {}", userId);
        return userMOMapper.toUser(userMOJpaRepository.findById(userId).get());
    }

    @Override
    public User findByUsernameOrEmail(String username, String email) {
        log.info("Se va a buscar un usuario por nombre de usuario o email: {}, {}", username, email);
        return userMOMapper.toUser(userMOJpaRepository.findByUsernameOrEmail(username, email).get());
    }
}

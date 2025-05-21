package com.rob.main.driven.repositories.adapters;

import com.rob.application.ports.driven.UserRepositoryPort;
import com.rob.domain.models.Role;
import com.rob.domain.models.User;
import com.rob.main.driven.repositories.UserMOJpaRepository;
import com.rob.main.driven.repositories.mappers.UserMOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Arrays.stream;

@Repository
@RequiredArgsConstructor
public class UserJpaRepositoryAdapter implements UserRepositoryPort {

    private final UserMOJpaRepository userMOJpaRepository;
    private final UserMOMapper userMOMapper;


    @Override
    public List<User> findByNameContaining(String name) {
        return userMOJpaRepository.findByUsernameContaining(name)
                .stream()
                .map(userMOMapper::toUser)
                .toList();
    }

    @Override
    public List<User> findAll() {
        return userMOJpaRepository.findAll()
                .stream()
                .map(userMOMapper::toUser)
                .toList();
    }

    @Override
    public User findByUsernameOrEmailAndPassword(String usernameOrEmail, String password) {
        return userMOMapper.toUser(userMOJpaRepository.findByUsernameOrEmailAndPassword(usernameOrEmail,usernameOrEmail,password));
    }

    @Override
    public User findByUsername(String username) {
        return userMOMapper.toUser(userMOJpaRepository.findByUsername(username));
    }

    @Override
    public User findByEmail(String email) {
        return userMOMapper.toUser(userMOJpaRepository.findByEmail(email));
    }

    @Override
    public User save(User user) {
        return userMOMapper.toUser(userMOJpaRepository.save(userMOMapper.toUserMO(user)));
    }

    @Override
    public User findById(Integer userId) {
        return userMOMapper.toUser(userMOJpaRepository.findById(userId).get());
    }
}

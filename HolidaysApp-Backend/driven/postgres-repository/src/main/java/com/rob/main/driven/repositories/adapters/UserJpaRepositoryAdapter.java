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
    public User findByUsernameAndPassword(String username, String password) {
        return userMOMapper.toUser(userMOJpaRepository.findByUsernameAndPassword(username, password));
    }
}

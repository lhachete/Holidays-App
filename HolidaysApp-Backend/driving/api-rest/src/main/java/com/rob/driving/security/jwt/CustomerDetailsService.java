package com.rob.driving.security.jwt;

import com.rob.driving.dtos.UserDTO;
import com.rob.driving.mappers.UserDTOMapper;
import com.rob.main.driven.repositories.UserMOJpaRepository;
import com.rob.main.driven.repositories.mappers.UserMOMapper;
import com.rob.main.driven.repositories.models.UserMO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {

    private final UserMOJpaRepository userMOJpaRepository;
    private UserMO userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        userDetail = userMOJpaRepository.findByEmail(username);

        if (userDetail == null) {
            log.error("User not found with username: {}", username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        log.info("User found: {}", userDetail);
        return new User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
    }

    public UserMO getUserDetail() {
        return userDetail;
    }
}

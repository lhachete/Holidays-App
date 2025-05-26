package com.rob.application.services;

import com.rob.application.ports.driven.UserRepositoryPort;
import com.rob.application.ports.driving.MyUserDetailsServicePort;
import com.rob.domain.models.User;
import com.rob.domain.models.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsServiceUseCase implements UserDetailsService, MyUserDetailsServicePort {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepositoryPort.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (user == null) {
            throw new UsernameNotFoundException("Creedenciales inválidas");
        }
        UserPrincipal userPrincipal = new UserPrincipal(user);
        return userPrincipal;
    }
}

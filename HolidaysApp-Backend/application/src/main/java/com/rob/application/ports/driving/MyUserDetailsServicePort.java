package com.rob.application.ports.driving;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface MyUserDetailsServicePort {

    UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException;
}

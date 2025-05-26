package com.rob.application.ports.driving;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTServicePort {

    public String generateToken(String username);
    public String extractUserName(String token);
    public boolean validateToken(String token, UserDetails userDetails);
}

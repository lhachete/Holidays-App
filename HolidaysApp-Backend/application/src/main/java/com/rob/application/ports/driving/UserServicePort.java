package com.rob.application.ports.driving;

import com.rob.domain.models.User;

import java.util.List;

public interface UserServicePort {
    List<User> getUsersByUsername(String username);
}

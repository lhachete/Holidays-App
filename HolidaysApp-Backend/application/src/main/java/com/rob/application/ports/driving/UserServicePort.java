package com.rob.application.ports.driving;

import com.rob.domain.models.User;

import java.util.List;

public interface UserServicePort {
    List<User> getUsersByUsername(String username);
    User getUserByUsernameOrEmailAndHashedPassword(String usernameOrEmail, String password);
    boolean usernameExists(String username);
    boolean emailExists(String email);
    User createUser(User user);
    String verify(User user);
    User getUserById(Integer userId);
}

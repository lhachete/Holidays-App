package com.rob.domain.models.dtos;

import com.rob.domain.models.entities.User;

public class UserDTO {
    private String username;
    private String password;
    private boolean enabled;
    private String profile;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.profile = user.getProfilePicture();
    }
}

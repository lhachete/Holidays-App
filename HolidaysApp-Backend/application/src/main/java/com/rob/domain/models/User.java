package com.rob.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long userId;
    private Role role;
    private String username;
    private String password;
    private String profilePicture;
    private boolean enabled;
}

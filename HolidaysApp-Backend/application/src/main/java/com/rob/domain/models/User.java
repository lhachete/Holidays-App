package com.rob.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private Employee employee;
    private String codeColor;
    private Role role;
    private String username;
    private String email;
    private String hashedPassword;
    private Boolean enabled;
}

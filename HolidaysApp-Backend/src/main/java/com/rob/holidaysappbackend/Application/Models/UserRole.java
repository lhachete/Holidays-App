package com.rob.holidaysappbackend.Application.Models;

import com.rob.holidaysappbackend.Application.Models.Enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    @Id
    private long id;

    @Enumerated(EnumType.STRING)
    private Role role;
}

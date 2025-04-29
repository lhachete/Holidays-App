package com.rob.models;

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
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank
    @Min(value = 0)
    @Max(value = 99999)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    @NotNull
    private Role role;

    @Column(unique = true, nullable = false)
    @NotBlank
    @Size(min = 3, max = 100)
    private String username;

    @Column(name = "password",nullable = false)
    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    @Column(name = "profile", unique = true, nullable = true)
    private String profilePicture;

    @Column(name = "enabled", nullable = false)
    @NotNull
    private boolean enabled;
}

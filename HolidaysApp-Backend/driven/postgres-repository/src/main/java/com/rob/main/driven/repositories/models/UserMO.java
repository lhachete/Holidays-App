package com.rob.main.driven.repositories.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserMO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('users_user_id_seq')")
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "rol_id", nullable = false)
    private RoleMO rol;

    @Size(max = 255)
    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "hash_password", nullable = false)
    private String hashedPassword;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

}
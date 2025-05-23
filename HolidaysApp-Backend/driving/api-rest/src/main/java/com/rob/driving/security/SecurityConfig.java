package com.rob.driving.security;

import com.rob.application.ports.driving.UserServicePort;
import com.rob.main.driven.repositories.UserMOJpaRepository;
import com.rob.main.driven.repositories.models.UserMO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity // -> Esto le dice a Spring que la seguridad la configure a través de esta clase y no de forma predeterminada
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserMOJpaRepository userMOJpaRepository;

    // Cuando implementas Seguridad de SB, esta tiene muchols filtros, con este bean tu le dices lo que tiene que hacer el filtro.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorizeRequests) ->
                    authorizeRequests
                            .requestMatchers("/api-rest/users/login", "/api-rest/users/register").permitAll() // Permite acceso sin autenticación a las rutas de login y registro
                            .anyRequest().hasRole("ADMIN")
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura la seguridad para que no se cree ni use sesión HTTP; cada solicitud debe autenticarse de forma independiente (modo sin estado)
            .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF (Cross-Site Request Forgery)
            .httpBasic(Customizer.withDefaults()); // Activa autenticación HTTP Basic (usuario/contraseña) usando los valores predeterminados de Spring Security
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return usernameOrEmail -> {
            Optional<UserMO> user = userMOJpaRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
            if (user.isEmpty()) {
                throw new RuntimeException("User not found");
            }
            return org.springframework.security.core.userdetails.User.withUsername(user.get().getUsername())
                    .password(user.get().getPassword())
                    .authorities(user.get().getRol().getName())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(null);
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        return new AuthenticationProvider(authenticationManagerBuilder.getObject());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

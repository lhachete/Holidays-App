package com.rob.application.services;

import com.rob.application.ports.driven.UserRepositoryPort;
import com.rob.application.ports.driving.UserServicePort;
import com.rob.domain.models.User;
import com.rob.domain.models.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUseCase implements UserServicePort {

    private final UserRepositoryPort userRepositoryPort;
    private final JWTServiceUseCase jwtServiceUseCase;
    private final AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);

    @Override
    public List<User> getUsersByUsername(String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        User currentUser = principal.getUser();
        boolean isAdmin = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ADMIN"));
        if(!isAdmin && username == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El usuario no tiene permisos para ver todos los usuarios.");
        else if(!isAdmin && !username.equals(currentUser.getUsername()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"El usuario no tiene permisos para ver los usuarios que no son el mismo.");
        else if(username == null || username.isEmpty())
            return userRepositoryPort.findAll();
        return userRepositoryPort.findByUsernameContaining(username);
    }

    @Override
    public User getUserByUsernameOrEmailAndHashedPassword(String usernameOrEmail, String password) {
        User user = userRepositoryPort.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if(user == null || !encoder.matches(password, user.getHashedPassword()))
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username/email o contraseñas invalidas", null);
        return user;
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepositoryPort.findByUsername(username) != null;
    }

    @Override
    public boolean emailExists(String email) {
        return userRepositoryPort.findByEmail(email) != null;
    }

    @Override
    public boolean colorCodeExists(String colorCode) {
        return userRepositoryPort.findByCodeColor(colorCode);
    }

    @Override
    public User createUser(User user) {
        if(!usernameExists(user.getUsername()) && !emailExists(user.getEmail())) {
            user.setHashedPassword(encoder.encode(user.getHashedPassword()));
            user.setEnabled(true);
            return userRepositoryPort.save(user);
        }
        return null;
    }

    @Override
    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getHashedPassword()));
        if (authentication.isAuthenticated())
            return jwtServiceUseCase.generateToken(user.getUsername());
        else
            return "fail";
    }

    @Override
    public User getUserById(Integer userId) {
        User user = userRepositoryPort.findById(userId);
        if(user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado", null);
        return user;
    }

    @Override
    public List<User> findByFullName(String nameAndLastName) {
        if(nameAndLastName == null || nameAndLastName.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre y apellido no pueden estar vacíos");
        List<User> users = userRepositoryPort.findByFullName(nameAndLastName);
        if(users.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron usuarios con el nombre y apellido proporcionados");
        return users;
    }
}

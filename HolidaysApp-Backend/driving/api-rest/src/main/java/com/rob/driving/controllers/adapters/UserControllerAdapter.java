package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.RoleServicePort;
import com.rob.application.ports.driving.UserServicePort;
import com.rob.domain.models.User;
import com.rob.driving.api.UsersApi;
import com.rob.driving.controllers.adapters.error.ErrorResponse;
import com.rob.driving.dtos.LoginRequest;
import com.rob.driving.dtos.LoginResponse;
import com.rob.driving.dtos.RegisterRequest;
import com.rob.driving.dtos.UserDTO;
import com.rob.driving.mappers.RoleDTOMapper;
import com.rob.driving.mappers.UserDTOMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api-rest/users")
@RequiredArgsConstructor
public class UserControllerAdapter implements UsersApi {

    private final UserServicePort userServicePort;
    private final RoleServicePort roleServicePort;
    private final UserDTOMapper userDTOMapper;
    private final RoleDTOMapper roleDTOMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(value = "username", required = false) String username) {
            List<User> users = userServicePort.getUsersByUsername(username);
            return ResponseEntity.ok(userServicePort.getUsersByUsername(username)
                    .stream()
                    .map(userDTOMapper::toUserDTO)
                    .toList());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> getUserByUsernameOrEmailAndPassword(@RequestBody LoginRequest loginRequest) {
        //System.out.println(encoder.encode(loginRequest.getPassword()));
        LoginResponse loginResponse = userDTOMapper.toLoginResponse(userServicePort.getUserByUsernameOrEmailAndHashedPassword(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
        loginRequest.setUsernameOrEmail(loginResponse.getUsername());
        String token = userServicePort.verify(userDTOMapper.toUser(loginRequest));
        loginResponse.setToken(token);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerByUsernameEmailAndPassword(@Valid @RequestBody RegisterRequest registerRequest) {
        registerRequest.setRole(roleDTOMapper.toRoleDTO(roleServicePort.getRolesByName("ADMIN").get(0)));
        if(!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las contraseñas no coinciden");
        }
        return ResponseEntity.ok(userDTOMapper.toUserDTO(userServicePort.createUser(userDTOMapper.toUser(registerRequest))));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId) {
        return ResponseEntity.ok(userDTOMapper.toUserDTO(userServicePort.getUserById(userId)));
    }
}

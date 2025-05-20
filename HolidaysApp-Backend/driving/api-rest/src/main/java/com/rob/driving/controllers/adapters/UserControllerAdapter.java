package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.UserServicePort;
import com.rob.domain.models.User;
import com.rob.driving.api.UsersApi;
import com.rob.driving.dtos.LoginRequest;
import com.rob.driving.dtos.RegisterRequest;
import com.rob.driving.dtos.UserDTO;
import com.rob.driving.mappers.UserDTOMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api-rest/users")
@RequiredArgsConstructor
public class UserControllerAdapter implements UsersApi {

    private final UserServicePort userServicePort;
    private final UserDTOMapper userDTOMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(value = "username", required = false) String username) {
            List<User> users = userServicePort.getUsersByUsername(username);
            return ResponseEntity.ok(userServicePort.getUsersByUsername(username)
                    .stream()
                    .map(userDTOMapper::toUserDTO)
                    .toList());
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> getUserByUsernameOrEmailAndPassword(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userDTOMapper.toUserDTO(userServicePort.getUserByUsernameOrEmailAndPassword(loginRequest.getUsernameOrEmail(), loginRequest.getPassword())));
    }

//    @PostMapping("/register")
//    public ResponseEntity<UserDTO> registerByUsernameEmailAndPassword(@Valid @RequestBody RegisterRequest registerRequest) {
//        if(registerRequest.getPassword().equals(registerRequest.getRepeatPassword())) {
//            return ResponseEntity.badRequest().build();
//        }
//        // hacer funcion para buscar por email y otra por username!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        if(userServicePort.getUserByUsernameOrEmail(registerRequest.getUsername(), registerRequest.getEmail()).isPresent()) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
}

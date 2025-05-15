package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.UserServicePort;
import com.rob.domain.models.User;
import com.rob.driving.api.UsersApi;
import com.rob.driving.dtos.LoginRequest;
import com.rob.driving.dtos.UserDTO;
import com.rob.driving.mappers.UserDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-rest/users")
@RequiredArgsConstructor
public class UserControllerAdapter implements UsersApi {

    private final UserServicePort userServicePort;
    private final UserDTOMapper userDTOMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(value = "username", required = false) String username) {
        try {
            List<User> users = userServicePort.getUsersByUsername(username);
            return ResponseEntity.ok(userServicePort.getUsersByUsername(username)
                    .stream()
                    .map(userDTOMapper::toUserDTO)
                    .toList());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> getUserByUsernameAndPassword(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userDTOMapper.toUserDTO(userServicePort.getUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())));
    }
}

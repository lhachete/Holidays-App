package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.EmployeeServicePort;
import com.rob.application.ports.driving.RoleServicePort;
import com.rob.application.ports.driving.UserServicePort;
import com.rob.domain.models.Employee;
import com.rob.domain.models.User;
import com.rob.driving.api.UsersApi;
import com.rob.driving.controllers.adapters.error.ErrorResponse;
import com.rob.driving.dtos.*;
import com.rob.driving.mappers.EmployeeDTOMapper;
import com.rob.driving.mappers.RoleDTOMapper;
import com.rob.driving.mappers.UserDTOMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api-rest/users")
@RequiredArgsConstructor
public class UserControllerAdapter implements UsersApi {

    private final EmployeeServicePort employeeServicePort;
    private final UserServicePort userServicePort;
    private final RoleServicePort roleServicePort;
    private final UserDTOMapper userDTOMapper;
    private final RoleDTOMapper roleDTOMapper;
    private final EmployeeDTOMapper employeeDTOMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(value = "username", required = false) String username) {
        log.info("Solicitud GET /users recibida{}", username != null ? " con username=" + username : " sin username");
        List<User> users = userServicePort.getUsersByUsername(username);
        log.debug("Se encontraron {} usuarios{}", users.size(), username != null ? " con username=" + username : "");
        return ResponseEntity.ok(userServicePort.getUsersByUsername(username)
                .stream()
                .map(userDTOMapper::toUserDTO)
                .toList());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> getUserByUsernameOrEmailAndPassword(@RequestBody LoginRequest loginRequest) {
        log.info("Solicitud POST /users/login recibida con datos: {}", loginRequest);
        LoginResponse loginResponse = userDTOMapper.toLoginResponse(userServicePort.getUserByUsernameOrEmailAndHashedPassword(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
        loginRequest.setUsernameOrEmail(loginResponse.getUsername());
        String token = userServicePort.verify(userDTOMapper.toUser(loginRequest));
        loginResponse.setToken(token);
        log.info("Se ha generado un token para el usuario: {} con token: {}", loginResponse.getUsername(), token);
        return ResponseEntity.ok(loginResponse);
    }

    // Registra un usuario y a la vez un empleado.
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerByUsernameEmailAndPassword(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("Solicitud POST /users/register recibida con datos: {}", registerRequest);
        registerRequest.setRole(roleDTOMapper.toRoleDTO(roleServicePort.getRolesByName("USUARIO").get(0)));
        if(!registerRequest.getPassword().equals(registerRequest.getRepeatPassword()))
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las contraseñas no coinciden");
        if(userServicePort.usernameExists(registerRequest.getUsername())) {
            log.error("El nombre de usuario {} ya está en uso", registerRequest.getUsername());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre de usuario ya en uso");
        }
        if (userServicePort.emailExists(registerRequest.getEmail())) {
            log.error("El email {} ya está en uso", registerRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email ya en uso");
        }
        log.info("Registrando usuario con username: {} y email: {}", registerRequest.getUsername(), registerRequest.getEmail());
        Employee employee = Employee.builder()
                .firstName(registerRequest.getName())
                .lastName(registerRequest.getLastName())
                .build();
        Employee newEmployee = employeeServicePort.createEmployee(employee);
        EmployeeDTO newEmployeeDTO = employeeDTOMapper.toEmployeeDTO(newEmployee);
        registerRequest.setEmployee(newEmployeeDTO);
        return ResponseEntity.ok(userDTOMapper.toUserDTO(userServicePort.createUser(userDTOMapper.toUser(registerRequest))));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId) {
        log.info("Solicitud GET /users/{} recibida", userId);
        return ResponseEntity.ok(userDTOMapper.toUserDTO(userServicePort.getUserById(userId)));
    }
}

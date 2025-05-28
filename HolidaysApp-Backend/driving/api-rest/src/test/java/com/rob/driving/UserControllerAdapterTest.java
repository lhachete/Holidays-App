package com.rob.driving;

import com.rob.application.ports.driving.RoleServicePort;
import com.rob.application.ports.driving.UserServicePort;
import com.rob.domain.models.Role;
import com.rob.domain.models.User;
import com.rob.driving.controllers.adapters.UserControllerAdapter;
import com.rob.driving.dtos.*;
import com.rob.driving.mappers.RoleDTOMapperImpl;
import com.rob.driving.mappers.UserDTOMapper;
import com.rob.driving.mappers.UserDTOMapperImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

public class UserControllerAdapterTest {

    @Mock
    private UserServicePort userService;

    @Mock
    private RoleServicePort roleService;

    @Mock
    private UserDTOMapperImpl userMapper;

    @Mock
    private RoleDTOMapperImpl roleMapper;

    @Mock
    private UserServicePort userRepositoryPort;

    @InjectMocks
    private UserControllerAdapter userController;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

//    @Test
//    @DisplayName("Should return all users without filtering by username")
//    void shouldReturnAllUsersWityhoutName() {
//        User user = User.builder().id(1).username("username").email("email@gmail.com").role(Role.builder().id(1).name("USER").build()).enabled(true).hashedPassword("hashedPassword").build();
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(user.getId());
//        userDTO.setUsername(user.getUsername());
//        userDTO.setEmail(user.getEmail());
//        RoleDTO roleDTO = new RoleDTO();
//        roleDTO.setId(user.getRole().getId());
//        roleDTO.setName(user.getRole().getName());
//        userDTO.setRole(roleDTO);
//        userDTO.setEnabled(user.getEnabled());
//        userDTO.setHashedPassword(user.getHashedPassword());
//        List<User> usersList = List.of(user);
//        List<UserDTO> expectedDTOs = List.of(userDTO);
//
//        given(userService.getUsersByUsername(null)).willReturn(usersList);
//        given(userMapper.toUserDTO(user)).willReturn(userDTO);
//
//        ResponseEntity<List<UserDTO>> response = userController.getAllUsers(null);
//
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(expectedDTOs, response.getBody());
//    }
//
//    @Test
//    @DisplayName("Should return users filtered by username")
//    void shouldReturnUsersFilteredByUsername() {
//        String filter = "username";
//        User user = User.builder().id(1).username(filter).email("email@gmail.com").role(Role.builder().id(1).name("USER").build()).enabled(true).hashedPassword("hashedPassword").build();
//
//        RoleDTO roleDTO = new RoleDTO();
//        roleDTO.setId(user.getRole().getId());
//        roleDTO.setName(user.getRole().getName());
//
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(user.getId());
//        userDTO.setUsername(user.getUsername());
//        userDTO.setEmail(user.getEmail());
//        userDTO.setRole(roleDTO);
//        userDTO.setEnabled(user.getEnabled());
//        userDTO.setHashedPassword(user.getHashedPassword());
//
//        List<User> usersList = List.of(user);
//        List<UserDTO> expectedDTOs = List.of(userDTO);
//
//        given(userService.getUsersByUsername(filter)).willReturn(usersList);
//        given(userMapper.toUserDTO(user)).willReturn(userDTO);
//
//        ResponseEntity<List<UserDTO>> response = userController.getAllUsers(filter);
//
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(expectedDTOs, response.getBody());
//    }
//
//    @Test
//    @DisplayName("Should return an empty list when username is not found")
//    void shouldReturnEmptyListWhenUsernameNotFound() {
//        String username = "nonexistent";
//        List<User> emptyUserList = new ArrayList<>();
//        List<UserDTO> emptyDTOList = new ArrayList<>();
//
//        given(userService.getUsersByUsername(username)).willReturn(emptyUserList);
//
//        ResponseEntity<List<UserDTO>> response = userController.getAllUsers(username);
//
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertTrue(response.getBody().isEmpty());
//        assertEquals(emptyDTOList, response.getBody());
//    }
//
//    @Test
//    @DisplayName("Should return a LoginResponse with a token when login is successful")
//    void shouldReturnLoginResponseByUsernameOk() {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsernameOrEmail("username");
//        loginRequest.setPassword("password");
//
//        User user = User.builder()
//                .id(1)
//                .username("username")
//                .email("email@gmail.com")
//                .role(Role.builder().id(1).name("USER").build())
//                .enabled(true)
//                .hashedPassword("hashedPassword")
//                .build();
//
//        RoleDTO roleDTO = new RoleDTO();
//        roleDTO.setId(user.getRole().getId());
//        roleDTO.setName(user.getRole().getName());
//
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setUserId(user.getId());
//        loginResponse.setUsername(user.getUsername());
//        loginResponse.setEmail(user.getEmail());
//        loginResponse.setRol(roleDTO);
//
//        String token = "mocked-jwt-token";
//        loginResponse.setToken(token);
//
//        given(userService.getUserByUsernameOrEmailAndHashedPassword(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()))
//                .willReturn(user);
//
//        given(userMapper.toLoginResponse(user)).willReturn(loginResponse);
//
//        given(userService.verify(any(User.class))).willReturn(token);
//
//        ResponseEntity<LoginResponse> response = userController.getUserByUsernameOrEmailAndPassword(loginRequest);
//        response.getBody().setToken(token); // Aseguramos que el token esté en la respuesta
//
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        LoginResponse resultBody = response.getBody();
//        assertNotNull(resultBody);
//        assertEquals(loginResponse.getUsername(), resultBody.getUsername());
//        assertEquals(token, resultBody.getToken()); // ahora sí está correctamente puesto
//    }
//
//    @Test
//    @DisplayName("Should return a LoginResponse with a token when login is successful")
//    void shouldReturnLoginResponseByEmailOk() {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsernameOrEmail("email@gmail.com");
//        loginRequest.setPassword("password");
//
//        User user = User.builder()
//                .id(1)
//                .username("username")
//                .email("email@gmail.com")
//                .role(Role.builder().id(1).name("USER").build())
//                .enabled(true)
//                .hashedPassword("hashedPassword")
//                .build();
//
//        RoleDTO roleDTO = new RoleDTO();
//        roleDTO.setId(user.getRole().getId());
//        roleDTO.setName(user.getRole().getName());
//
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setUserId(user.getId());
//        loginResponse.setUsername(user.getUsername());
//        loginResponse.setEmail(user.getEmail());
//        loginResponse.setRol(roleDTO);
//
//        String token = "mocked-jwt-token";
//        loginResponse.setToken(token);
//
//        given(userService.getUserByUsernameOrEmailAndHashedPassword(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()))
//                .willReturn(user);
//
//        given(userMapper.toLoginResponse(user)).willReturn(loginResponse);
//
//        given(userService.verify(any(User.class))).willReturn(token);
//
//        ResponseEntity<LoginResponse> response = userController.getUserByUsernameOrEmailAndPassword(loginRequest);
//        response.getBody().setToken(token);
//
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        LoginResponse resultBody = response.getBody();
//        assertNotNull(resultBody);
//        assertEquals(loginResponse.getUsername(), resultBody.getUsername());
//        assertEquals(token, resultBody.getToken());
//    }
//
//    @Test
//    @DisplayName("Debería retornar 401 Unauthorized cuando el email o la contraseña son inválidos")
//    void shouldReturnUnauthorizedWhenEmailOrPasswordInvalid() {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsernameOrEmail("wrongemail@gmail.com");
//        loginRequest.setPassword("wrongemail");
//
//        given(userService.getUserByUsernameOrEmailAndHashedPassword(
//                loginRequest.getUsernameOrEmail(), loginRequest.getPassword()))
//                .willThrow(new RuntimeException("Username/email o contraseñas inválidas"));
//
//        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
//                userController.getUserByUsernameOrEmailAndPassword(loginRequest)
//        );
//        assertEquals("Username/email o contraseñas inválidas", thrown.getMessage());
//    }
//
//    @Test
//    @DisplayName("Debería retornar 401 Unauthorized cuando el email o la contraseña son inválidos")
//    void shouldReturnUnauthorizedWhenUsernameOrPasswordInvalid() {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsernameOrEmail("wrongusername");
//        loginRequest.setPassword("wrongemail");
//
//        given(userService.getUserByUsernameOrEmailAndHashedPassword(
//                loginRequest.getUsernameOrEmail(), loginRequest.getPassword()))
//                .willThrow(new RuntimeException("Username/email o contraseñas inválidas"));
//
//        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
//                userController.getUserByUsernameOrEmailAndPassword(loginRequest)
//        );
//        assertEquals("Username/email o contraseñas inválidas", thrown.getMessage());
//    }
//
//    @Test
//    @DisplayName("Debería lanzar una excepción cuando el email o la contraseña son nulos")
//    void shouldThrowExceptionWhenUsernameOrEmailIsNull() {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsernameOrEmail(null);
//        loginRequest.setPassword("somePassword");
//
//        given(userService.getUserByUsernameOrEmailAndHashedPassword(
//                isNull(), eq("somePassword")))
//                .willThrow(new IllegalArgumentException("Username/email no puede ser nulo"));
//
//        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
//                userController.getUserByUsernameOrEmailAndPassword(loginRequest)
//        );
//
//        assertEquals("Username/email no puede ser nulo", ex.getMessage());
//    }
//
//    @Test
//    @DisplayName("Debería lanzar una excepción cuando la contraseña es nula")
//    void shouldThrowExceptionWhenPasswordIsNull() {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsernameOrEmail("username");
//        loginRequest.setPassword(null);
//
//        given(userService.getUserByUsernameOrEmailAndHashedPassword(
//                eq("username"), isNull()))
//                .willThrow(new IllegalArgumentException("La contraseña no puede ser nula"));
//
//        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
//                userController.getUserByUsernameOrEmailAndPassword(loginRequest)
//        );
//
//        assertEquals("La contraseña no puede ser nula", ex.getMessage());
//    }
//
//    @Test
//    @DisplayName("Debería lanzar una excepción cuando el email o la contraseña están vacíos")
//    void shouldFailValidationWhenUsernameTooShort() {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsernameOrEmail("ab");
//        loginRequest.setPassword("validPassword");
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//
//        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
//
//        assertFalse(violations.isEmpty());
//
//        Optional<ConstraintViolation<LoginRequest>> usernameViolation = violations.stream()
//                .filter(v -> v.getPropertyPath().toString().equals("usernameOrEmail"))
//                .findFirst();
//
//        assertTrue(usernameViolation.isPresent());
//        assertEquals("el tamaño debe estar entre 3 y 30", usernameViolation.get().getMessage());
//    }
//
//    @Test
//    @DisplayName("Debería fallar la validación cuando el nombre de usuario es demasiado largo")
//    void shouldFailValidationWhenUsernameTooLong() {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsernameOrEmail("este_correo_tiene_mas_de_30_caracteres@example.com");
//        loginRequest.setPassword("validPassword");
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//
//        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
//
//        assertFalse(violations.isEmpty());
//
//        Optional<ConstraintViolation<LoginRequest>> usernameViolation = violations.stream()
//                .filter(v -> v.getPropertyPath().toString().equals("usernameOrEmail"))
//                .findFirst();
//
//        assertTrue(usernameViolation.isPresent());
//        assertEquals("el tamaño debe estar entre 3 y 30", usernameViolation.get().getMessage());
//    }
//
//    @Test
//    @DisplayName("Debería fallar la validación cuando la contraseña es demasiado corta")
//    void shouldFailValidationWhenPasswordTooLong() {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsernameOrEmail("validUser");
//        loginRequest.setPassword("Esta_Contraseña_es_muyyyyyyyyyyyyyyyyy_larga123!");
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//
//        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
//
//        assertFalse(violations.isEmpty());
//
//        boolean hasSizeError = violations.stream()
//                .anyMatch(v -> v.getPropertyPath().toString().equals("password") &&
//                        v.getMessage().contains("tamaño debe estar entre"));
//
//        assertTrue(hasSizeError);
//    }
//
//    @Test
//    @DisplayName("Debe registrar un usuario correctamente")
//    void shouldRegisterUserSuccessfully() {
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setUsername("irodes22");
//        registerRequest.setEmail("rtrh2@gft.com");
//        registerRequest.setPassword("Pass1234!");
//        registerRequest.setRepeatPassword("Pass1234!");
//
//        Role role = Role.builder().id(2).name("USUARIO").build();
//        RoleDTO roleDTO = new RoleDTO();
//        roleDTO.setId(role.getId());
//        roleDTO.setName(role.getName());
//        registerRequest.setRole(roleDTO);
//
//        User userToCreate = User.builder()
//                .username(registerRequest.getUsername())
//                .email(registerRequest.getEmail())
//                .hashedPassword("hashedPassword")
//                .role(role)
//                .enabled(true)
//                .build();
//
//        User createdUser = User.builder()
//                .id(6)
//                .username(userToCreate.getUsername())
//                .email(userToCreate.getEmail())
//                .hashedPassword(userToCreate.getHashedPassword())
//                .role(userToCreate.getRole())
//                .enabled(true)
//                .build();
//
//        UserDTO expectedUserDTO = new UserDTO();
//        expectedUserDTO.setId(createdUser.getId());
//        expectedUserDTO.setUsername(createdUser.getUsername());
//        expectedUserDTO.setEmail(createdUser.getEmail());
//        expectedUserDTO.setHashedPassword(createdUser.getHashedPassword());
//
//        RoleDTO responseRoleDTO = new RoleDTO();
//        responseRoleDTO.setId(role.getId());
//        responseRoleDTO.setName(role.getName());
//        expectedUserDTO.setRole(responseRoleDTO);
//        expectedUserDTO.setEnabled(true);
//
//        given(roleService.getRolesByName("USUARIO")).willReturn(List.of(role));
//        given(roleMapper.toRoleDTO(role)).willReturn(roleDTO);
//        given(userMapper.toUser(registerRequest)).willReturn(userToCreate);
//        given(userService.createUser(userToCreate)).willReturn(createdUser);
//        given(userMapper.toUserDTO(createdUser)).willReturn(expectedUserDTO);
//
//        ResponseEntity<UserDTO> response = userController.registerByUsernameEmailAndPassword(registerRequest);
//
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        UserDTO result = response.getBody();
//        assertNotNull(result);
//        assertEquals(expectedUserDTO.getId(), result.getId());
//        assertEquals(expectedUserDTO.getUsername(), result.getUsername());
//        assertEquals(expectedUserDTO.getEmail(), result.getEmail());
//        assertEquals(expectedUserDTO.getRole(), result.getRole());
//        assertEquals(expectedUserDTO.getHashedPassword(), result.getHashedPassword());
//        assertEquals(expectedUserDTO.getEnabled(), result.getEnabled());
//    }
//
//    @Test
//    @DisplayName("Should throw exception when passwords do not match")
//    void shouldThrowExceptionWhenPasswordsDoNotMatch() {
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setUsername("irodes22");
//        registerRequest.setEmail("rtrh2@gft.com");
//        registerRequest.setPassword("Pass1234!");
//        registerRequest.setRepeatPassword("Distinta123!");
//
//        Role role = Role.builder().id(2).name("USUARIO").build();
//        RoleDTO roleDTO = new RoleDTO();
//        roleDTO.setId(role.getId());
//        roleDTO.setName(role.getName());
//
//        given(roleService.getRolesByName("USUARIO")).willReturn(List.of(role));
//        given(roleMapper.toRoleDTO(role)).willReturn(roleDTO);
//
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
//                userController.registerByUsernameEmailAndPassword(registerRequest)
//        );
//
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
//        assertEquals("Las contraseñas no coinciden", exception.getReason());
//    }
//
//    @Test
//    @DisplayName("Debera retornar 401 cuando el nombre de usuario ya existe")
//    void shouldReturn401WhenUsernameExists() {
//        RegisterRequest request = new RegisterRequest();
//        request.setUsername("irodes22");
//        request.setEmail("rtrh2@gft.com");
//        request.setPassword("Pass1234!");
//        request.setRepeatPassword("Pass1234!");
//
//        Role role = Role.builder().id(2).name("USUARIO").build();
//        RoleDTO roleDTO = new RoleDTO();
//        roleDTO.setId(role.getId());
//        roleDTO.setName(role.getName());
//        request.setRole(roleDTO);
//
//        given(roleService.getRolesByName("USUARIO")).willReturn(List.of(role));
//        given(roleMapper.toRoleDTO(role)).willReturn(roleDTO);
//        given(userMapper.toUser(request)).willReturn(User.builder().username("irodes22").email("rtrh2@gft.com").build());
//
//        given(userService.createUser(any(User.class)))
//                .willThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Nombre de usuario ya en uso"));
//
//        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
//                userController.registerByUsernameEmailAndPassword(request)
//        );
//
//        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
//        assertEquals("Nombre de usuario ya en uso", ex.getReason());
//    }
//
//    @Test
//    @DisplayName("Debera retornar 401 cuando el email ya existe")
//    void shouldReturn401WhenEmailExists() {
//        RegisterRequest request = new RegisterRequest();
//        request.setUsername("nuevoUser");
//        request.setEmail("yaexiste@gft.com");
//        request.setPassword("Pass1234!");
//        request.setRepeatPassword("Pass1234!");
//
//        Role role = Role.builder().id(2).name("USUARIO").build();
//        RoleDTO roleDTO = new RoleDTO();
//        roleDTO.setId(role.getId());
//        roleDTO.setName(role.getName());
//        request.setRole(roleDTO);
//
//        given(roleService.getRolesByName("USUARIO")).willReturn(List.of(role));
//        given(roleMapper.toRoleDTO(role)).willReturn(roleDTO);
//        given(userMapper.toUser(request)).willReturn(
//                User.builder().username("nuevoUser").email("yaexiste@gft.com").hashedPassword("Pass1234!").build()
//        );
//
//        given(userService.createUser(any(User.class)))
//                .willThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ya en uso"));
//
//        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
//                userController.registerByUsernameEmailAndPassword(request)
//        );
//
//        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
//        assertEquals("Email ya en uso", ex.getReason());
//    }
}

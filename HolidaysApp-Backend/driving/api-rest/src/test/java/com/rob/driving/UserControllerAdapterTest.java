package com.rob.driving;

import com.rob.application.ports.driving.RoleServicePort;
import com.rob.application.ports.driving.UserServicePort;
import com.rob.domain.models.Role;
import com.rob.domain.models.User;
import com.rob.driving.controllers.adapters.UserControllerAdapter;
import com.rob.driving.dtos.RoleDTO;
import com.rob.driving.dtos.UserDTO;
import com.rob.driving.mappers.RoleDTOMapperImpl;
import com.rob.driving.mappers.UserDTOMapper;
import com.rob.driving.mappers.UserDTOMapperImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    @DisplayName("Should return all users without filtering by username")
    void shouldReturnAllUsersWityhoutName() {
        User user = User.builder().id(1).username("username").email("email@gmail.com").role(Role.builder().id(1).name("USER").build()).enabled(true).hashedPassword("hashedPassword").build();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(user.getRole().getId());
        roleDTO.setName(user.getRole().getName());
        userDTO.setRole(roleDTO);
        userDTO.setEnabled(user.getEnabled());
        userDTO.setHashedPassword(user.getHashedPassword());
        List<User> usersList = List.of(user);
        List<UserDTO> expectedDTOs = List.of(userDTO);

        given(userService.getUsersByUsername(null)).willReturn(usersList);
        given(userMapper.toUserDTO(user)).willReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers(null);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTOs, response.getBody());
    }
}

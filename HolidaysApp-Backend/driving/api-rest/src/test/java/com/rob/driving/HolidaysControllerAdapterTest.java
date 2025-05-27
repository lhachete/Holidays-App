package com.rob.driving;

import com.rob.application.ports.driving.HolidayServicePort;
import com.rob.application.ports.driving.UserServicePort;
import com.rob.domain.models.Holiday;
import com.rob.domain.models.Role;
import com.rob.domain.models.User;
import com.rob.driving.controllers.adapters.HolidayControllerAdapter;
import com.rob.driving.controllers.adapters.UserControllerAdapter;
import com.rob.driving.dtos.*;
import com.rob.driving.mappers.HolidayDTOMapper;
import com.rob.driving.mappers.UserDTOMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

public class HolidaysControllerAdapterTest {

    @Mock
    private HolidayServicePort holidayServicePort;

    @Mock
    private UserServicePort userServicePort;

    @Mock
    private HolidayDTOMapper holidayDTOMapper;

    @Mock
    private UserDTOMapper userDTOMapper;

    @InjectMocks
    private HolidayControllerAdapter holidayControllerAdapter;

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
    @DisplayName("Debería retornar todas las vacaciones sin userId")
    void shouldReturnAllHolidaysWithoutUserId() {
        User user = User.builder()
                .id(1)
                .username("testuser")
                .email("email@gmail.com")
                .hashedPassword("hashedPassword")
                .role(Role.builder().id(1).name("ROLE_USER").build())
                .enabled(true)
                .build();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setUsername("testuser");
        userDTO.setEmail("email@gmail.com");
        userDTO.setHashedPassword("hashedPassword");
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1);
        roleDTO.setName("ROLE_USER");
        userDTO.setRole(roleDTO);
        userDTO.setEnabled(true);
        Holiday holiday = Holiday.builder()
                .id(1)
                .vacationType("Vacaciones de verano")
                .user(user)
                .holidayStartDate(LocalDate.parse("2023-06-01"))
                .holidayEndDate(LocalDate.parse("2023-06-15"))
                .build();

        HolidayDTO holidayDTO = new HolidayDTO();
        holidayDTO.setHolidayId(holiday.getId());
        holidayDTO.setVacationType(holiday.getVacationType());
        holidayDTO.setUser(userDTO);

        List<Holiday> holidays = List.of(holiday);
        List<HolidayDTO> expectedDTOs = List.of(holidayDTO);

        given(holidayServicePort.getHolidaysByUserId(null)).willReturn(holidays);
        given(holidayDTOMapper.toHolidayDTO(holiday)).willReturn(holidayDTO);

        ResponseEntity<List<HolidayDTO>> response = holidayControllerAdapter.getAllHolidays(null);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTOs, response.getBody());
    }

    @Test
    @DisplayName("Debe retornar vacaciones filtradas por userId")
    void shouldReturnHolidaysFilteredByUserId() {
        Integer userId = 1;

        User user = User.builder()
                .id(userId)
                .username("testuser")
                .email("email@gmail.com")
                .hashedPassword("hashedPassword")
                .role(Role.builder().id(1).name("ROLE_USER").build())
                .enabled(true)
                .build();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setHashedPassword(user.getHashedPassword());
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(user.getRole().getId());
        roleDTO.setName(user.getRole().getName());
        userDTO.setRole(roleDTO);
        userDTO.setEnabled(user.getEnabled());

        Holiday holiday = Holiday.builder()
                .id(1)
                .vacationType("Vacaciones filtradas")
                .user(user)
                .holidayStartDate(LocalDate.parse("2023-07-01"))
                .holidayEndDate(LocalDate.parse("2023-07-15"))
                .build();

        HolidayDTO holidayDTO = new HolidayDTO();
        holidayDTO.setHolidayId(holiday.getId());
        holidayDTO.setVacationType(holiday.getVacationType());
        holidayDTO.setUser(userDTO);

        List<Holiday> holidays = List.of(holiday);
        List<HolidayDTO> expectedDTOs = List.of(holidayDTO);

        given(holidayServicePort.getHolidaysByUserId(userId)).willReturn(holidays);
        given(holidayDTOMapper.toHolidayDTO(holiday)).willReturn(holidayDTO);

        ResponseEntity<List<HolidayDTO>> response = holidayControllerAdapter.getAllHolidays(userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTOs, response.getBody());
    }

    @Test
    @DisplayName("Debería agregar una nueva vacación correctamente")
    void shouldAddHolidaySuccessfully() {
        Integer userId = 1;
        HolidayRequestDTO holidayRequest = new HolidayRequestDTO();
        holidayRequest.setUserId(userId);
        holidayRequest.setVacationType("Vacaciones de verano");
        holidayRequest.setHolidayStartDate(LocalDate.parse("2023-08-01"));
        holidayRequest.setHolidayEndDate(LocalDate.parse("2023-08-15"));

        User user = User.builder()
                .id(userId)
                .username("testuser")
                .email("test@example.com")
                .hashedPassword("hash")
                .role(Role.builder().id(1).name("USER").build())
                .enabled(true)
                .build();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setHashedPassword(user.getHashedPassword());
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(user.getRole().getId());
        roleDTO.setName(user.getRole().getName());
        userDTO.setRole(roleDTO);
        userDTO.setEnabled(user.getEnabled());

        HolidayDTO inputHolidayDTO = new HolidayDTO();
        inputHolidayDTO.setVacationType(holidayRequest.getVacationType());
        inputHolidayDTO.setHolidayStartDate(holidayRequest.getHolidayStartDate());
        inputHolidayDTO.setHolidayEndDate(holidayRequest.getHolidayEndDate());

        HolidayDTO finalHolidayDTO = new HolidayDTO();
        finalHolidayDTO.setHolidayId(10);
        finalHolidayDTO.setVacationType(inputHolidayDTO.getVacationType());
        finalHolidayDTO.setHolidayStartDate(inputHolidayDTO.getHolidayStartDate());
        finalHolidayDTO.setHolidayEndDate(inputHolidayDTO.getHolidayEndDate());
        finalHolidayDTO.setUser(userDTO);

        Holiday holiday = Holiday.builder()
                .id(10)
                .vacationType(finalHolidayDTO.getVacationType())
                .holidayStartDate(finalHolidayDTO.getHolidayStartDate())
                .holidayEndDate(finalHolidayDTO.getHolidayEndDate())
                .user(user)
                .build();

        given(userServicePort.getUserById(userId)).willReturn(user);
        given(holidayDTOMapper.holidayRequestDTOtoHolidayDTO(holidayRequest)).willReturn(inputHolidayDTO);
        given(userDTOMapper.toUserDTO(user)).willReturn(userDTO);
        given(holidayDTOMapper.toHoliday(inputHolidayDTO)).willReturn(holiday);
        given(holidayServicePort.addHoliday(holiday)).willReturn(holiday);
        given(holidayDTOMapper.toHolidayDTO(holiday)).willReturn(finalHolidayDTO);

        ResponseEntity<HolidayDTO> response = holidayControllerAdapter.addHoliday(holidayRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HolidayDTO result = response.getBody();
        assertNotNull(result);
        assertEquals(10, result.getHolidayId());
        assertEquals("Vacaciones de verano", result.getVacationType());
        assertEquals(userId, result.getUser().getId());
        assertEquals(LocalDate.parse("2023-08-01"), result.getHolidayStartDate());
        assertEquals(LocalDate.parse("2023-08-15"), result.getHolidayEndDate());
    }

    @Test
    @DisplayName("Debería eliminar una vacación correctamente")
    void shouldDeleteHolidaySuccessfully() {
        Integer holidayId = 10;

        User user = User.builder()
                .id(1)
                .username("testuser")
                .email("test@example.com")
                .hashedPassword("hash")
                .role(Role.builder().id(1).name("USER").build())
                .enabled(true)
                .build();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setHashedPassword(user.getHashedPassword());
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(user.getRole().getId());
        roleDTO.setName(user.getRole().getName());
        userDTO.setRole(roleDTO);
        userDTO.setEnabled(user.getEnabled());

        Holiday deletedHoliday = Holiday.builder()
                .id(holidayId)
                .vacationType("Vacaciones eliminadas")
                .holidayStartDate(LocalDate.of(2023, 8, 1))
                .holidayEndDate(LocalDate.of(2023, 8, 15))
                .user(user)
                .build();

        HolidayDTO deletedHolidayDTO = new HolidayDTO();
        deletedHolidayDTO.setHolidayId(deletedHoliday.getId());
        deletedHolidayDTO.setVacationType(deletedHoliday.getVacationType());
        deletedHolidayDTO.setHolidayStartDate(deletedHoliday.getHolidayStartDate());
        deletedHolidayDTO.setHolidayEndDate(deletedHoliday.getHolidayEndDate());
        deletedHolidayDTO.setUser(userDTO);

        given(holidayServicePort.deleteHolidayById(holidayId)).willReturn(deletedHoliday);
        given(holidayDTOMapper.toHolidayDTO(deletedHoliday)).willReturn(deletedHolidayDTO);

        ResponseEntity<HolidayDTO> response = holidayControllerAdapter.deleteHolidayById(holidayId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HolidayDTO result = response.getBody();
        assertNotNull(result);
        assertEquals(deletedHolidayDTO.getHolidayId(), result.getHolidayId());
        assertEquals(deletedHolidayDTO.getVacationType(), result.getVacationType());
        assertEquals(deletedHolidayDTO.getHolidayStartDate(), result.getHolidayStartDate());
        assertEquals(deletedHolidayDTO.getHolidayEndDate(), result.getHolidayEndDate());
        assertEquals(deletedHolidayDTO.getUser(), result.getUser());
    }

    @Test
    @DisplayName("Debería actualizar una vacación correctamente")
    void shouldUpdateHolidaySuccessfully() {
        HolidayUpdateRequestDTO updateRequest = new HolidayUpdateRequestDTO();
        updateRequest.setHolidayId(10);
        updateRequest.setUserId(1);
        updateRequest.setVacationType("Vacaciones actualizadas");
        updateRequest.setHolidayStartDate(LocalDate.of(2023, 9, 1));
        updateRequest.setHolidayEndDate(LocalDate.of(2023, 9, 15));

        User user = User.builder()
                .id(1)
                .username("testuser")
                .email("test@example.com")
                .hashedPassword("hashed")
                .role(Role.builder().id(1).name("USER").build())
                .enabled(true)
                .build();

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1);
        roleDTO.setName("USER");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setHashedPassword(user.getHashedPassword());
        userDTO.setRole(roleDTO);
        userDTO.setEnabled(true);

        HolidayDTO inputHolidayDTO = new HolidayDTO();
        inputHolidayDTO.setHolidayId(updateRequest.getHolidayId());
        inputHolidayDTO.setVacationType(updateRequest.getVacationType());
        inputHolidayDTO.setHolidayStartDate(updateRequest.getHolidayStartDate());
        inputHolidayDTO.setHolidayEndDate(updateRequest.getHolidayEndDate());

        Holiday holiday = Holiday.builder()
                .id(10)
                .vacationType(updateRequest.getVacationType())
                .holidayStartDate(updateRequest.getHolidayStartDate())
                .holidayEndDate(updateRequest.getHolidayEndDate())
                .user(user)
                .build();

        HolidayDTO updatedHolidayDTO = new HolidayDTO();
        updatedHolidayDTO.setHolidayId(holiday.getId());
        updatedHolidayDTO.setVacationType(holiday.getVacationType());
        updatedHolidayDTO.setHolidayStartDate(holiday.getHolidayStartDate());
        updatedHolidayDTO.setHolidayEndDate(holiday.getHolidayEndDate());
        updatedHolidayDTO.setUser(userDTO);

        given(userServicePort.getUserById(updateRequest.getUserId())).willReturn(user);
        given(holidayDTOMapper.holidayUpdateRequestDTOtoHolidayDTO(updateRequest)).willReturn(inputHolidayDTO);
        given(userDTOMapper.toUserDTO(user)).willReturn(userDTO);
        given(holidayDTOMapper.toHoliday(inputHolidayDTO)).willReturn(holiday);
        given(holidayServicePort.updateHoliday(holiday)).willReturn(holiday);
        given(holidayDTOMapper.toHolidayDTO(holiday)).willReturn(updatedHolidayDTO);

        ResponseEntity<HolidayDTO> response = holidayControllerAdapter.updateHoliday(updateRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HolidayDTO result = response.getBody();
        assertNotNull(result);
        assertEquals(10, result.getHolidayId());
        assertEquals("Vacaciones actualizadas", result.getVacationType());
        assertEquals(LocalDate.of(2023, 9, 1), result.getHolidayStartDate());
        assertEquals(LocalDate.of(2023, 9, 15), result.getHolidayEndDate());
        assertEquals(userDTO, result.getUser());
    }
}

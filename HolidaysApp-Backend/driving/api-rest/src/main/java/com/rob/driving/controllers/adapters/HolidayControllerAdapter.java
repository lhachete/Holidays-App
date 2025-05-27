package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.HolidayServicePort;
import com.rob.application.ports.driving.UserServicePort;
import com.rob.domain.models.User;
import com.rob.driving.api.HolidaysApi;
import com.rob.driving.dtos.HolidayDTO;
import com.rob.driving.dtos.HolidayRequestDTO;
import com.rob.driving.dtos.HolidayUpdateRequestDTO;
import com.rob.driving.mappers.HolidayDTOMapper;
import com.rob.driving.mappers.UserDTOMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api-rest/holidays")
@RequiredArgsConstructor
public class HolidayControllerAdapter implements HolidaysApi {

    private final HolidayServicePort holidayServicePort;
    private final UserServicePort userServicePort;
    private final HolidayDTOMapper holidayDTOMapper;
    private final UserDTOMapper userDTOMapper;

    @GetMapping
    public ResponseEntity<List<HolidayDTO>> getAllHolidays(@RequestParam(value = "userId", required = false) Integer userId) {
        log.info("Solicitud GET /holidays recibida{}", userId != null ? " con userId=" + userId : " sin userId");
        List<HolidayDTO> holidays = holidayServicePort.getHolidaysByUserId(userId).stream()
                .map(holidayDTOMapper::toHolidayDTO)
                .toList();
        log.debug("Se encontraron {} vacaciones{}", holidays.size(), userId != null ? " para userId=" + userId : "");
        return ResponseEntity.ok(holidays);
    }

    @PostMapping
    public ResponseEntity<HolidayDTO> addHoliday(@RequestBody HolidayRequestDTO holidayRequestDTO) {
        log.info("Solicitud POST /holidays recibida con datos: {}", holidayRequestDTO);
        User user = userServicePort.getUserById(holidayRequestDTO.getUserId());
        HolidayDTO newHoliday = holidayDTOMapper.holidayRequestDTOtoHolidayDTO(holidayRequestDTO);
        newHoliday.setUser(userDTOMapper.toUserDTO(user));
        log.debug("Creando nueva vacación para el usuario: {}", user.getUsername());
        return ResponseEntity.ok(holidayDTOMapper.toHolidayDTO(
                holidayServicePort.addHoliday(holidayDTOMapper.toHoliday(newHoliday))
        ));
    }

    @GetMapping("/{holidayId}")
    public ResponseEntity<HolidayDTO> getHolidayById(@PathVariable(name = "holidayId") Integer holidayId) {
        log.info("Solicitud GET /holidays/{} recibida", holidayId);
        HolidayDTO holiday = holidayDTOMapper.toHolidayDTO(holidayServicePort.getHolidayById(holidayId));
        log.debug("Se encontró la vacación: {}", holiday);
        return ResponseEntity.ok(holiday);
    }

    @DeleteMapping("/{holidayId}")
    public ResponseEntity<HolidayDTO> deleteHolidayById(@PathVariable(name = "holidayId") Integer holidayId) {
        log.info("Solicitud DELETE /holidays/{} recibida", holidayId);
        HolidayDTO deletedHolidayDTO = holidayDTOMapper.toHolidayDTO(holidayServicePort.deleteHolidayById(holidayId));
        log.debug("Se eliminó la vacación: {}", deletedHolidayDTO);
        return ResponseEntity.ok(deletedHolidayDTO);
    }

    @PutMapping
    public ResponseEntity<HolidayDTO> updateHoliday(@Valid @RequestBody HolidayUpdateRequestDTO holidayUpdateRequestDTO) {
        log.info("Solicitud PUT /holidays recibida con datos: {}", holidayUpdateRequestDTO);
        User user = userServicePort.getUserById(holidayUpdateRequestDTO.getUserId());
        HolidayDTO newHoliday = holidayDTOMapper.holidayUpdateRequestDTOtoHolidayDTO(holidayUpdateRequestDTO);
        newHoliday.setUser(userDTOMapper.toUserDTO(user));
        log.debug("Actualizando vacación para el usuario: {}", user.getUsername());
        return ResponseEntity.ok(holidayDTOMapper.toHolidayDTO(
                holidayServicePort.updateHoliday(holidayDTOMapper.toHoliday(newHoliday))
        ));
    }
}

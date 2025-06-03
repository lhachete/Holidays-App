package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.HolidayServicePort;
import com.rob.application.ports.driving.HolidaysGeneratorPort;
import com.rob.application.ports.driving.UserServicePort;
import com.rob.application.services.MyUserDetailsServiceUseCase;
import com.rob.domain.models.Holiday;
import com.rob.domain.models.User;
import com.rob.domain.models.UserPrincipal;
import com.rob.driving.api.HolidaysApi;
import com.rob.driving.dtos.*;
import com.rob.driving.mappers.HolidayDTOMapper;
import com.rob.driving.mappers.UserDTOMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api-rest/holidays")
@RequiredArgsConstructor
public class HolidayControllerAdapter implements HolidaysApi {

    private final HolidayServicePort holidayServicePort;
    private final UserServicePort userServicePort;
    private final HolidaysGeneratorPort holidaysGeneratorPort;
    private final HolidayDTOMapper holidayDTOMapper;
    private final UserDTOMapper userDTOMapper;

    @GetMapping
    public ResponseEntity<HolidayCollectionResponse> getAllHolidays(@RequestParam(value = "userId", required = false) Integer userId) {
        log.info("Solicitud GET /holidays recibida{}", userId != null ? " con userId=" + userId : " sin userId");
        HolidayCollectionResponse holidays = holidayDTOMapper.toHolidayCollectionResponse(holidayServicePort.getHolidaysByUserId(userId));
        log.debug("Se encontraron {} vacaciones{}", holidays.getData().size(), userId != null ? " para userId=" + userId : "");
        return ResponseEntity.ok(holidays);
    }

    @PostMapping
    public ResponseEntity<HolidayResponse> addHoliday(@RequestBody HolidayRequestDTO holidayRequestDTO) {
        log.info("Solicitud POST /holidays recibida con datos: {}", holidayRequestDTO);
        User user = userServicePort.getUserById(holidayRequestDTO.getUserId());
        HolidayDTO newHoliday = holidayDTOMapper.holidayRequestDTOtoHolidayDTO(holidayRequestDTO);
        newHoliday.setUser(userDTOMapper.toUserDTO(user));
        log.debug("Creando nueva vacación para el usuario: {}", user.getUsername());
        return ResponseEntity.ok(holidayDTOMapper.toHolidayResponse(
                holidayServicePort.addHoliday(holidayDTOMapper.toHoliday(newHoliday))
        ));
    }

    @GetMapping("/{holidayId}")
    public ResponseEntity<HolidayResponse> getHolidayById(@PathVariable(name = "holidayId") Integer holidayId) {
        log.info("Solicitud GET /holidays/{} recibida", holidayId);
        HolidayResponse holiday = holidayDTOMapper.toHolidayResponse(holidayServicePort.getHolidayById(holidayId));
        log.debug("Se encontró la vacación: {}", holiday);
        return ResponseEntity.ok(holiday);
    }

    @DeleteMapping("/{holidayId}")
    public ResponseEntity<HolidayResponse> deleteHolidayById(@PathVariable(name = "holidayId") Integer holidayId) {
        log.info("Solicitud DELETE /holidays/{} recibida", holidayId);
        HolidayResponse deletedHolidayDTO = holidayDTOMapper.toHolidayResponse(holidayServicePort.deleteHolidayById(holidayId));
        log.debug("Se eliminó la vacación: {}", deletedHolidayDTO);
        return ResponseEntity.ok(deletedHolidayDTO);
    }

    @PutMapping
    public ResponseEntity<HolidayResponse> updateHoliday(@Valid @RequestBody HolidayUpdateRequestDTO holidayUpdateRequestDTO) {
        log.info("Solicitud PUT /holidays recibida con datos: {}", holidayUpdateRequestDTO);
        User user = userServicePort.getUserById(holidayUpdateRequestDTO.getUserId());
        HolidayDTO newHoliday = holidayDTOMapper.holidayUpdateRequestDTOtoHolidayDTO(holidayUpdateRequestDTO);
        newHoliday.setUser(userDTOMapper.toUserDTO(user));
        log.debug("Actualizando vacación para el usuario: {}", user.getUsername());
        return ResponseEntity.ok(holidayDTOMapper.toHolidayResponse(
                holidayServicePort.updateHoliday(holidayDTOMapper.toHoliday(newHoliday))
        ));
    }

    @GetMapping("/report/xlsx")
    public ResponseEntity<org.springframework.core.io.Resource> generateHolidaysReport(@RequestParam(value = "getAll", required = false) boolean getAll,
                                                                                       @RequestParam(value = "userId", required = false) Integer userId) {
        List<Holiday> holidays = holidayServicePort.getAllHolidaysOrByUser(getAll, userId);
        log.info("Solicitud GET /holidays/report/xlsx recibida con getAll={}", getAll);
        org.springframework.core.io.Resource report = holidaysGeneratorPort.exportHolidayReport(holidays);
        log.debug("Reporte de vacaciones generado exitosamente");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=holidays-report.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(report);
    }
}

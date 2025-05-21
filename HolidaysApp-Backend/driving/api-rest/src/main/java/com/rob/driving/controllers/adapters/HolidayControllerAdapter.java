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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<HolidayDTO> holidays = holidayServicePort.getHolidaysByUserId(userId).stream()
                .map(holidayDTOMapper::toHolidayDTO)
                .toList();
        return ResponseEntity.ok(holidays);
    }

    @PostMapping
    public ResponseEntity<HolidayDTO> addHoliday(@RequestBody HolidayRequestDTO holidayRequestDTO) {
        User user = userServicePort.getUserById(holidayRequestDTO.getUserId());
        HolidayDTO newHoliday = holidayDTOMapper.holidayRequestDTOtoHolidayDTO(holidayRequestDTO);
        newHoliday.setUser(userDTOMapper.toUserDTO(user));
        return ResponseEntity.ok(holidayDTOMapper.toHolidayDTO(
                holidayServicePort.addHoliday(holidayDTOMapper.toHoliday(newHoliday))
        ));
    }

    @GetMapping("/{holidayId}")
    public ResponseEntity<HolidayDTO> getHolidayById(@PathVariable(name = "holidayId") Integer holidayId) {
        HolidayDTO holiday = holidayDTOMapper.toHolidayDTO(holidayServicePort.getHolidayById(holidayId));
        return ResponseEntity.ok(holiday);
    }

    @DeleteMapping("/{holidayId}")
    public ResponseEntity<HolidayDTO> deleteHolidayById(@PathVariable(name = "holidayId") Integer holidayId) {
        HolidayDTO deletedHolidayDTO = holidayDTOMapper.toHolidayDTO(holidayServicePort.deleteHolidayById(holidayId));
        return ResponseEntity.ok(deletedHolidayDTO);
    }

//    @PutMapping
//    public ResponseEntity<HolidayDTO> updateHoliday(@Valid @RequestBody HolidayUpdateRequestDTO holidayUpdateRequestDTO) {
//
//    }
}

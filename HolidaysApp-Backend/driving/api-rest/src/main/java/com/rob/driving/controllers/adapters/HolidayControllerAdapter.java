package com.rob.driving.controllers.adapters;

import com.rob.application.ports.driving.HolidayServicePort;
import com.rob.driving.api.HolidaysApi;
import com.rob.driving.dtos.HolidayDTO;
import com.rob.driving.dtos.HolidayRequestDTO;
import com.rob.driving.mappers.HolidayDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-rest/holidays")
@RequiredArgsConstructor
public class HolidayControllerAdapter implements HolidaysApi {

    private final HolidayServicePort holidayServicePort;
    private final HolidayDTOMapper holidayDTOMapper;

    @GetMapping
    public ResponseEntity<List<HolidayDTO>> getAllHolidays(@RequestParam(value = "userId", required = false) Integer userId) {
        List<HolidayDTO> holidays = holidayServicePort.getHolidaysByUserId(userId).stream()
                .map(holidayDTOMapper::toHolidayDTO)
                .toList();
        return ResponseEntity.ok(holidays);
    }

    @PostMapping
    public ResponseEntity<HolidayDTO> addHoliday(@RequestBody HolidayRequestDTO holidayRequestDTO) {
        return ResponseEntity.ok(holidayDTOMapper.toHolidayDTO(
                holidayServicePort.addHoliday(holidayDTOMapper.toHoliday(holidayRequestDTO))
        ));
    }
}

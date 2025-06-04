package com.rob.application.ports.driving;

import com.rob.domain.models.Holiday;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

public interface HolidaysGeneratorPort {

    ByteArrayResource exportHolidayReport(List<Holiday> holidays);
}

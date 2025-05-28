package com.rob.application.services;

import com.rob.application.ports.driven.HolidayRepositoryPort;
import com.rob.application.ports.driving.HolidayServicePort;
import com.rob.domain.models.Holiday;
import com.rob.domain.models.User;
import com.rob.domain.models.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayUseCase implements HolidayServicePort {

    private final HolidayRepositoryPort holidayRepositoryPort;

    @Override
    public List<Holiday> getHolidaysByUserId(Integer userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        User currentUser = principal.getUser();
        boolean isAdmin = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ADMIN"));
        if(!isAdmin && userId == null) {
            throw new IllegalArgumentException("El usuario no tiene permisos para ver todas las vacaciones.");
        }
        if(userId == currentUser.getId())
            return holidayRepositoryPort.findByUserId(userId);
        else if(userId != currentUser.getId())
            throw new IllegalArgumentException("El usuario no tiene permisos para ver las vacaciones de otro usuario.");
        return holidayRepositoryPort.findAllHolidays();
    }

    @Override
    public Holiday addHoliday(Holiday holiday) {
        System.out.println("Holiday: " + holiday);
        if(isValidHoliday(holiday)) {
            holiday.setCreatedAt(OffsetDateTime.now());
            holiday.setCreatedBy(holiday.getUser());
            holiday.setVacationState("aprobada");
            holiday.setIsDeleted(false);
            return holidayRepositoryPort.save(holiday);
        }
        return null;
    }

    @Override
    public Holiday getHolidayById(Integer id) {
        return holidayRepositoryPort.findById(id);
    }

    @Override
    public Holiday deleteHolidayById(Integer holidayId) {
        return holidayRepositoryPort.deleteById(holidayId);
    }

    //meter validacion si al usuario le pertenece la vacacion
    @Override
    public Holiday updateHoliday(Holiday holiday) {
        if(holidayRepositoryPort.findByIdAndUserId(holiday.getId(), holiday.getUser().getId()) == null)
            throw new IllegalArgumentException("La vacacion no pertenece al usuario.");
        if(isValidHoliday(holiday)) {
            Holiday holidayToUpdate = holidayRepositoryPort.findById(holiday.getId());
            holidayToUpdate.setHolidayStartDate(holiday.getHolidayStartDate());
            holidayToUpdate.setHolidayEndDate(holiday.getHolidayEndDate());
            holidayToUpdate.setVacationState("aprobada");
            holidayToUpdate.setUpdatedAt(OffsetDateTime.now());
            holidayToUpdate.setUpdatedBy(holiday.getUser());
            return holidayRepositoryPort.save(holidayToUpdate);
        }
        return null;
    }

    public boolean isValidHoliday(Holiday holiday) {
        if(holiday.getHolidayEndDate().isBefore(holiday.getHolidayStartDate()))
            throw new IllegalArgumentException("La fecha de fin de vacaciones no puede ser anterior a la fecha de inicio.");
        if(holidayRepositoryPort.countOverlappingVacations(holiday.getUser().getId(),
                holiday.getHolidayStartDate(), holiday.getHolidayEndDate(),holiday.getId()) > 0)
            throw new IllegalArgumentException("Las fechas de vacaciones se solapan con otras vacaciones existentes.");
        return true;
    }
}

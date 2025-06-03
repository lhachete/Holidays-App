package com.rob.application.services;

import com.rob.application.ports.driven.HolidayRepositoryPort;
import com.rob.application.ports.driving.HolidayServicePort;
import com.rob.domain.models.Holiday;
import com.rob.domain.models.User;
import com.rob.domain.models.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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
        if(!isAdmin && userId == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El usuario no tiene permisos para ver las vacaciones de todos los usuarios.");
        else if(userId != currentUser.getId() && !isAdmin)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El usuario no tiene permisos para ver las vacaciones de otros usuarios.");
        if(userId != null)
            return holidayRepositoryPort.findByUserId(userId);
        return holidayRepositoryPort.findAllHolidays();
    }

    @Override
    public List<Holiday> getAllHolidaysOrByUser(boolean getAll, Integer userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        User currentUser = principal.getUser();
        boolean isAdmin = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ADMIN"));
        if(!isAdmin && getAll)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El usuario no tiene permisos para ver las vacaciones de todos los usuarios.");
        else if(userId != null && getAll)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El usuario no puede ver las vacaciones de un usuario especifico si getAll es true.");
        else if(!isAdmin && userId != null && userId != currentUser.getId())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El usuario no tiene permisos para ver las vacaciones de otros usuarios.");
        else if(getAll)
            return holidayRepositoryPort.findAllHolidays();
        else if(userId != null)
            return holidayRepositoryPort.findByUserId(userId);
        return holidayRepositoryPort.findByUserId(currentUser.getId());
    }

    // si el usuario que tiene la sision iniciada no es el mismo que el de la vacacion tirar una excepcion
    @Override
    public Holiday addHoliday(Holiday holiday) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        User currentUser = principal.getUser();
        boolean isAdmin = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ADMIN"));
        if(!isAdmin && holiday.getUser().getId() != currentUser.getId()) {
            log.error("El usuario no tiene permisos para añadir vacaciones para otro usuario.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El usuario no tiene permisos para añadir vacaciones para otro usuario.");
        }
        log.info("Se va a añadir la vacación: {}", holiday);
        if(isValidHoliday(holiday, true)) {
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        User currentUser = principal.getUser();
        boolean isAdmin = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ADMIN"));
        Holiday holidayToDelete = holidayRepositoryPort.findById(holidayId);
        if(!isAdmin && currentUser.getId() != holidayToDelete.getUser().getId())
            throw new IllegalArgumentException("La vacacion no pertenece al usuario y no tiene permisos para eliminar vacaciones para otro usuario.");
        return holidayRepositoryPort.deleteById(holidayId);
    }

    //en el update by se le emte el usauirio que tiene el token con la sesion iniciada
    // si el usuario que tiene la sesion iniciada no es el mismo que el de la vacacion tirar una excepcion
    public Holiday updateHoliday(Holiday holiday) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        User currentUser = principal.getUser();
        boolean isAdmin = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ADMIN"));
        if(!isAdmin && holidayRepositoryPort.findByIdAndUserId(holiday.getId(), currentUser.getId()) == null)
            throw new IllegalArgumentException("La vacacion no pertenece al usuario y no tiene permisos para modificar vacaciones para otro usuario.");
        if(isValidHoliday(holiday, false)) {
            Holiday holidayToUpdate = holidayRepositoryPort.findById(holiday.getId());
            holidayToUpdate.setHolidayStartDate(holiday.getHolidayStartDate());
            holidayToUpdate.setHolidayEndDate(holiday.getHolidayEndDate());
            holidayToUpdate.setVacationState("aprobada");
            holidayToUpdate.setVacationType(holiday.getVacationType());
            holidayToUpdate.setUpdatedAt(OffsetDateTime.now());
            holidayToUpdate.setUpdatedBy(currentUser);
            return holidayRepositoryPort.save(holidayToUpdate);
        }
        return null;
    }

    public boolean isValidHoliday(Holiday holiday, boolean isCreation) {
        if(holiday.getHolidayEndDate().isBefore(holiday.getHolidayStartDate()))
            throw new IllegalArgumentException("La fecha de fin de vacaciones no puede ser anterior a la fecha de inicio.");
        long countOverlappingHolidays;
        if(isCreation)
            countOverlappingHolidays = holidayRepositoryPort.countOverlappingVacationsForCreation(holiday.getUser().getId(),
                    holiday.getHolidayStartDate(), holiday.getHolidayEndDate());
        else
            countOverlappingHolidays = holidayRepositoryPort.countOverlappingVacationsForUpdate(holiday.getUser().getId(),
                holiday.getHolidayStartDate(), holiday.getHolidayEndDate(),holiday.getId());
        if(countOverlappingHolidays > 0)
            throw new IllegalArgumentException("Las fechas de vacaciones se solapan con otras vacaciones existentes.");
        return true;
    }
}

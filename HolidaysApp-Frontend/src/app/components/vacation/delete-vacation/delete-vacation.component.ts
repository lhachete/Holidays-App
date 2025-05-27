import { Component } from '@angular/core';
import { HolidayService } from '../../../services/holiday.service';
import { CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';
import { CalendarComponent } from '../../calendar/calendar.component';
import { AuthService } from '../../../services/auth.service';
import Swal from 'sweetalert2';
import { CustomCalendarEv } from '../../../models/CustomCalendarEv';

@Component({
  selector: 'app-delete-vacation',
  imports: [CalendarComponent],
  templateUrl: './delete-vacation.component.html',
})
export class DeleteVacationComponent {
  // Vacaciones que tiene el usuario
  userEvents: CalendarEvent[] = [];

  get user(): any {
    return this.authService.user;
  }

  constructor(private holidayService: HolidayService, private authService: AuthService) { }

  // Carga las vacaciones al iniciar el componente
  async ngOnInit(): Promise<void> {
    const userId = this.user.userId;
    const holidays = await this.holidayService.getHolidaysById(userId);

    this.userEvents = holidays.map(h => ({
      start: new Date(h.holidayStartDate),
      end: new Date(h.holidayEndDate),
      title: `Vacaciones: ${new Date(h.holidayStartDate).toLocaleDateString()} – ${new Date(h.holidayEndDate).toLocaleDateString()}`,
      holidayId: h.holidayId 
    } as CalendarEvent));
  }


  onDayDetails = async (day: CalendarMonthViewDay<CalendarEvent>): Promise<void> => {
    // He tenido que añadirlo para que reconozca las propiedades personalizadas.
    const events = day.events as CustomCalendarEv[]; 
    if (events.length) {
      // Construimos HTML con lista de vacaciones
      const html = events[0].title;

      const result = await Swal.fire({
        title: `Borrar vacaciones en ${day.date.toLocaleDateString()}?`,
        html,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Si, borrar',
        confirmButtonColor: '#d33',
        iconColor: '#d33',
        cancelButtonText: 'Cancelar',
      });

      if (result.isConfirmed) {
        const id = events[0].holidayId;
        if (id) {
          await this.holidayService.deleteHoliday(id);
        }

        // Actualizamos la lista local excluyendo la vacación eliminada
        this.userEvents = this.userEvents.filter(holiday =>
          !events.some(selectedHoliday => selectedHoliday.holidayId === (holiday as CustomCalendarEv).holidayId)
        );

        // Notificación de éxito
        Swal.fire({
          toast: true,
          icon: 'success',
          title: 'Vacaciones borradas',
          showConfirmButton: false,
          timer: 1500,
          position: 'top-end'
        });
      }
    }
  };
}
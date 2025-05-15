import { Component } from '@angular/core';
import { HolidayService } from '../../../services/holiday.service';
import { CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';
import { CalendarComponent } from '../../calendar/calendar.component';
import { AuthService } from '../../../services/auth.service';
import Swal from 'sweetalert2';

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
    const userId = this.user.id;
    const holidays = await this.holidayService.getHolidaysById(userId);

    this.userEvents = holidays.map(h => ({
      start: new Date(h.holiday_start_date),
      end: new Date(h.holiday_end_date),
      title: `Holidays ${new Date(h.holiday_start_date).toLocaleDateString()} – ${new Date(h.holiday_end_date).toLocaleDateString()}`,
      // meta para guardar el id de la vacation que se va a eliminar. //! Igual cuando conectemos la API da problemas.
      meta: { id: h.holiday_id }
    } as CalendarEvent));
  }


  onDayDetails = async (day: CalendarMonthViewDay<CalendarEvent>): Promise<void> => {
    const events = day.events;
    if (events.length) {
      // Construimos HTML con lista de vacaciones
      console.log('events', events);
      const html = events[0].title;

      const result = await Swal.fire({
        title: `Delete vacations on ${day.date.toLocaleDateString()}?`,
        html,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, delete',
        cancelButtonText: 'Cancel'
      });

      if (result.isConfirmed) {
        const id = events[0].meta?.id;
        if (id) {
          await this.holidayService.deleteHoliday(id);
        }

        // Actualizamos la lista local excluyendo la vacación eliminada
        this.userEvents = this.userEvents.filter(holiday =>
          !events.some(selectedHoliday => selectedHoliday.meta?.id === holiday.meta?.id)
        );

        // Notificación de éxito
        Swal.fire({
          toast: true,
          icon: 'success',
          title: 'Vacations deleted',
          showConfirmButton: false,
          timer: 1500,
          position: 'top-end'
        });
      }
    }
  };
}
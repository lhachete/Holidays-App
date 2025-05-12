import { Component } from '@angular/core';
import { HolidayService } from '../../../services/holiday.service';
import { CalendarEvent } from 'angular-calendar';
import { CalendarComponent } from '../../calendar/calendar.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-delete-vacation',
  standalone: true,
  imports: [CalendarComponent],
  templateUrl: './delete-vacation.component.html',
})
export class DeleteVacationComponent {

  userEvents: CalendarEvent[] = [];

  constructor(private holidayService: HolidayService) { }


  async ngOnInit() {
    try {

      const userId = 1;
      const holidays = await this.holidayService.getHolidaysById(userId);
      console.log(holidays)
      this.userEvents = holidays.map(h => {
        const start = new Date(h.holiday_start_date);
        const end = new Date(h.holiday_end_date);
        return {
          start: start, /* start: startOfDay(start), //!Ver bien lo del startOfDay, para que és */
          end: end,
          title: `Vacaciones ${start.toLocaleDateString()} – ${end.toLocaleDateString()}`,
          meta: { id: h.holiday_id }
        } as CalendarEvent;
      });

    } catch (err) {
      console.error('Error cargando vacaciones', err);
    }
  }

  async onEventClicked(event: CalendarEvent) {
    const id = event.meta?.id;
    console.log(event)
    if (!id) return;

    const confirm = await Swal.fire({
      title: '¿Eliminar vacaciones?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, borrar',
      cancelButtonText: 'Cancelar'
    });

    if (confirm.isConfirmed) {
      await this.holidayService.deleteHoliday(id);
      this.userEvents = this.userEvents.filter(e => e.meta?.id !== id);

      Swal.fire({
        toast: true,
        icon: 'success',
        title: 'holidays deleted',
        timer: 1500,
        showConfirmButton: false,
        position: 'top-end'
      });
    }
  }
}
import { Component } from '@angular/core';
import { CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';
import { CalendarComponent } from '../../calendar/calendar.component';
import { HolidayService } from '../../../services/holiday.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-show-vacation',
  standalone: true,
  imports: [CalendarComponent],
  templateUrl: './show-vacation.component.html',
})
export class ShowVacationComponent {
  // Lista de vacaciones del usuario
  usersEvents: CalendarEvent[] = [];
  // Detalles completos del día clicado
  selectedDayDetail: CalendarMonthViewDay<CalendarEvent> | null = null;

  constructor(private holidayService: HolidayService) {}

  // Carga las vacaciones al iniciar el componente
  async ngOnInit(): Promise<void> {
    await this.loadAllHolidays();
  }

  private loadAllHolidays = async (): Promise<void> => {
    try {
      const holidays = await this.holidayService.getAllHolidays();
      console.log('holidays', holidays);
      this.usersEvents = holidays.map(h => ({
        start: new Date(h.holiday_start_date),
        end:   new Date(h.holiday_end_date),
        title: `User ${h.user_id}: ${new Date(h.holiday_start_date).toLocaleDateString()} → ${new Date(h.holiday_end_date).toLocaleDateString()}`,
        meta: { id: h.holiday_id }
      } as CalendarEvent));
    } catch (err) {
      console.error('Error loading all the holidays', err);
    }
  };

  // 
  onDayDetails = async (day: CalendarMonthViewDay<CalendarEvent>): Promise<void> => {
    this.selectedDayDetail = day;
    const events = day.events;

    if (events.length) {
    // Construir HTML con detalles de cada vacación
    const html = events.map(ev => {
      const start = ev.start.toLocaleDateString();
      const end = ev.end?.toLocaleDateString();
      return `<p><strong>${ev.title}</strong><br>Starts ${start} to ${end}</p>`;
    }).join('');

    await Swal.fire({
      title: `Active holidays on ${day.date.toLocaleDateString()}`,
      html,
      icon: 'info',
      confirmButtonText: 'Close'
    });
    }
  };
}

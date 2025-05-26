import { Component } from '@angular/core';
import { CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';
import { CalendarComponent } from '../../calendar/calendar.component';
import { HolidayService } from '../../../services/holiday.service';
import { CustomCalendarEv } from '../../../models/CustomCalendarEv';

import Swal from 'sweetalert2';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-show-vacation',
  standalone: true,
  imports: [CalendarComponent],
  templateUrl: './show-vacation.component.html',
})
export class ShowVacationComponent {
  get user(): any {
    return this.authService.user;
  }

  // Lista de vacaciones del usuario
  usersEvents: CalendarEvent[] = [];
  // Detalles completos del día clicado
  selectedDayDetail: CalendarMonthViewDay<CalendarEvent> | null = null;
  // Lista de vacaciones
  holidays: any[] = [];

  constructor(private holidayService: HolidayService, private authService: AuthService) { }

  // Carga las vacaciones al iniciar el componente
  async ngOnInit(): Promise<void> {
    await this.loadAllHolidays();
  }

  private loadAllHolidays = async (): Promise<void> => {

    try {
      const user = this.user;
      {
        user.role.name === 'ADMIN' 
        ? this.holidays = await this.holidayService.getAllHolidays()
        : this.holidays = await this.holidayService.getHolidaysById(user.id)
      }

      //console.log('Cargando vacaciones', this.holidays);
      this.usersEvents = this.holidays.map(h => ({
        start: new Date(h.holidayStartDate),
        end: new Date(h.holidayEndDate),
        title: `Vacaciones: ${new Date(h.holidayStartDate).toLocaleDateString()} – ${new Date(h.holidayEndDate).toLocaleDateString()}`,
        type: h.vacationType,
        holidayId: h.holidayId
      } as CalendarEvent));
    } catch (err) {
      console.error('Error al cargar todas las vacaciones', err);
    }
  };

  // 
  onDayDetails = async (day: CalendarMonthViewDay<CalendarEvent>): Promise<void> => {
    this.selectedDayDetail = day;

    //He tenido que añadirlo para que reconozca las propiedades personalizadas.
    const events = day.events as CustomCalendarEv[];

    if (events.length) {
      // Construir HTML con detalles de cada vacación
      const html = events.map(ev => {
        const holiday = this.holidays.find(h => h.holidayId === ev.holidayId);
        const username = holiday?.user?.username ?? 'Desconocido';

        return `<p>
          <strong>${ev.title}</strong><br>
          El tipo de la vacación es: ${ev.type}
          ${this.user.role.name === 'ADMIN' && `<br><i>Usuario: ${username}</i>`}
        </p>`;
      }).join('');


      await Swal.fire({
        title: `Vacaciones activas el ${day.date.toLocaleDateString()}`,
        html,
        icon: 'info',
        iconColor: '#153A7B',
        confirmButtonText: 'Cerrar',
        confirmButtonColor: '#153A7B'
      });
    }
  };
}

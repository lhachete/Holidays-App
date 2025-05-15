import { Component } from '@angular/core';
import { CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';
import { CalendarComponent } from '../../calendar/calendar.component';
import { HolidayService } from '../../../services/holiday.service';
import { AuthService } from '../../../services/auth.service';
import Holiday from '../../../models/Holiday';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit-vacation',
  imports: [CalendarComponent],
  templateUrl: './edit-vacation.component.html'
})
export class EditVacationComponent {
  // Lista de vacaciones del usuario
  userEvents: CalendarEvent[] = [];

  get user(): any {
    return this.authService.user;
  }

  constructor(private holidayService: HolidayService, private authService: AuthService) { }

  async ngOnInit(): Promise<void> {
    await this.loadUserHolidays();
  }

  private loadUserHolidays = async (): Promise<void> => {
    const userId = this.user.id;
    const holidays = await this.holidayService.getHolidaysById(userId);
    console.log('Holidays:', holidays);
    this.userEvents = holidays.map(holiday => {
      return this.mapToCalendarEvent(holiday)
    });
  };

  // Mapeo las holidays, y le doy formato.
  private mapToCalendarEvent = (holidy: Holiday): CalendarEvent => ({
    start: new Date(holidy.holiday_start_date),
    end: new Date(holidy.holiday_end_date),
    title: `Holiday ${new Date(holidy.holiday_start_date).toLocaleDateString()} → ${new Date(holidy.holiday_end_date).toLocaleDateString()}`,
    meta: { id: holidy.holiday_id ?? holidy.id, user_id: holidy.user_id }
  });

  /**
   * Se dispara con (dayDetails), maneja edición de todas las vacacione
   */
  onDayDetails = async (day: CalendarMonthViewDay<CalendarEvent>): Promise<void> => {
    const events = day.events;
    if (events.length) {
      const data = this.prepareEditData(events[0]);

      if (data) {
        const formValues = await this.openEditModal(data);

        if (formValues) {
          await this.saveChanges(data, formValues);
          this.refreshCalendar(data.id, formValues);
          this.showSuccessToast();
        }
      }
    }
  };

  // Preparo los datos para el modal de edición y compruebo que los id son válidos.
  private prepareEditData = (event: CalendarEvent) => {
    const id = event.meta?.id; // id de la vacation
    const user_id = event.meta?.user_id;
    if (!id || !user_id) {
      // console.error('Evento inválido para editar:', event);
      return null;
    } else {
      return { id, user_id, start: event.start, end: event.end! };
    }
  };

  /**
   * Abre modal para editar fechas y retorna nuevas fechas
   */
  private openEditModal = async (data: { start: Date; end: Date }) => {
    const currentStart = this.toDateInputValue(data.start);
    const currentEnd = this.toDateInputValue(data.end);

    const result = await Swal.fire({
      title: 'Edit vacation',
      html:
        `<label>Start:</label><input type="date" id="start" class="swal2-input" value="${currentStart}"><br>` +
        `<label>End:</label><input type="date" id="end" class="swal2-input" value="${currentEnd}">`,
      showCancelButton: true,
      focusConfirm: false,
      preConfirm: () => this.parseModalDates()
    });

    return result.value; // { holiday_start_date, holiday_end_date }
  };

  /**
   * Convierte Date a YYYY-MM-DD para input
   */
  private toDateInputValue = (date: Date): string => {
    const y = date.getFullYear();
    const m = String(date.getMonth() + 1).padStart(2, '0');
    const d = String(date.getDate()).padStart(2, '0');
    return `${y}-${m}-${d}`;
  };

  /**
   * Valida y parsea fechas del modal
   */
  private parseModalDates = (): { holiday_start_date: Date; holiday_end_date: Date } | void => {
    const start = (document.getElementById('start') as HTMLInputElement).value;
    const end = (document.getElementById('end') as HTMLInputElement).value;
    if (!start || !end) {
      Swal.showValidationMessage('You must select both start and end dates');
      return;
    }
    const [sy, sm, sd] = start.split('-').map(Number);
    const [ey, em, ed] = end.split('-').map(Number);
    return {
      holiday_start_date: new Date(sy, sm - 1, sd),
      holiday_end_date: new Date(ey, em - 1, ed)
    };
  };

  /**
   * Envia PUT al servidor con los nuevos datos
   */
  private saveChanges = async (
    data: { id: number; user_id: number },
    values: { holiday_start_date: Date; holiday_end_date: Date }
  ): Promise<void> => {
    await this.holidayService.updateHoliday({
      id: data.id,
      holiday_id: data.id,
      user_id: data.user_id,
      holiday_start_date: values.holiday_start_date,
      holiday_end_date: values.holiday_end_date
    });
  };

  /**
   * Actualiza el array local para reflejar los cambios
   */
  private refreshCalendar = (
    id: number,
    values: { holiday_start_date: Date; holiday_end_date: Date }
  ): void => {
    this.userEvents = this.userEvents.map(ev =>
      ev.meta?.id === id ? {
        ...ev,
        start: values.holiday_start_date,
        end: values.holiday_end_date,
        title: `Vacación ${values.holiday_start_date.toLocaleDateString()} → ${values.holiday_end_date.toLocaleDateString()}`
      } : ev
    );
  };

  /**
   * Muestra toast de éxito tras guardar
   */
  private showSuccessToast = (): void => {
    Swal.fire({
      toast: true,
      icon: 'success',
      title: 'Vacation updated',
      showConfirmButton: false,
      timer: 1500,
      position: 'top-end'
    });
  };
}

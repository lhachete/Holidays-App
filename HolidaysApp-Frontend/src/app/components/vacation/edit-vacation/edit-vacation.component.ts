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
    this.userEvents = holidays.map(holiday => {
      return this.mapToCalendarEvent(holiday)
    });
    console.log('userEvents', this.userEvents);
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
    console.log('events', events);

    console.log('events', day);
    if (events.length) {
      const data = this.prepareEditData(events[0]);

      if (data) {
        const formValues = await this.openEditModal(data);
        if (formValues) {
          const newStart = formValues.holiday_start_date;
          const newEnd = formValues.holiday_end_date;

          if (this.isValidVacationRange(newStart, newEnd, data.id)) {

            await this.saveChanges(data, formValues);
            this.refreshCalendar(data.id, formValues);
            this.showSuccessToast();
          }
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

  //Función para comprobar la fecha editada
  private isValidVacationRange = (newStart: Date, newEnd: Date, holidayId: number): boolean => {

    const today = new Date();
    today.setHours(0, 0, 0, 0); // eliminar la hora

    if (newStart <= today) {
      Swal.fire('Error', 'La fecha de inicio debe ser posterior a la actual.', 'error');
      return false;
    }

    if (newEnd < newStart) {
      Swal.fire('Error', 'La fecha de fin no puede ser anterior a la de inicio y viceversa.', 'error');
      return false;
    }
console.log(this.userEvents)
    // Verificar solapamiento con otras vacaciones
    for (const event of this.userEvents) {
      if (event.meta?.id === holidayId) continue; // Ignorar la vacación actual
      
      const existingStart = new Date(event.start);
      const existingEnd = new Date(event.end!);

      const overlaps = newStart <= existingEnd && newEnd >= existingStart;
      if (overlaps) {
        Swal.fire('Error', 'El nuevo rango se solapa con otra vacación existente.', 'error');
        return false;
      }
    }

    return true;
  };


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

    // Convierte la fecha a un formato de entrada de fecha HTML sin errores.
  private toDateInputValue = (date: Date): string => {
    const y = date.getFullYear();
    const m = String(date.getMonth() + 1).padStart(2, '0');
    const d = String(date.getDate()).padStart(2, '0');
    return `${y}-${m}-${d}`;
  };

  // Para asegurarnos de guardar las fechas en el formato correcto en cualquier zona horaria.
  private parseModalDates = (): { holiday_start_date: Date; holiday_end_date: Date } | void => {
    const start = (document.getElementById('start') as HTMLInputElement).value;
    const end = (document.getElementById('end') as HTMLInputElement).value;
    if (!start || !end) {
      Swal.showValidationMessage('You must select both start and end dates');
      return;
    }
      const [startYear, startMonth, startDay] = start.split('-').map(Number);
  const [endYear, endMonth, endDay] = end.split('-').map(Number);

  return {
    holiday_start_date: new Date(startYear, startMonth - 1, startDay), // Mes -1 porque enero es 0
    holiday_end_date: new Date(endYear, endMonth - 1, endDay)
  };
  };

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

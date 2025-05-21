import { Component } from '@angular/core';
import { CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';
import { CalendarComponent } from '../../calendar/calendar.component';
import { HolidayService } from '../../../services/holiday.service';
import { AuthService } from '../../../services/auth.service';
import { vacationTypeOptions } from '../../../shared/constants/vacation.constants';
import { CustomCalendarEv } from '../../../models/CustomCalendarEv';
import Holiday from '../../../models/Holiday';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit-vacation',
  imports: [CalendarComponent],
  templateUrl: './edit-vacation.component.html'
})
export class EditVacationComponent {
  userEvents: CalendarEvent[] = [];

  get user(): any {
    return this.authService.user;
  }

  private vacationTypeOptions = vacationTypeOptions;

  constructor(private holidayService: HolidayService, private authService: AuthService) { }

  async ngOnInit(): Promise<void> {
    await this.loadUserHolidays();
  }

  private loadUserHolidays = async (): Promise<void> => {
    const userId = this.user.id;
    const holidays = await this.holidayService.getHolidaysById(userId);
    console.log('holidays', holidays);
    this.userEvents = holidays.map(holiday => {
      return this.mapToCalendarEvent(holiday)
    });
  };

  // Mapeo las holidays, y le doy formato.
  private mapToCalendarEvent = (holiday: Holiday): CalendarEvent => ({
    start: new Date(holiday.holidayStartDate),
    end: new Date(holiday.holidayEndDate),
    title: `Holidays: ${new Date(holiday.holidayStartDate).toLocaleDateString()} – ${new Date(holiday.holidayEndDate).toLocaleDateString()}`,
    holidayId: holiday.holidayId,
    type: holiday.vacationType,
  }) as CustomCalendarEv;

  //Se dispara con (dayDetails) del html, para editar una vacación.

  onDayDetails = async (day: CalendarMonthViewDay<CalendarEvent>): Promise<void> => {
    const events = day.events as CustomCalendarEv[]; 

    console.log('events', events);
    console.log('events', day.events[0]);
    /* console.log('events', day);*/
    if (events.length) {
      const data = this.prepareEditData(events[0]);
      console.log('data', data);
      if (data) {
        const formValues = await this.openEditModal(data);
        if (formValues) {
          const newStart = formValues.holidayStartDate;
          const newEnd = formValues.holidayEndDate;

          if (this.isValidVacationRange(newStart, newEnd, data.holidayId)) {

            await this.saveChanges(data, formValues);
            this.refreshCalendar(data.holidayId, formValues);
            this.showSuccessToast();
          }
        }
      }
    }
  };

  // Preparo los datos para el modal de edición y compruebo que los id son válidos.
  private prepareEditData = (event: CustomCalendarEv) => {
    console.log('event', event);
    const holidayId = event.holidayId; // id de la vacation
    const userId = this.user.id; // id del usuario
    if (!holidayId || !userId) {
      // console.error('Evento inválido para editar:', event);
      return null;
    } else {
      return { holidayId, userId, start: event.start, end: event.end! };
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
      confirmButtonColor: '#b35cff',
      focusConfirm: false,
      preConfirm: () => this.parseModalDates()
    });
    return result.value; // { holidayStartDate, holidayEndDate, faltaría el vacationType }
  };

  //Función para comprobar la fecha editada
  private isValidVacationRange = (newStart: Date, newEnd: Date, holidayId: number): boolean => {

    const today = new Date();
    today.setHours(0, 0, 0, 0); // eliminar la hora

    if (newStart <= today) {
      Swal.fire('Error', 'The start date must be later than the current date.', 'error');
      return false;
    }

    if (newEnd < newStart) {
      Swal.fire('Error', 'The end date cannot be earlier than the start date, and the start date cannot be later than the end date.', 'error');
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
        Swal.fire('Error', 'The new rank overlaps with an existing holiday.', 'error');
        return false;
      }
    }

    return true;
  };


  private saveChanges = async (
    data: { holidayId: number; userId: number },
    values: { holidayStartDate: Date; holidayEndDate: Date }
  ): Promise<void> => {
    await this.holidayService.updateHoliday({
      holidayId: data.holidayId,
      userId: data.userId,
      holidayStartDate: values.holidayStartDate,
      holidayEndDate: values.holidayEndDate,
      vacationType: 'Vacation' // TODO: Cambiar por el tipo de vacación real
    });
  };

  
  private refreshCalendar = (
    id: number,
    values: { holidayStartDate: Date; holidayEndDate: Date }
  ): void => {
    this.userEvents = this.userEvents.map(ev =>
      ev.meta?.id === id ? {
        ...ev,
        start: values.holidayStartDate,
        end: values.holidayEndDate,
        title: `Holiday ${values.holidayStartDate.toLocaleDateString()} → ${values.holidayEndDate.toLocaleDateString()}`
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
  private parseModalDates = (): { holidayStartDate: Date; holidayEndDate: Date } | void => {
    const start = (document.getElementById('start') as HTMLInputElement).value;
    const end = (document.getElementById('end') as HTMLInputElement).value;
    if (!start || !end) {
      Swal.showValidationMessage('You must select both start and end dates');
      return;
    }
      const [startYear, startMonth, startDay] = start.split('-').map(Number);
  const [endYear, endMonth, endDay] = end.split('-').map(Number);

  return {
    holidayStartDate: new Date(startYear, startMonth - 1, startDay), // Mes -1 porque enero es 0
    holidayEndDate: new Date(endYear, endMonth - 1, endDay)
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

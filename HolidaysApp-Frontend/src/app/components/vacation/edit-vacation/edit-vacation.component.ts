import { Component } from '@angular/core';
import { CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';
import { CalendarComponent } from '../../calendar/calendar.component';
import { HolidayService } from '../../../services/holiday.service';
import { AuthService } from '../../../services/auth.service';
import { vacationTypeOptions, setUTCDate, toDateInputValue, parseInputDate } from '../../../shared/constants/vacation.constants';
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

  private setUTCDate = setUTCDate;
  private toDateInputValue = toDateInputValue;

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

    /* console.log('events', day);*/
    if (events.length) {
      const data = this.prepareEditData(events[0]);
      if (data) {
        const formValues = await this.openEditModal(data);
        if (formValues) {
          const newStart = this.setUTCDate(formValues.holidayStartDate);
          const newEnd = this.setUTCDate(formValues.holidayEndDate);

          if (this.isValidVacationRange(newStart, newEnd, data.holidayId)) {

            await this.saveChanges(data.holidayId, data.userId, newStart, newEnd, formValues.vacationType);
            this.refreshCalendar(data.holidayId, newStart, newEnd, formValues.vacationType);
            this.showSuccessToast();
          }
        }
      }
    }
  };

  // Preparo los datos para el modal de edición y compruebo que los id son válidos.
  private prepareEditData = (event: CustomCalendarEv) => {

    const holidayId = event.holidayId; // id de la vacation
    const userId = this.user.id; // id del usuario
    if (!holidayId || !userId) {
      // console.error('Evento inválido para editar:', event);
      return null;
    } else {
      return { holidayId, userId, start: event.start, end: event.end!, type: event.type };
    }
  };

  /**
   * Abre modal para editar fechas y retorna nuevas fechas
   */
  private openEditModal = async (data: { start: Date; end: Date; type: string }) => {
    const currentStart = this.toDateInputValue(data.start);
    const currentEnd = this.toDateInputValue(data.end);
    const typeOptionsHtml = Object
      .entries(this.vacationTypeOptions)
      .map(([val, label]) =>
        `<option value="${val}" ${val === data.type && 'selected'}>${label}</option>`
      )
      .join('');
    const html =
      `<label>Start:</label> <input type="date" id="start" class="swal2-input" value="${currentStart}"><br>` +
      `<label>End:</label> <input type="date" id="end" class="swal2-input" value="${currentEnd}"><br>` +
      `<label>Type:</label>
      <select id="type" class="swal2-select">
        ${typeOptionsHtml}
      </select>`;

    const result = await Swal.fire({
      title: 'Edit vacation',
      html,
      showCancelButton: true,
      confirmButtonColor: '#b35cff',
      focusConfirm: false,
      preConfirm: () => this.parseModalDates()
    });
    console.log('result', result);
    return result.value; 
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

    // Verificar solapamiento con otras vacaciones
    for (const event of this.userEvents as CustomCalendarEv[]) {
      if (event.holidayId === holidayId) continue; // Ignorar la vacación actual
      console.log('event', event);
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
    holidayId: number, userId: number, newStart: Date, newEnd: Date, type: string
  ): Promise<void> => {
    await this.holidayService.updateHoliday({
      holidayId,
      userId,
      holidayStartDate: newStart,
      holidayEndDate: newEnd,
      vacationType: type
    });
  };

  private refreshCalendar = (
    id: number,
    newStart: Date,
    newEnd: Date,
    type: string
  ): void => {
    this.userEvents = (this.userEvents as CustomCalendarEv[]).map(ev =>
      ev.holidayId === id ? {
        ...ev,
        start: newStart,
        end: newEnd,
        title: `Holiday ${newStart.toLocaleDateString()} - ${newEnd.toLocaleDateString()}`,
        type
      } : ev
    );
  };



  // Para asegurarnos de guardar las fechas en el formato correcto en cualquier zona horaria.
  private parseModalDates = (): { holidayStartDate: Date; holidayEndDate: Date; vacationType: string } | void => {
    const startStr = (document.getElementById('start') as HTMLInputElement).value;
    const endStr = (document.getElementById('end') as HTMLInputElement).value;
    const vacationType = (document.getElementById('type') as HTMLSelectElement).value;
    if (!startStr || !endStr) {
      Swal.showValidationMessage('You must select both start and end dates');
    } else {
      return {
        holidayStartDate: parseInputDate(startStr),
        holidayEndDate: parseInputDate(endStr),
        vacationType
      };
    }
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

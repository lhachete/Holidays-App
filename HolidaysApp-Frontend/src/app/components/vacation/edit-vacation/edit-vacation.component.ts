import { Component } from '@angular/core';
import { CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';
import { CalendarComponent } from '../../calendar/calendar.component';
import { HolidayService } from '../../../services/holiday.service';
import { AuthService } from '../../../services/auth.service';
import { vacationTypeOptions, setUTCDate, toDateInputValue, parseInputDate, showErrorAlert } from '../../../shared/constants/vacation.constants';
import { CustomCalendarEv } from '../../../models/CustomCalendarEv';
import Holiday from '../../../models/Holiday';
import Swal from 'sweetalert2';
import { set } from 'date-fns';

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
    const userId = this.user.userId;
    const holidays = await this.holidayService.getHolidaysById(userId);
    this.userEvents = holidays.map(holiday => {
      return this.mapToCalendarEvent(holiday)
    });
  };


  // Mapeo las holidays, y le doy formato.
  private mapToCalendarEvent = (holiday: Holiday): CalendarEvent => ({
    start: new Date(holiday.holidayStartDate),
    end: new Date(holiday.holidayEndDate),
    title: `Vacaciones: ${new Date(holiday.holidayStartDate).toLocaleDateString()} – ${new Date(holiday.holidayEndDate).toLocaleDateString()}`,
    holidayId: holiday.holidayId,
    type: holiday.vacationType,
  }) as CustomCalendarEv;


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

  // Preparo los datos para la ventana modal de edición y compruebo que los id son válidos.
  private prepareEditData = (event: CustomCalendarEv) => {

    const holidayId = event.holidayId;
    const userId = this.user.userId;
    if (!holidayId || !userId) {
      // console.error('Evento inválido para editar:', event);
      return null;
    } else {
      return { holidayId, userId, start: event.start, end: event.end!, type: event.type };
    }
  };


  private openEditModal = async (data: { start: Date; end: Date; type: string }) => {
    const currentStart = this.toDateInputValue(data.start);
    const currentEnd = this.toDateInputValue(data.end);
    const typeOptionsHtml = Object
      .entries(this.vacationTypeOptions)
      .map(([val, label]) =>
        `<option value="${val}" ${val === data.type && 'selected'}>${label}</option>`
      ).join('');

    const html =
      `<label>Inicio:</label> <input type="date" id="start" class="swal2-input" value="${currentStart}"><br>` +
      `<label>Fin:</label> <input type="date" id="end" class="swal2-input" value="${currentEnd}"><br>` +
      `<label>Tipo:</label>
      <select id="type" class="swal2-select">
        ${typeOptionsHtml}
      </select>`;

    const result = await Swal.fire({
      title: 'Editar vacaciones',
      html,
      showCancelButton: true,
      confirmButtonColor: '#153A7B',
      cancelButtonText: 'Cancelar',
      confirmButtonText: 'Editar',
      focusConfirm: false,
      preConfirm: () => this.parseModalDates()
    });
    return result.value;
  };

  //Función para comprobar la fecha editada
  private isValidVacationRange = (newStart: Date, newEnd: Date, holidayId: number): boolean => {

    const todayUtc = new Date();
    todayUtc.setUTCHours(0, 0, 0, 0);

    if (newStart <= todayUtc) {
      showErrorAlert('La fecha de inicio debe ser posterior a la fecha actual.');
      return false;
    }

    if (newEnd < newStart) {
      showErrorAlert('La fecha de fin no puede ser anterior a la fecha de inicio, y la fecha de inicio no puede ser posterior a la fecha de fin.');
      return false;
    }

    // Verificar solapamiento con otras vacaciones
    for (const event of this.userEvents as CustomCalendarEv[]) {
      if (event.holidayId === holidayId) continue; // Ignorar la vacación actual
      const existingStart = new Date(event.start);
      const existingEnd = new Date(event.end!);

      const overlaps = newStart <= existingEnd && newEnd >= existingStart;
      if (overlaps) {
        showErrorAlert('El nuevo rango se solapa con unas vacaciones existentes.');
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
        title: `Vacaciones ${newStart.toLocaleDateString()} - ${newEnd.toLocaleDateString()}`,
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
      Swal.showValidationMessage('Debes seleccionar tanto la fecha de inicio como la de fin');
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
      title: 'Vacaciones actualizadas',
      showConfirmButton: false,
      timer: 1500,
      position: 'top-end'
    });
  };

  
}

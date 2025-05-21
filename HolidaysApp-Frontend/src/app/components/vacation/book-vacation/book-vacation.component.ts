import { Component } from '@angular/core';
import { HolidayService } from '../../../services/holiday.service';
import { CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';
import { startOfDay, endOfDay } from 'date-fns';
import { CalendarComponent } from '../../calendar/calendar.component';
import { AuthService } from '../../../services/auth.service';
import Swal from 'sweetalert2';
import { CustomCalendarEv } from '../../../models/CustomCalendarEv';

@Component({
  selector: 'app-book-vacation',
  imports: [CalendarComponent],
  templateUrl: './book-vacation.component.html',
})
export class BookVacationComponent {
  get user(): any {
    return this.authService.user;
  }

  selectedStart: Date | null = null;
  selectedEnd: Date | null = null;

  // Detalles completos del día clicado
  selectedDayDetail: CalendarMonthViewDay<CalendarEvent> | null = null;

  // Vacaciones que YA tiene el usuario
  userEvents: CalendarEvent[] = [];
  // Vacaciones que el usuario está seleccionando (resaltadas)
  selectionEvents: CalendarEvent[] = [];

  saving: Boolean = false;


  constructor(private holidayService: HolidayService, private authService: AuthService) { }

  // Inicializa cargando las vacaciones del usuario
  async ngOnInit(): Promise<void> {
    const userId = this.user.id;
    const holidays = await this.holidayService.getHolidaysById(userId);
    this.userEvents = holidays.map(h => ({
      start: new Date(h.holidayStartDate),
      end: new Date(h.holidayEndDate),
      title: `Holiday ${new Date(h.holidayStartDate).toLocaleDateString()} – ${new Date(h.holidayEndDate).toLocaleDateString()}`,
    } as CalendarEvent));
  }

  // Combina eventos pasados junto con los seleccionados
  get displayEvents(): CalendarEvent[] {
    return [...this.userEvents, ...this.selectionEvents];
  }

  /**
   * Se dispara al recibir detalles de un día (dayDetails)
   * Valida si es pasado o hoy y muestra error
   */
  onDayDetails = (day: CalendarMonthViewDay<CalendarEvent>): void => {
    this.selectedDayDetail = day;

    // Validaciones para que no se pueda seleccionar hoy o fechas pasadas
    if (day.isPast || day.isToday) {
      Swal.fire({
        icon: 'error',
        title: 'Invalid date',
        text: 'You cannot select past or today dates for vacation.',
        confirmButtonText: 'OK'
      });
      this.clearSelection();
    } else {

      // Donde se guarda la fecha seleccionada
      const date = day.date;

      if (!this.selectedStart) {
        this.selectedStart = date;
      } else if (!this.selectedEnd) {
        this.selectedEnd = date >= this.selectedStart ? date : this.selectedStart;
      } else {
        this.selectedStart = date;
        this.selectedEnd = null;
      }

      this.updateSelectionEvents();
    }
  }


  // Genera los eventos visuales para la selección de las fechas
  private updateSelectionEvents = (): void => {
    if (this.selectedStart && this.selectedEnd) {
      this.selectionEvents = [{
        start: startOfDay(this.selectedStart),
        end: endOfDay(this.selectedEnd),
        title: 'Selected',
        color: { primary: '#38e51d', secondary: '#D1E8FF' },
      }];
    } else if (this.selectedStart) {
      this.selectionEvents = [{
        start: startOfDay(this.selectedStart),
        end: new Date(this.selectedStart),
        title: 'Selected',
        color: { primary: '#ffaa00', secondary: '#D1E8FF' },
      }];
    } else {
      this.selectionEvents = [];
    }
  };

  // Limpia la selección de las fechas
  clearSelection = (): void => {
    this.selectedStart = null;
    this.selectedEnd = null;
    this.selectionEvents = [];
  };


  addHolidayRange = async (): Promise<void> => {
    if (!this.selectedStart || !this.selectedEnd) return;

    const overlap = this.userEvents.some(ev =>
      startOfDay(this.selectedStart!) <= ev.end! &&
      endOfDay(this.selectedEnd!) >= ev.start!
    );

    if (overlap) {
      Swal.fire({
        icon: 'warning',
        title: 'Booking conflict',
        text: 'There are already vacations in the selected date range.',
        confirmButtonText: 'OK',
      });
      return;
    }

    const { value: selectedType } = await Swal.fire({
      title: 'Select vacation type',
      input: 'select',
      inputLabel: 'Type of vacation',
      inputOptions: { // Opciones de vacaciones, el campo es lo que se guarda en la BD
        Vacation: 'Vacation',
        PTO: 'Paid Time Off',
        Sick: 'Sick Leave',
        Other: 'Other',
      },
      inputValue: 'Vacation', 
      showCancelButton: true,
      confirmButtonText: 'Accept',
      confirmButtonColor: '#b35cff',
      cancelButtonText: 'Cancel',
    });

    if (selectedType) {

      if (this.saving === false) {
        this.saving = true;

        const newHoliday = await this.holidayService.addHoliday({
          userId: this.user.id,
          holidayStartDate: this.selectedStart,
          holidayEndDate: this.selectedEnd,
          vacationType: selectedType,
        });

        this.userEvents = [
          ...this.userEvents,
          {
            start: new Date(newHoliday.holidayStartDate),
            end: new Date(newHoliday.holidayEndDate),
            title: `Holiday ${new Date(newHoliday.holidayStartDate).toLocaleDateString()} – ${new Date(newHoliday.holidayEndDate).toLocaleDateString()}`,
          },
        ] as CustomCalendarEv[];

        this.clearSelection();

        Swal.fire({
          toast: true,
          icon: 'success',
          title: 'Vacations saved',
          showConfirmButton: false,
          timer: 1500,
          position: 'top-end',
        });

        this.saving = false;
      }
    }
  };




}

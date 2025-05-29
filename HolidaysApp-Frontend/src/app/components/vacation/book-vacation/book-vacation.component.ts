import { Component } from '@angular/core';
import { HolidayService } from '../../../services/holiday.service';
import { CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';
import { startOfDay, endOfDay } from 'date-fns';
import { CalendarComponent } from '../../calendar/calendar.component';
import { AuthService } from '../../../services/auth.service';
import { vacationTypeOptions, setUTCDate, toDateInputValue, parseInputDate, showErrorAlert } from '../../../shared/constants/vacation.constants';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-book-vacation',
  imports: [CalendarComponent, FormsModule],
  templateUrl: './book-vacation.component.html',
})
export class BookVacationComponent {
  get user(): any {
    return this.authService.user;
  }
  
  selectedStart: Date | null = null;
  selectedEnd: Date | null = null;
  // valores para los inputs <date>
  startInput = '';
  endInput = '';

  // Detalles completos del día clicado
  selectedDayDetail: CalendarMonthViewDay<CalendarEvent> | null = null;
  // Vacaciones que YA tiene el usuario
  userEvents: CalendarEvent[] = [];
  // Vacaciones que el usuario está seleccionando (resaltadas)
  selectionEvents: CalendarEvent[] = [];

  saving: Boolean = false;

  private vacationTypeOptions = vacationTypeOptions;
  private setUTCDate = setUTCDate;
  public toDateInputValue = toDateInputValue;

  // Fecha mínima para el select en Start date
  public get minStartDate(): string {
    return this.toDateInputValue(new Date());
  }

  // Fecha máxima para el end -> o bien startInput, o bien hoy por si no hay fecha de inicio aún.
  public get minEndDate(): string {
    return this.startInput || this.minStartDate;
  }

  constructor(private holidayService: HolidayService, private authService: AuthService) { }

  // Inicializa cargando las vacaciones del usuario
  async ngOnInit(): Promise<void> {
    const userId = this.user.userId;
    console.log('Usuario actual', this.user);
    const holidays = await this.holidayService.getHolidaysById(userId);
    console.log('Cargando vacaciones del  actual', holidays);
    
    

    this.userEvents = holidays.map(h => ({
      start: new Date(h.holidayStartDate),
      end: new Date(h.holidayEndDate),
      title: `Vacaciones ${new Date(h.holidayStartDate).toLocaleDateString()} – ${new Date(h.holidayEndDate).toLocaleDateString()}`,
      color: { 
        primary: this.user.codeColor,
        secondary: `${this.user.codeColor}25`
      }
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
      showErrorAlert('No puedes seleccionar fechas pasadas o actuales para las vacaciones.');
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
      // Actualizo los inputs y los eventos de selección.
      this.startInput = this.toDateInputValue(this.selectedStart);
      this.endInput = this.selectedEnd ? this.toDateInputValue(this.selectedEnd) : '';
      this.updateSelectionEvents();
    }
  }


  // Genera los eventos visuales para la selección de las fechas
  private updateSelectionEvents = (): void => {
    if (this.selectedStart && this.selectedEnd) {
      this.selectionEvents = [{
        start: this.selectedStart,
        end: this.selectedEnd,
        title: 'Seleccionado',
        color: { primary: '#38e51d', secondary: '#38e51d' },
      }];
    } else if (this.selectedStart) {
      this.selectionEvents = [{
        start: this.selectedStart,
        end: new Date(this.selectedStart),
        title: 'Seleccionado',
        color: { primary: '#ffaa00', secondary: '#ffaa00' },
      }];
    } else {
      this.selectionEvents = [];
    }
  };


  addHolidayRange = async (): Promise<void> => {
    if (!this.selectedStart || !this.selectedEnd) return;

    // Validación de solapamiento
    const overlap = this.userEvents.some(ev =>
      startOfDay(this.selectedStart!) <= ev.end! &&
      endOfDay(this.selectedEnd!) >= ev.start!
    );
    if (overlap) {
      await Swal.fire({
        icon: 'warning',
        title: 'Conflicto al guardar',
        text: 'Ya hay vacaciones en el intervalo de fechas seleccionadas.',
        confirmButtonText: 'OK',
        confirmButtonColor: '#153A7B',
      });
      return;
    }

    const selectedType = await this.promptVacationType();

    if (selectedType) {
      if (!this.saving) {
        this.saving = true;
        const startDateUTC = this.setUTCDate(this.selectedStart);
        const endDateUTC = this.setUTCDate(this.selectedEnd);

        const newHoliday = await this.holidayService.addHoliday({
          userId: this.user.userId,
          holidayStartDate: startDateUTC,
          holidayEndDate: endDateUTC,
          vacationType: selectedType,
        });

        // Actualizo la lista de vacaiones del usuario //! Llegan nulos desdes el backend, ESPERAR, por eso no se actualiza el calendario
        this.userEvents = [...this.userEvents,
          {
            start: new Date(newHoliday.holidayStartDate),
            end: new Date(newHoliday.holidayEndDate),
            title: `Vacaciones: ${new Date(newHoliday.holidayStartDate).toLocaleDateString()} – ${new Date(newHoliday.holidayEndDate).toLocaleDateString()}`,
            color: {
              primary: this.user.codeColor,
              secondary: `${this.user.codeColor}25`
            },
          }
        ];
        console.log('Nueva vacación añadida:', newHoliday);
        console.log('Eventos de usuario actualizados:', this.userEvents);
        this.clearSelection();
        await Swal.fire({
          toast: true,
          icon: 'success',
          title: 'Vacaciones guardadas',
          showConfirmButton: false,
          timer: 1500,
          position: 'top-end',
        });
        this.saving = false;
      }
    }
  };

  private promptVacationType = async (): Promise<string | undefined> => {
    const { value } = await Swal.fire<string>({
      title: 'Selecciona el tipo de vacaciones',
      input: 'select',
      inputLabel: 'Tipo de vacacion',
      inputOptions: this.vacationTypeOptions,
      inputValue: 'Vacación',
      showCancelButton: true,
      confirmButtonText: 'Guardar',
      confirmButtonColor: '#153A7B',
      cancelButtonText: 'Cancelar',
    });
    return value ?? undefined;
  };


  //Para que el input de fechas funcione bien
  onDateInputChange(type: 'start' | 'end', value: string) {
    const date = parseInputDate(value);
    const today = startOfDay(new Date());

    // No permitir hoy ni fechas pasadas
    if (date <= today) {
      Swal.fire('Fecha no válida', 'La fecha debe ser posterior a hoy.', 'error');
      if (type === 'start') this.startInput = '';
      else this.endInput = '';
      return;
    }

    if (type === 'start') {
      this.selectedStart = date;
      //Si ya había end y ahora es anterior, lo ajustas:
      if (this.selectedEnd && this.selectedEnd < date) {
        Swal.fire('Rango inválido', 'La fecha final no puede ser anterior a la fecha inicial.', 'error');
        this.selectedEnd = date;
        this.endInput = this.toDateInputValue(date);
      }
      this.startInput = value;
    } else {
      // Para no dejar que end sea antes de start
      if (!this.selectedStart) {
        Swal.fire('Error', 'Primero debe seleccionar una fecha de inicio.', 'error');
        this.endInput = '';
        return;
      }
      if (date < this.selectedStart) {
        Swal.fire('Rango inválido', 'La fecha final no puede ser anterior a la fecha inicial.', 'error');
        this.endInput = '';
        return;
      }
      this.selectedEnd = date;
    }
    this.updateSelectionEvents();
  }

  clearSelection = (): void => {
    this.selectedStart = null;
    this.selectedEnd = null;
    this.selectionEvents = [];
    this.startInput = '';
    this.endInput = '';
  };

}

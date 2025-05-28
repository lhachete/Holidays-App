import { Component } from '@angular/core';
import { CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';
import { CalendarComponent } from '../../calendar/calendar.component';
import { HolidayService } from '../../../services/holiday.service';
import { CustomCalendarEv } from '../../../models/CustomCalendarEv';
import { AuthService } from '../../../services/auth.service';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-show-vacation',
  standalone: true,
  imports: [CalendarComponent, FormsModule],
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
  // ID del usuario seleccionado para ver sus vacaciones
  selectedUserId: number | null = null;
  availableUsers: { userId: number; name: string; lastName: string }[] = [];


  constructor(private holidayService: HolidayService, private authService: AuthService) { }

  // Carga las vacaciones al iniciar el componente
  async ngOnInit(): Promise<void> {
    await this.loadAllHolidays();
  }

  private loadAllHolidays = async (): Promise<void> => {

    try {
      const user = this.user;
      console.log('Usuario', user);
      {
        user.rol.name === 'ADMIN'
          ? this.holidays = await this.holidayService.getAllHolidays()
          : this.holidays = await this.holidayService.getHolidaysById(user.userId)
      }

      console.log('Cargando vacaciones', this.holidays);
      this.usersEvents = this.holidays.map(h => ({
        start: new Date(h.holidayStartDate),
        end: new Date(h.holidayEndDate), /* //!Cambiar el username por el nombre */
        title: `${user.rol.name === 'ADMIN' ? h.user.username : 'Vacaciones'}: 
        ${new Date(h.holidayStartDate).toLocaleDateString()} – ${new Date(h.holidayEndDate).toLocaleDateString()}`,
        type: h.vacationType,
        holidayId: h.holidayId,
        color: {
          primary: h.user?.color,
          secondary: `${h.user?.color}25`
        }
      } as CalendarEvent));

      //
      if (this.user.rol.name === 'ADMIN') {
        const userMap = new Map();
        for (const h of this.holidays) {
          if (!userMap.has(h.user.userId)) {
            userMap.set(h.user.userId, {
              userId: h.user.userId,
              name: h.user.name,
              lastName: h.user.lastName
            });
          }
        }
        this.availableUsers = Array.from(userMap.values());
      }


    } catch (err) {
      console.error('Error al cargar todas las vacaciones', err);
    }
  };

  loadHolidaysByUserId = async (userId: number): Promise<void> => {
  try {
    const holidays = await this.holidayService.getHolidaysById(userId);
    this.usersEvents = holidays.map(h => ({
      start: new Date(h.holidayStartDate),
      end: new Date(h.holidayEndDate),
      title: `${h.user!.name} ${h.user!.lastName}: ${new Date(h.holidayStartDate).toLocaleDateString()} – ${new Date(h.holidayEndDate).toLocaleDateString()}`,
      type: h.vacationType,
      holidayId: h.holidayId,
      color: {
        primary: h.user?.color,
        secondary: `${h.user?.color}25`
      }
    } as CalendarEvent));
  } catch (err) {
    console.error('Error al cargar vacaciones por ID', err);
  }
};

onUserSelect = (event: Event): void => {
  const target = event.target as HTMLSelectElement;
  const userId = Number(target.value);
  if (userId) {
    this.loadHolidaysByUserId(userId);
  }
};

clearFilters = (): void => {
  this.selectedUserId = null;
  this.usersEvents = this.holidays.map(h => ({
    start: new Date(h.holidayStartDate),
    end: new Date(h.holidayEndDate),
    title: `${h.user.name} ${h.user.lastName}: ${new Date(h.holidayStartDate).toLocaleDateString()} – ${new Date(h.holidayEndDate).toLocaleDateString()}`,
    type: h.vacationType,
    holidayId: h.holidayId,
    color: {
      primary: h.user?.color,
      secondary: `${h.user?.color}25`
    }
  } as CalendarEvent));
};


  // 
  onDayDetails = async (day: CalendarMonthViewDay<CalendarEvent>): Promise<void> => {
    this.selectedDayDetail = day;

    //He tenido que añadirlo para que reconozca las propiedades personalizadas.
    const events = day.events as CustomCalendarEv[];

    if (events.length) {
      // Construir HTML con detalles de cada vacación //! Modificar para que muestre el nombre REAL
      const html = events.map(ev => {
        const holiday = this.holidays.find(h => h.holidayId === ev.holidayId);
        const username = holiday?.user?.username;
        return `<p>
          <strong>${ev.title}</strong><br>
          El tipo de la vacación es: ${ev.type}
          ${this.user.rol.name === 'ADMIN' ? `<br><i>Usuario: ${username}</i>` : ''}
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

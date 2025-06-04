import { Component } from '@angular/core';
import { CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';
import { CalendarComponent } from '../../calendar/calendar.component';
import { HolidayService } from '../../../services/holiday.service';
import { CustomCalendarEv } from '../../../models/CustomCalendarEv';
import { AuthService } from '../../../services/auth.service';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
/* import * as XLSX from 'xlsx'; */

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
  availableUsers: { personId: number; name: string; lastName: string }[] = [];
  searchTerm: string = '';
  showDropdown: boolean = false;
  activeIndex = -1; // índice de la opción destacada

  constructor(private holidayService: HolidayService, private authService: AuthService) { }

  // Carga las vacaciones al iniciar el componente
  async ngOnInit(): Promise<void> {
    await this.loadAllHolidays();
  }

  private loadAllHolidays = async (): Promise<void> => {

    try {
      const user = this.user;
      {
        user.rol.name === 'ADMIN'
          ? this.holidays = await this.holidayService.getAllHolidays()
          : this.holidays = await this.holidayService.getHolidaysById(user.userId)
      }

      this.usersEvents = this.holidays.map(h => ({
        start: new Date(h.holidayStartDate),
        end: new Date(h.holidayEndDate),
        title: `${h.user.employee.name}: ${new Date(h.holidayStartDate).toLocaleDateString()} – ${new Date(h.holidayEndDate).toLocaleDateString()}`,
        type: h.vacationType,
        holidayId: h.holidayId,
        color: {
          primary: h.user.codeColor,
          secondary: `${h.user.codeColor}25`
        }
      } as CalendarEvent));

      // Si el usuario es ADMIN, cargamos los usuarios disponibles para la lista desplegable
      if (this.user.rol.name === 'ADMIN') {
        const userMap = new Map();
        for (const h of this.holidays) {
          if (!userMap.has(h.user.employee.personId)) {
            userMap.set(h.user.employee.personId, {
              personId: h.user.employee.personId,
              name: h.user.employee.name,
              lastName: h.user.employee.lastName
            });
          }
        }
        this.availableUsers = Array.from(userMap.values());
      }


    } catch (err) {
      console.error('Error al cargar todas las vacaciones', err);
    }
  };

  loadHolidaysByUserId = async (personId: number): Promise<void> => {
    try {
      const holidays = await this.holidayService.getHolidaysById(personId);

      this.usersEvents = holidays.map(h => ({
        start: new Date(h.holidayStartDate),
        end: new Date(h.holidayEndDate),
        title: `${h.user?.employee?.name} ${h.user?.employee?.lastName}: ${new Date(h.holidayStartDate).toLocaleDateString()} – ${new Date(h.holidayEndDate).toLocaleDateString()}`,
        type: h.vacationType,
        holidayId: h.holidayId,
        color: {
          primary: h.user?.codeColor,
          secondary: `${h.user?.codeColor}25`
        }
      } as CalendarEvent));
    } catch (err) {
      console.error('Error al cargar vacaciones por ID', err);
    }
  };

  onUserSelect(personId: number) {
    const user = this.availableUsers.find(u => u.personId === personId);
    console.log("Usuario seleccionado:", user);
    // carga las vacaciones, asigno el valor y oculto el dropdown
    if (user) {
      this.searchTerm = `${user.name} ${user.lastName}`;
      this.selectedUserId = personId;
      this.loadHolidaysByUserId(personId);
      this.showDropdown = false;
      this.activeIndex = -1;
    };
    console.log("lista de vacaciones del usuario seleccionado:", this.filteredUsers);
  }


  clearFilters = (): void => {
    this.selectedUserId = null;
    this.searchTerm = '';
    this.usersEvents = this.holidays.map(h => ({
      start: new Date(h.holidayStartDate),
      end: new Date(h.holidayEndDate),
      title: `${h.user.employee.name} ${h.user.employee.lastName}: ${new Date(h.holidayStartDate).toLocaleDateString()} – ${new Date(h.holidayEndDate).toLocaleDateString()}`,
      type: h.vacationType,
      holidayId: h.holidayId,
      color: {
        primary: h.user.codeColor,
        secondary: `${h.user.codeColor}25`
      }
    } as CalendarEvent));
  };


  onDayDetails = async (day: CalendarMonthViewDay<CalendarEvent>): Promise<void> => {
    //He tenido que añadirlo para que reconozca las propiedades personalizadas.
    const events = day.events as CustomCalendarEv[];

    if (events.length) {
      // Construir HTML con detalles de cada vacación
      const html = events.map(ev => {
        const holiday = this.holidays.find(h => h.holidayId === ev.holidayId);
        const username = holiday.user.username;
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

  // Getter que devuelve solo los usuarios cuyo nombre o apellido contenga el searchTerm (ignorando mayúsc./minúsc.)
  get filteredUsers() {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      return this.availableUsers;
    } else {
      return this.availableUsers.filter(user =>
        (`${user.name} ${user.lastName}`).toLowerCase().includes(term)
      );
    }
  }

  // Cuando cambie el input, volvemos a mostrar el desplegable
  onSearchTermChange(value: string) {
    this.searchTerm = value;
    this.showDropdown = true;
    this.activeIndex = 0;
  }

  // Cuando el usuario pulsa Enter en el input
  selectUserFromInput() {
    
    // Si hay usuarios filtrados, seleccionamos el primero
    if (this.filteredUsers.length) {
      const first = this.filteredUsers[0];
      this.onUserSelect(first.personId);
      this.searchTerm = `${first.name} ${first.lastName}`;
      this.showDropdown = false;
    }
  }

  // Para que el desplegable se cierre al hacer clic fuera de él
  hideDropdown() {
    setTimeout(() => this.showDropdown = false);
  }

  // Para manejar las teclas de flecha arriba/abajo y Enter en el input
  onInputKeyDown(event: KeyboardEvent) {
    if (event.key === 'ArrowDown') {
      event.preventDefault();
      if (this.activeIndex < this.filteredUsers.length - 1) {
        this.activeIndex++;
      }
    } else if (event.key === 'ArrowUp') {
      event.preventDefault();
      if (this.activeIndex > 0) {
        this.activeIndex--;
      }
    } else if (event.key === 'Enter') {
      event.preventDefault();
      if (this.activeIndex >= 0 && this.activeIndex < this.filteredUsers.length) {
        const selectedUser = this.filteredUsers[this.activeIndex];
        this.onUserSelect(selectedUser.personId);
      }
    }
  }

  // Exporta las vacaciones a un archivo Excel desde el back
  exportToExcelFromBackend(): void {
    const user = this.user;

    let exportPromise: Promise<Blob>;

    if (user.rol.name === 'ADMIN') {
      if (this.selectedUserId) {
        console.log("Exportando vacaciones del usuario con ID:", this.selectedUserId);
        exportPromise = this.holidayService.getHolidaysByIdInExcel(this.selectedUserId);
      } else {
        console.log("Exportando todas las vacaciones");
        exportPromise = this.holidayService.getAllHolidaysInExcel();
      }
    } else {
      console.log("Exportando vacaciones del usuario actual");
      exportPromise = this.holidayService.getHolidaysByInExcelForUser();
    }

    exportPromise.then(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `holidays_${this.selectedUserId || user.userId || 'all'}.xlsx`;
      document.body.appendChild(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);
    }).catch(error => {
      console.error("Error al exportar el archivo Excel", error);
    });
  }





  // Exporta las vacaciones a un archivo Excel desde el frontend
  /* exportToExcel(): void {
    // Si hay un usuario seleccionado, filtra; si no, toma todas las vacaciones
    const source = this.selectedUserId
      ? this.holidays.filter(h => h.user.employee.personId === this.selectedUserId)
      : this.holidays;

    const exportData = source.map(h => ({
      'ID Vacación': h.holidayId,
      'Nombre': h.user.employee.name,
      'Apellido': h.user.employee.lastName,
      'Inicio': new Date(h.holidayStartDate).toLocaleDateString(),
      'Fin': new Date(h.holidayEndDate).toLocaleDateString(),
      'Tipo': h.vacationType,
      'Estado': h.vacationState
    }));

    // Crea la hoja y el libro
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(exportData);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Vacaciones');
    XLSX.writeFile(wb, 'vacaciones.xlsx');
  } */

}

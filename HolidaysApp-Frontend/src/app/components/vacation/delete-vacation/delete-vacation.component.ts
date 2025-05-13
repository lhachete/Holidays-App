import { Component } from '@angular/core';
import { CalendarEvent } from 'angular-calendar';
import { CalendarComponent } from '../../calendar/calendar.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-delete-vacation',
  standalone: true,
  imports: [CalendarComponent],
  templateUrl: './delete-vacation.component.html',
})
export class DeleteVacationComponent {

  userEvents: CalendarEvent[] = [];

  constructor() { }
  
}

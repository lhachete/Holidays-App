import { Component } from '@angular/core';
import { CalendarComponent } from '../../calendar/calendar.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CalendarModule } from 'angular-calendar';

@Component({
  selector: 'app-edit-vacation',
  imports: [
    CalendarComponent,
    CommonModule,
    FormsModule,
    CalendarModule
  ],
  templateUrl: './edit-vacation.component.html',
})
export class EditVacationComponent {

}

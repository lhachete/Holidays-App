import { Component, OnInit, Input, EventEmitter, Output }   from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CalendarModule, CalendarEvent} from 'angular-calendar';
import { startOfDay, endOfDay, addMonths, subMonths } from 'date-fns';
import { HolidayService } from '../../services/holiday.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-calendar',
  
  imports: [
    CommonModule,
    FormsModule,
    CalendarModule
  ],
  templateUrl: './calendar.component.html',
})
export class CalendarComponent implements OnInit {

  @Input()
  mode: 'show' | 'add' | 'put' | 'del' = 'show';

  @Output() daySelected = new EventEmitter<Date>();
  @Output() eventClicked = new EventEmitter<CalendarEvent>();
  @Input() events: CalendarEvent[] = [];

  viewDate!: Date;
  years: number[] = [];
  viewMonth!: number;
  viewYear!: number;
  months = [
    'January', 'February', 'March',     'April',
    'May',     'June',     'July',      'August',
    'September','October', 'November',  'December'
  ];

  constructor(
    private holidayService: HolidayService,
  ) {}


  ngOnInit() {
    const today = new Date();
    this.viewDate  = today;
    this.viewMonth = today.getMonth();
    this.viewYear  = today.getFullYear();

    for (let y = today.getFullYear() - 1; y <= today.getFullYear() + 5; y++) {
      this.years.push(y);
    }
}




  //TODO Funciones CRUD ----------------------------vvvvvvvvvvv

  
  private async loadAllHolidays() {
    try {
      // 1) Llamas al servicio
      const holidays = await this.holidayService.getAllHolidays();
      
      // 2) Logueas para comprobar
      console.log('Todas las holidays:', holidays);

      // 3) Mapeas al formato de angular-calendar
      this.events = holidays.map(h => {
        const start = new Date(h.holiday_start_date);
        const end   = new Date(h.holiday_end_date);
        return {
          start: startOfDay(start),
          end:   end,
          title: `User ${h.user_id}: ${start.toLocaleDateString()} → ${end.toLocaleDateString()}`
        } as CalendarEvent;
      });
    } catch (err) {
      console.error('Error cargando todas las vacaciones', err);
    }
  }


  // TODO Funciones CRUD ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  

  prevMonth() {
    const prev = subMonths(this.viewDate, 1);
    this.viewDate = prev;
    this.viewMonth = prev.getMonth();
    this.viewYear  = prev.getFullYear();
  }

  nextMonth() {
    const next = addMonths(this.viewDate, 1);
    this.viewDate = next;
    this.viewMonth = next.getMonth();
    this.viewYear  = next.getFullYear();
  }

  today() {
    const today = new Date();
    this.viewDate  = today;
    this.viewMonth = today.getMonth();
    this.viewYear  = today.getFullYear();
  }

    // Para cuando cambien los selects
    updateViewDate() {
      this.viewDate = new Date(this.viewYear, this.viewMonth, 1);
    }

}

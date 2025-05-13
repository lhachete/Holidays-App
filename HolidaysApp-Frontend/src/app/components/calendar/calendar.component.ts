import { Component, OnInit, Input}   from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CalendarModule} from 'angular-calendar';
import { addMonths, subMonths } from 'date-fns';

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

  viewDate!: Date;
  years: number[] = [];
  viewMonth!: number;
  viewYear!: number;
  months = [
    'January', 'February', 'March',     'April',
    'May',     'June',     'July',      'August',
    'September','October', 'November',  'December'
  ];

  constructor() {}

  ngOnInit() {
    const today = new Date();
    this.viewDate  = today;
    this.viewMonth = today.getMonth();
    this.viewYear  = today.getFullYear();

    for (let y = today.getFullYear() - 1; y <= today.getFullYear() + 5; y++) {
      this.years.push(y);
    }
}

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

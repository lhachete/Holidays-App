import { Component, Input, Output, EventEmitter, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CalendarModule, CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';
import { addMonths, subMonths } from 'date-fns';

@Component({
  selector: 'app-calendar',
  standalone: true,
  imports: [CommonModule, FormsModule, CalendarModule],
  templateUrl: './calendar.component.html',
})

export class CalendarComponent implements OnInit {
  @Input() mode: 'show' | 'add' | 'put' | 'del' = 'show';
  @Input() events: CalendarEvent[] = [];

  // Evento que emite todos los detalles del día seleccionado
  @Output() dayDetails = new EventEmitter<CalendarMonthViewDay<CalendarEvent>>();

  viewDate!: Date;
  years: number[] = [];
  viewMonth!: number;
  viewYear!: number;
  months = [
  'Enero', 'Febrero', 'Marzo', 'Abril',
  'Mayo', 'Junio', 'Julio', 'Agosto',
  'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
];


  //Inicializa viewDate y se generan los años disponibles.
  ngOnInit(): void {
    const today = new Date();
    this.viewDate = today;
    this.viewMonth = today.getMonth();
    this.viewYear = today.getFullYear();
    for (let y = today.getFullYear() - 1; y <= today.getFullYear() + 5; y++) {
      this.years.push(y);
    }
  }


  onDayClick(event: { day: CalendarMonthViewDay<CalendarEvent>; sourceEvent: MouseEvent | KeyboardEvent }): void {
    this.dayDetails.emit(event.day);
  }


  prevMonth(): void {
    const prev = subMonths(this.viewDate, 1);
    this.viewDate = prev;
    this.viewMonth = prev.getMonth();
    this.viewYear = prev.getFullYear();
  }

  nextMonth(): void {
    const next = addMonths(this.viewDate, 1);
    this.viewDate = next;
    this.viewMonth = next.getMonth();
    this.viewYear = next.getFullYear();
  }

  todayFn(): void {
    const today = new Date();
    this.viewDate = today;
    this.viewMonth = today.getMonth();
    this.viewYear = today.getFullYear();
  }

  //Actualiza viewDate al cambiar mes o año
  updateViewDate(): void {
    this.viewDate = new Date(this.viewYear, this.viewMonth, 1);
  }
}

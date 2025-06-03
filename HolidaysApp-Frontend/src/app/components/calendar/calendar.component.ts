import { Component, Input, Output, EventEmitter, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CalendarModule, CalendarEvent, CalendarMonthViewDay } from 'angular-calendar';
import { addMonths, subMonths } from 'date-fns';
import { ActivatedRoute  } from '@angular/router';

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

constructor(private route: ActivatedRoute) {}

  //Inicializa viewDate y se generan los años disponibles.
  ngOnInit(): void {
    const today = new Date();
    this.viewDate = today;
    this.viewMonth = today.getMonth();
    this.viewYear = today.getFullYear();
    for (let y = today.getFullYear() - 1; y <= today.getFullYear() + 5; y++) {
      this.years.push(y);
    }

    this.route.queryParams.subscribe(params => {
      if (params['date']) {
        const date = new Date(params['date']);
        this.scrollToDate(date);
      }
    });
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

  // Método para navegar a una fecha específica
  scrollToDate(date: Date): void {
    this.viewDate = date;
    this.viewMonth = date.getMonth();
    this.viewYear = date.getFullYear();
  }

}

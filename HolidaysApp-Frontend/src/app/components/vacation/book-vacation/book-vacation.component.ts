import { Component } from '@angular/core';
import { HolidayService } from '../../../services/holiday.service';
import { CalendarEvent } from 'angular-calendar';
import { startOfDay, endOfDay } from 'date-fns';
import { CalendarComponent } from '../../calendar/calendar.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-book-vacation',
  imports: [CalendarComponent],
  templateUrl: './book-vacation.component.html',
})
export class BookVacationComponent {

  selectedStart: Date | null = null;
  selectedEnd: Date | null = null;

  selectionEvents: CalendarEvent[] = [];
  userEvents: CalendarEvent[] = [];

  constructor(private holidayService: HolidayService) {}

  get displayEvents(): CalendarEvent[] {
    return [...this.userEvents, ...this.selectionEvents];
  }


  async ngOnInit() {
    try {

      const userId = 1;
      const holidays = await this.holidayService.getHolidaysById(userId);
      console.log(holidays)
      this.userEvents = holidays.map(h => {
        const start = new Date(h.holiday_start_date);
        const end = new Date(h.holiday_end_date);
        return {
          start: start, /* start: startOfDay(start), //!Ver bien lo del startOfDay, para que és */
          end: end,
          title: `Vacaciones ${start.toLocaleDateString()} – ${end.toLocaleDateString()}`,
        } as CalendarEvent;
      });

    } catch (err) {
      console.error('Error cargando vacaciones', err);
    }
  }



  onDayClicked(date: Date) {
    if (!this.selectedStart) {
      this.selectedStart = date;
      this.selectedEnd = null;
    } else if (!this.selectedEnd) {
      if (date >= this.selectedStart) {
        this.selectedEnd = date;
      } else {
        this.selectedStart = date;
      }
    } else {
      this.selectedStart = date;
      this.selectedEnd = null;
    }

    this.updateSelectionEvents();
  }

  private updateSelectionEvents() {
    if (this.selectedStart && this.selectedEnd) {
      this.selectionEvents = [{
        start: startOfDay(this.selectedStart),
        end: endOfDay(this.selectedEnd),
        title: 'Seleccionado',
        color: { primary: '#38e51d', secondary: '#D1E8FF' }
      }];
    } else if (this.selectedStart) {
      this.selectionEvents = [{
        start: startOfDay(this.selectedStart),
        end: new Date(this.selectedStart),
        title: 'Seleccionado',
        color: { primary: '#ffaa00', secondary: '#D1E8FF' }
      }];
    } else {
      this.selectionEvents = [];
    }
  }

  clearSelection() {
    this.selectedStart = null;
    this.selectedEnd = null;
    this.selectionEvents = [];
  }

  async addHolidayRange() {
    if (!this.selectedStart || !this.selectedEnd) return;

    const newHoliday = await this.holidayService.addHoliday({
      user_id: 1,
      holiday_start_date: this.selectedStart,
      holiday_end_date: this.selectedEnd
    });

    this.userEvents = [
      ...this.userEvents,
      {
        start: new Date(newHoliday.holiday_start_date),
        end: new Date(newHoliday.holiday_end_date),
        title: 'Nueva vacación'
      }
    ];
    this.clearSelection();

    Swal.fire({
      toast: true,
      icon: 'success',
      title: 'Vacaciones guardadas',
      showConfirmButton: false,
      timer: 1500,
      position: 'top-end'
    });
  }
}

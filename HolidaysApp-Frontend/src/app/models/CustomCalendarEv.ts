import { CalendarEvent } from 'angular-calendar';

export interface CustomCalendarEv extends CalendarEvent {
  type: string;
  holidayId: number;
}


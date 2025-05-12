import { Injectable } from '@angular/core';
import Holiday from '../models/Holiday';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class HolidayService {
  apiUrl: string = "";

  constructor(private api: ApiService) {
    this.apiUrl = `${this.api.getApiUrl()}/holidays`;
  }

  getAllHolidays(): Promise<Holiday[]> {
    return this.api.get<Holiday[]>(`${this.apiUrl}`);
  }

  getHolidaysById(userId: number): Promise<Holiday[]> {
    return this.api.get<Holiday[]>(`${this.apiUrl}?user_id=${userId}`);
  }
  
  // Crear una nueva vacación
  addHoliday(holiday: Holiday): Promise<Holiday> {
    return this.api.post<Holiday>(this.apiUrl, holiday);
  }

  // Actualizar (por si quieres editar fechas más tarde)
  updateHoliday(holiday: Holiday): Promise<Holiday> {
    return this.api.put<Holiday>(`${this.apiUrl}/${holiday.holiday_id}`, holiday);
  }
  
  // Borrar (o marcar borrado) una vacación
  deleteHoliday(id: number): Promise<void> {
    return this.api.delete<void>(`${this.apiUrl}/${id}`);
  }

}

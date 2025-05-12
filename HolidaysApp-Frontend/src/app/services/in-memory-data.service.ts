import { InMemoryDbService } from 'angular-in-memory-web-api';
import Holiday from '../models/Holiday';

export class InMemoryDataService implements InMemoryDbService {
    createDb() {
        const holidays: Holiday[] = [
            { 
                holiday_id: 1, user_id: 1, holiday_start_date: new Date('2025-05-15'), holiday_end_date: new Date('2025-05-20') 
            },
            {
                 holiday_id: 2, user_id: 1, holiday_start_date: new Date('2025-06-01'), holiday_end_date: new Date('2025-06-03') 
            },
            {
                holiday_id: 3, user_id: 2, holiday_start_date: new Date('2025-05-15'), holiday_end_date: new Date('2025-05-20')
            },
        ];
        return { holidays };
    }

    // Opcional: para que “auto‐genere” IDs
    genId(holidays: Holiday[]): number {
        return holidays.length > 0 ? Math.max(...holidays.map(h => h.holiday_id!)) + 1 : 1;
    }
}

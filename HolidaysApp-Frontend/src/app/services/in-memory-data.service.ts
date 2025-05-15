import { InMemoryDbService } from 'angular-in-memory-web-api';
import Holiday from '../models/Holiday';
import User from '../models/User';

export class InMemoryDataService implements InMemoryDbService {
    createDb() {
        //Los id son para pruebas.
        const holidays: Holiday[] = [
            {id: 1, holiday_id: 1, user_id: 1, holiday_start_date: new Date('2025-05-05'), holiday_end_date: new Date('2025-05-11')},
            {id: 2, holiday_id: 2, user_id: 1, holiday_start_date: new Date('2025-06-01'), holiday_end_date: new Date('2025-06-03')},
            {id: 3, holiday_id: 3, user_id: 2, holiday_start_date: new Date('2025-05-15'), holiday_end_date: new Date('2025-05-20')},
            {id: 4, holiday_id: 4, user_id: 1, holiday_start_date: new Date('2025-05-19'), holiday_end_date: new Date('2025-05-25')},
        ];

        const users: User[] = [
            { id: 1, username: 'admin', email: 'admin@example.com', password: '12345678', roles: ['USUARIO', 'ADMIN'] },
            { id: 2, username: 'user1', email: 'user1@example.com', password: '12345678', roles: ['USUARIO'] }
        ];
        
        return { holidays, users };
    }

    // para auto‐generar IDs con las pruebas
    genId<T extends { holiday_id?: number; id?: number }>(collection: T[]): number {

        if ('holiday_id' in collection[0]) {
            return Math.max(...collection.map(item => item.holiday_id!)) + 1;
        } else if ('id' in collection[0]) {
            return Math.max(...collection.map(item => item.id!)) + 1;
        }

        return 1;
    }

}

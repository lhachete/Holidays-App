import { InMemoryDbService } from 'angular-in-memory-web-api';
import User from '../models/User';

export class InMemoryDataService implements InMemoryDbService {
    createDb() {

        const users: User[] = [
            { id: 1, username: 'admin', email: 'admin@example.com', password: '12345678', roles: ['USUARIO', 'ADMIN'] },
            { id: 2, username: 'user1', email: 'user1@example.com', password: '12345678', roles: ['USUARIO'] }
        ];
        
        return { users };
    }

    // para auto‐generar IDs con las pruebas
    genId<T extends { holiday_id?: number; id?: number }>(collection: T[]): number {

        if ('id' in collection[0]) {
            return Math.max(...collection.map(item => item.id!)) + 1;
        }

        return 1;
    }

}

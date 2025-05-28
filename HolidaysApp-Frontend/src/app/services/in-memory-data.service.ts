import { InMemoryDbService, ResponseOptions, RequestInfo } from 'angular-in-memory-web-api';
import Holiday from '../models/Holiday';
import User from '../models/User';
import { Observable } from 'rxjs';

export class InMemoryDataService implements InMemoryDbService {
    createDb() {
        //Los id son para pruebas.
        const holidays: Holiday[] = [
            { id: 1, holidayId: 1, userId: 1, holidayStartDate: new Date('2025-05-05'), holidayEndDate: new Date('2025-05-11'), vacationType: 'PTO' },
            { id: 2, holidayId: 2, userId: 1, holidayStartDate: new Date('2025-06-01'), holidayEndDate: new Date('2025-06-03'), vacationType: 'PTO' },
            { id: 3, holidayId: 3, userId: 2, holidayStartDate: new Date('2025-05-15'), holidayEndDate: new Date('2025-05-20'), vacationType: 'PTO' },
            { id: 4, holidayId: 4, userId: 1, holidayStartDate: new Date('2025-05-19'), holidayEndDate: new Date('2025-05-25'), vacationType: 'PTO' },
        ];

        const users: User[] = [
            { userId: 1, username: 'admin', email: 'admin@example.com', password: 'Dedede123º', rol: { id: 1, name: 'ADMIN' }, color: '#ff0000', name: 'Admin', lastName: 'Admin Admin' },
            { userId: 2, username: 'user1', email: 'user1@example.com', password: 'Dedede123º', rol: { id: 2, name: 'USUARIO' }, color: '#f333ff', name: 'User', lastName: 'User User' },
        ];

        return { holidays, users };
    }

    // para auto‐generar IDs con las pruebas
    genId<T extends { holidayId?: number; id?: number }>(collection: T[]): number {

        if ('holidayId' in collection[0]) {
            return Math.max(...collection.map(item => item.holidayId!)) + 1;
        } else if ('id' in collection[0]) {
            return Math.max(...collection.map(item => item.id!)) + 1;
        }

        return 1;
    }

    get(reqInfo: RequestInfo): Observable<ResponseOptions> | undefined {
    const { collectionName, query } = reqInfo;

    const db = (reqInfo.utils as any).getDb();

    if (collectionName === 'holidays') {
    const db = (reqInfo.utils as any).getDb();
    const holidays: Holiday[] = db.holidays;
    const users: User[] = db.users;

    let result = holidays;

    if (query.get('userId')) {
        const userId = Number(query.get('userId')![0]);
        result = holidays.filter(h => h.userId === userId);
    }

    // Añadir el objeto de usuario completo (sin password)
    const holidaysWithUser = result.map(h => {
        const user = users.find(u => u.userId === h.userId);
        if (!user) return h;

        const { password, ...safeUser } = user;
        return { ...h, user: safeUser }; // embebemos user
    });

    return reqInfo.utils.createResponse$(() => ({
        status: 200,
        body: holidaysWithUser
    }));
}


    if (collectionName === 'users') {
        const users: User[] = db.users;

        // Si viene con user_id, devolvemos solo ese usuario
        if (query.get('userId')) {
           const holidays: Holiday[] = db.holidays;
        const userId = Number(query.get('userId')![0]);
        const filtered = holidays.filter(h => h.userId === userId);

        return reqInfo.utils.createResponse$(() => ({
            status: 200,
            body: filtered
        }));
        }

        // Si no hay filtro, devolvemos todos los usuarios sin su contraseña
        const safeUsers = users.map(({ password, ...rest }) => rest);
        return reqInfo.utils.createResponse$(() => ({
            status: 200,
            body: safeUsers
        }));
    }

    return undefined; // fallback a comportamiento por defecto
}


    // Intercepta **todos** los POST
    post(reqInfo: RequestInfo): Observable<ResponseOptions> | undefined {
        const url = reqInfo.req.url;
        // Si es POST …/login, pasamos a authenticate()
        if (url.endsWith('/login')) {
            return this.authenticate(reqInfo);
        }
        // en otro caso, que lo maneje el CRUD normal (/users, /holidays,…)
        return undefined;
    }

    private authenticate(reqInfo: RequestInfo): Observable<ResponseOptions> {
        // Body del POST
        const { usernameOrEmail, password } = reqInfo.utils.getJsonBody(reqInfo.req);

        // Coge la "DB" completa y extrae el array de usuarios
        const db = (reqInfo.utils as any).getDb(); // > any para sortear TS
        const users: User[] = db.users;

        const found = users.find(u =>
            u.username === usernameOrEmail /* || u.email === usernameOrEmail */
        );

        return reqInfo.utils.createResponse$(() => {
            let options: ResponseOptions;
            if (!found) {
                options = { status: 404, body: { error: 'USER_ERROR' } };
            } else if (found.password !== password) {
                options = { status: 401, body: { error: 'PASS_ERROR' } };
            } else {
                // No devolvemos la contraseña
                const { password: _, ...safeUser } = found;
                options = { status: 200, body: safeUser };
            }
            return options;
        });
    }


}

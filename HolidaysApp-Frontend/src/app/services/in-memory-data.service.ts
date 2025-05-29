import { InMemoryDbService, ResponseOptions, RequestInfo } from 'angular-in-memory-web-api';
import Holiday from '../models/Holiday';
import User from '../models/User';
import { Observable } from 'rxjs';

export class InMemoryDataService implements InMemoryDbService {
    createDb() {
        const holidays: Holiday[] = [
            { id: 1, holidayId: 1, userId: 1, holidayStartDate: new Date('2025-05-05'), holidayEndDate: new Date('2025-05-11'), vacationType: 'PTO' },
            { id: 2, holidayId: 2, userId: 1, holidayStartDate: new Date('2025-06-01'), holidayEndDate: new Date('2025-06-03'), vacationType: 'PTO' },
            { id: 3, holidayId: 3, userId: 2, holidayStartDate: new Date('2025-05-15'), holidayEndDate: new Date('2025-05-20'), vacationType: 'PTO' },
            { id: 4, holidayId: 4, userId: 1, holidayStartDate: new Date('2025-05-19'), holidayEndDate: new Date('2025-05-25'), vacationType: 'PTO' },
            { id: 5, holidayId: 5, userId: 3, holidayStartDate: new Date('2025-07-01'), holidayEndDate: new Date('2025-07-07'), vacationType: 'SICK' },
            { id: 6, holidayId: 6, userId: 4, holidayStartDate: new Date('2025-08-10'), holidayEndDate: new Date('2025-08-15'), vacationType: 'PTO' },
            { id: 7, holidayId: 7, userId: 5, holidayStartDate: new Date('2025-09-20'), holidayEndDate: new Date('2025-09-22'), vacationType: 'UNPAID' },
            { id: 8, holidayId: 8, userId: 6, holidayStartDate: new Date('2025-07-15'), holidayEndDate: new Date('2025-07-18'), vacationType: 'PTO' },
            { id: 9, holidayId: 9, userId: 7, holidayStartDate: new Date('2025-10-05'), holidayEndDate: new Date('2025-10-10'), vacationType: 'SICK' },
            { id: 10, holidayId: 10, userId: 3, holidayStartDate: new Date('2025-12-24'), holidayEndDate: new Date('2025-12-31'), vacationType: 'PTO' },
        ];

        const users: User[] = [
            { userId: 1, username: 'admin', email: 'admin@example.com', password: 'Dedede123º', rol: { id: 1, name: 'ADMIN' }, codeColor: '#ff0000', name: 'Admin', lastName: 'Admin Admin' },
            { userId: 2, username: 'user1', email: 'user1@example.com', password: 'Dedede123º', rol: { id: 2, name: 'USUARIO' }, codeColor: '#f333ff', name: 'User', lastName: 'User User' },
            { userId: 3, username: 'alice', email: 'alice@example.com', password: 'Dedede123º', rol: { id: 2, name: 'USUARIO' }, codeColor: '#33ccff', name: 'Alice', lastName: 'Johnson' },
            { userId: 4, username: 'bob', email: 'bob@example.com', password: 'Dedede123º', rol: { id: 2, name: 'USUARIO' }, codeColor: '#33ff77', name: 'Bob', lastName: 'Smith' },
            { userId: 5, username: 'carol', email: 'carol@example.com', password: 'Dedede123º', rol: { id: 2, name: 'USUARIO' }, codeColor: '#ffcc00', name: 'Carol', lastName: 'Davis' },
            { userId: 6, username: 'dave', email: 'dave@example.com', password: 'Dedede123º', rol: { id: 2, name: 'USUARIO' }, codeColor: '#9933ff', name: 'Dave', lastName: 'Miller' },
            { userId: 7, username: 'eve', email: 'eve@example.com', password: 'Dedede123º', rol: { id: 2, name: 'USUARIO' }, codeColor: '#ff66cc', name: 'Eve', lastName: 'Wilson' },
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
                return { ...h, employee: safeUser, codeColor: safeUser.codeColor }; // embebemos user
            });

            return reqInfo.utils.createResponse$(() => ({
                status: 200,
                body: { data: holidaysWithUser } // <-- aquí el cambio
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
                    body: { data: filtered }
                }));
            }

            // Si no hay filtro, devolvemos todos los usuarios sin su contraseña
            const safeUsers = users.map(({ password, ...rest }) => rest);
            return reqInfo.utils.createResponse$(() => ({
                status: 200,
                body: { data: safeUsers }
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

import { Role } from './Role';

export default interface User {
    id: number; 
    username: string;
    email: string;
    password?: string; // No es necesario en el frontend, pero lo dejo por si hace falta.
    repeatPassword?: string; // No es necesario en el frontend, pero lo dejo por si hace falta.
    rol: Role;
    token?: string; // Para cuando se creen los tokens de acceso.
}
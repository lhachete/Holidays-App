import { Role } from './Role';

export default interface User {
    userId: number; 
    username: string;
    email: string;
    password?: string; // No es necesario en el frontend, pero lo dejo por si hace falta.
    repeatPassword?: string; // No es necesario en el frontend, pero lo dejo por si hace falta.
    rol: Role;
    token?: string; // Para cuando se creen los tokens de acceso.
    name: string; 
    lastName: string; 
    codeColor: string; // para personalizar el calendario.
    
    // Información que llega adicional del usuario, como empleado (Del backend)
    employee?: {
        personId: number;
        name: string;
        lastName: string;}
}
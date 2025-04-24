export default interface User {
    id?: number; // Por si se necesita un ID único para cada usuario.
    username: string;
    email: string;
    password?: string; // No es necesario en el frontend, pero lo dejo por si hace falta.
    roles: string[];
    token?: string; // Para cuando se creen los tokens de acceso.
}
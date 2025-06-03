import User from "./User";

export default interface Holiday {
    id?: number; // opcional , solo es para pruebas
    holidayId?: number;
    userId: number;

    //! Añadido nuevo, ver si el userId se puede quitar
    user?: User;
    employee?: User; // Información del empleado, lo mismo que arriba, se ha modificado la API

    holidayStartDate: Date;
    holidayEndDate: Date;
    reviewDate?: Date;
    reviewComment?: string;
    vacationState?: string;
    vacationType: string;
}

/* 
    meta: { id: h.holidayId }
    holidayId: h.holidayId
*/
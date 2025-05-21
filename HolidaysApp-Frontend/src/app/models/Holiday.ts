export default interface Holiday {
    id?: number; // opcional , solo es para pruebas
    holidayId?: number;
    userId: number;
    holidayStartDate: Date;
    holidayEndDate: Date;
    reviewDate?: Date;
    reviewComment?: string;
    vacationState?: string;
    vacationType: string;
}

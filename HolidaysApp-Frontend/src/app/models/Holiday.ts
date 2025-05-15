export default interface Holiday {
    id?: number; // opcional para pruebas
    holiday_id?: number;
    user_id: number;
    holiday_start_date: Date;
    holiday_end_date: Date;
    review_date?: Date;
    review_comment?: string;
}

export default interface Holiday {
    holiday_id?: number;         // opcional para nuevas inserciones
    user_id: number;
    holiday_start_date: Date;
    holiday_end_date: Date;
    review_date?: Date;
    review_comment?: string;
}

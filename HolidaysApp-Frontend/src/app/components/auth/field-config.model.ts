export interface FieldConfig {
    key: string;
    label: string;
    type: 'text' | 'email' | 'password';
    modes: ('login' | 'register')[];
}

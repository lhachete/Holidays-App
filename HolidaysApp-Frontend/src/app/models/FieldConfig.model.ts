export interface FieldConfig {
    key: string;
    label: string;
    type: 'text' | 'email' | 'password' | 'color';
    modes: ('login' | 'register')[];
}

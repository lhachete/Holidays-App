import { max } from "date-fns";

export const VALIDATION_MESSAGES: {
    [controlName: string]: { [errorKey: string]: string }
} = {
    userInput: {
        required: 'Se requiere usuario o correo electrónico.'
    },
    username: {
        required: 'El nombre de usuario es obligatorio.',
        minlength: 'Debe tener al menos {{requiredLength}} caracteres.'
    },
    email: {
        required: 'El correo electrónico es obligatorio.',
        email: 'Formato de correo electrónico no válido.'
    },
    password: {
        required: 'La contraseña es obligatoria.',
        minlength: 'Debe tener al menos {{requiredLength}} caracteres, incluir al menos una letra mayúscula y un carácter especial.'
    },
    confirmPassword: {
        required: 'La confirmación de contraseña es obligatoria.',
        minlength: 'Debe tener al menos {{requiredLength}} caracteres, incluir al menos una letra mayúscula y un carácter especial.',
        passwordMismatch: 'Las contraseñas no coinciden.'
    },
    name: {
        required: 'El nombre es obligatorio.',
        minlength: 'Debe tener al menos {{requiredLength}} caracteres.',
        maxlength: 'No puede tener más de {{requiredLength}} caracteres.'
    },
    lastName: {
        required: 'Los apellidos son obligatorios.',
        minlength: 'Debe tener al menos {{requiredLength}} caracteres.',
        maxlength: 'No puede tener más de {{requiredLength}} caracteres.'
    },
    color: {
        required: 'El color es obligatorio.',
    }
};

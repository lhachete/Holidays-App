export const VALIDATION_MESSAGES: {
  [controlName: string]: { [errorKey: string]: string }
} = {
  userInput: {
    required: 'User or email is required.'
  },
  username: {
    required: 'Username is required.',
    minlength: 'Must be at least {{requiredLength}} characters long.'
  },
  email: {
    required: 'Email is required.',
    email: 'Invalid email format.'
  },
  password: {
    required: 'Password is required.',
    minlength: 'Must be at least {{requiredLength}} characters long.'
  },
  confirmPassword: {
    required: 'Password confirmation is required.',
    minlength: 'Must be at least {{requiredLength}} characters long.',
    passwordMismatch: 'Passwords do not match.'
  }
};

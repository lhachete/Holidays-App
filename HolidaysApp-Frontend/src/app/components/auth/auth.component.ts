import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, ReactiveFormsModule, FormControl, AbstractControl, ValidatorFn } from '@angular/forms';
import { RouterLink, Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FieldConfig } from '../../models/FieldConfig.model';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { VALIDATION_MESSAGES } from '../../shared/constants/validation.constants';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-login',
  imports: [RouterLink, ReactiveFormsModule, CommonModule, FontAwesomeModule],
  templateUrl: './auth.component.html',
})
export class AuthComponent implements OnInit {
  mode: 'login' | 'register' = 'login';
  userNotFound = false;
  serverError = false;
  
  faEye = faEye;
  faEyeSlash = faEyeSlash;
  showPasswordFields: { [key: string]: boolean } = {};
  registerErrors: { [key: string]: string } = {};

  // Campos del formulario
  fields: FieldConfig[] = [
    { key: 'userInput', label: 'Nombre de usuario o email', type: 'text', modes: ['login'] },
    { key: 'username', label: 'Nombre de usuario', type: 'text', modes: ['register'] },
    { key: 'email', label: 'Email', type: 'email', modes: ['register'] },
    { key: 'name', label: 'Nombre', type: 'text', modes: ['register'] },
    { key: 'lastName', label: 'Apellidos', type: 'text', modes: ['register'] },
    { key: 'password', label: 'Contraseña', type: 'password', modes: ['login', 'register'] },
    { key: 'confirmPassword', label: 'Confirmar contraseña', type: 'password', modes: ['register'] },
    { key: 'codeColor', label: 'Color de usuario', type: 'color', modes: ['register'] }
  ];

  loginForm = new FormGroup({
    userInput: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).*$/)])
  });

  registerForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(3)]),
    email: new FormControl('', [Validators.required, Validators.email]),
    name: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]),
    lastName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]),
    password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).*$/)]),
    confirmPassword: new FormControl('', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).*$/)]),
    codeColor: new FormControl('#153A7B', [Validators.required])
  }, { validators: this.passwordsMatchValidator() });

  constructor(
    public authService: AuthService,
    public router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      if (data['mode']) this.mode = data['mode'];
    });

    this.fields
      .filter(f => f.type === 'password')
      .forEach(f => this.showPasswordFields[f.key] = false);
  }

  get form(): FormGroup {
    return this.mode === 'login' ? this.loginForm : this.registerForm;
  }

  get currentFields(): FieldConfig[] {
    return this.fields.filter(f => f.modes.includes(this.mode));
  }

  isTouched(control: AbstractControl | null): boolean {
    return Boolean(control && (control.dirty || control.touched));
  }

  togglePasswordVisibility = (key: string): void => {
    this.showPasswordFields[key] = !this.showPasswordFields[key];
  };

  getErrorMessages(key: string): string[] {
    const control = this.form.get(key);
    let messages: string[] = [];

    if (control && this.isTouched(control) && control.errors) {
      Object.keys(control.errors).forEach(errorKey => {

        const messageTemplate = VALIDATION_MESSAGES[key]?.[errorKey];
        const formattedMessage = errorKey === 'minlength'
          ? messageTemplate.replace('{{requiredLength}}', control.errors![errorKey].requiredLength)
          : messageTemplate;

        messages = [...messages, formattedMessage];
      });
    }

    // Añadir errores de servidor en modo registro
    if (this.mode === 'register' && this.registerErrors[key]) {
      messages = [...messages, this.registerErrors[key]];
    }

    return messages;
  }

  // LOGIN
  async login(): Promise<void> {

    const userInput = this.loginForm.value.userInput ?? '';
    const password = this.loginForm.value.password ?? '';

    const result = await this.authService.login(userInput, password);
    
    if (result === 'OK') {
      this.router.navigateByUrl('/vacation/show');
    } else if (result === 'USER_ERROR') {
      this.userNotFound = true;
    } else if (result === 'SERVER_ERROR') {
      this.serverError = true;
    }
  }

  // REGISTER
  async register(): Promise<void> {
    if (this.registerForm.invalid) {
      return;
    }

    this.registerErrors = {};  // limpieza de errores
    const password = this.registerForm.value.password ?? '';
    const repeatPassword = this.registerForm.value.confirmPassword ?? '';
    const username = this.registerForm.value.username ?? '';
    const email = this.registerForm.value.email ?? '';
    const name = this.registerForm.value.name ?? '';
    const lastName = this.registerForm.value.lastName ?? '';
    const codeColor = this.registerForm.value.codeColor ?? '#153A7B';


    const { valid, errors } = await this.authService.validatePasswords({
      password, repeatPassword, 
    });

    if (!valid) {
      this.registerErrors = errors;
    } else {
      try {
        await this.authService.registerUser({ username, password, repeatPassword, email, name, lastName, codeColor });

        Swal.fire({
          icon: 'success',
          title: '¡Registro exitoso!',
          text: 'Ya puedes iniciar sesión.',
          iconColor: '#153A7B',
          confirmButtonText: 'Vale',
          confirmButtonColor: '#153A7B',
        });

        this.router.navigateByUrl('/login');

      } catch (serverMessage: any) {
        this.registerErrors = this.parseServerErrors(serverMessage);
      }
    }
  }

  submit(): void {
    this.userNotFound = this.serverError = false;
    this.mode === 'login' ? this.login() : this.register();
  }

  // Validator para que password y confirmPassword coincidan
  private passwordsMatchValidator(): ValidatorFn {
    return (group: AbstractControl) => {
      const pass = group.get('password');
      const confirm = group.get('confirmPassword');
      if (!pass || !confirm) return null;

      if (pass.value !== confirm.value) {
        confirm.setErrors({ ...confirm.errors, passwordMismatch: true });
      } else {
        if (confirm.errors) {
          delete confirm.errors['passwordMismatch'];
          if (!Object.keys(confirm.errors).length) {
            confirm.setErrors(null);
          }
        }
      }
      return null;
    };
  }

  // Parsear errores del servidor
  private parseServerErrors(message: string): { [key: string]: string } {
    const errors: { [key: string]: string } = {};
    // con esto separo los errores que estan divididos por ";" 
    const entries = message.split(';');

    entries.forEach(entry => {
      const parts = entry.split(':');

      if (parts.length >= 2) {
        const field = parts[0].trim();
        const msg = parts.slice(1).join(':').trim(); // por si el mensaje contiene ":"
        errors[field] = msg;
      } else {
        const trimmed = entry.trim();
        if (!trimmed) return;

        if (trimmed.endsWith('d')) {
          errors['codeColor'] = "Error al conectar con el servidor.";
        } else if (trimmed.startsWith('U') || trimmed.startsWith('N')) {
          errors['username'] = trimmed;
        } else if (trimmed.startsWith('E')) {
          errors['email'] = trimmed;
        } else if (trimmed.startsWith('C')|| trimmed.endsWith('D')) { //quitar <<D>> al final
          errors['codeColor'] = trimmed;
        }
      }
    });

    return errors;
  }



}

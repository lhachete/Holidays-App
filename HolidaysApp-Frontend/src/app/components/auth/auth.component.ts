import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, ReactiveFormsModule, FormControl, AbstractControl, ValidatorFn } from '@angular/forms';
import { RouterLink, Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import Swal from 'sweetalert2';

import { FieldConfig } from './field-config.model';
import { VALIDATION_MESSAGES } from './validation-messages';

@Component({
  selector: 'app-login',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './auth.component.html',
})
export class AuthComponent implements OnInit {
  mode: 'login' | 'register' = 'login';
  userNotFound = false;
  wrongPassword = false;
  registerErrors: { [key: string]: string } = {};

  // Campos del formulario
  fields: FieldConfig[] = [
    { key: 'userInput', label: 'Username', type: 'text', modes: ['login'] },
    /* { key: 'email', label: 'Email', type: 'email', modes: ['register'] }, */
    { key: 'username', label: 'Username', type: 'text', modes: ['register'] },
    { key: 'password', label: 'Password', type: 'password', modes: ['login', 'register'] },
    { key: 'confirmPassword', label: 'Confirm Password', type: 'password', modes: ['register'] },
  ];

  loginForm = new FormGroup({
    userInput: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)])
  });

  registerForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(3)]),
    /* email: new FormControl('', [Validators.required, Validators.email]), */
    password: new FormControl('', [Validators.required, Validators.minLength(8)]),
    confirmPassword: new FormControl('', [Validators.required, Validators.minLength(8)])
  }, { validators: this.passwordsMatchValidator() });

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      if (data['mode']) this.mode = data['mode'];
    });
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

    //this.userNotFound = this.wrongPassword = false;

    const result = await this.authService.login(userInput, password);

    if (result === 'OK') {
      this.router.navigateByUrl('/');
    } else if (result === 'USER_ERROR') {
      this.userNotFound = true;
    } else if (result === 'PASS_ERROR') {
      this.wrongPassword = true;
    }
  }

  // REGISTER
  async register(): Promise<void> {
    if (this.registerForm.invalid) {
      return;
    }

    this.registerErrors = {};  // limpieza de errores
    const username = this.registerForm.value.username ?? '';
    /* const email = this.registerForm.value.email ?? ''; */
    const password = this.registerForm.value.password ?? '';
    const confirmPassword = this.registerForm.value.confirmPassword ?? '';

    const { valid, errors } = await this.authService.validateRegistration({
      username, password, confirmPassword
    });

    if (!valid) {
      this.registerErrors = errors;
      return;
    }

    // si todo OK, creamos el usuario
    await this.authService.registerUser({ username, password });
    Swal.fire({
      icon: 'success',
      title: 'Registration successful!',
      text: 'You can now log in.',
      iconColor: '#c490ff',
      confirmButtonText: 'OK',
      confirmButtonColor: '#c490ff',
    });
    this.router.navigateByUrl('/login');
  }

  submit(): void {
    this.userNotFound = this.wrongPassword = false;
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
}

import { Component, OnInit, signal } from '@angular/core';
import { FormGroup, Validators, ReactiveFormsModule, FormControl } from '@angular/forms';
import { RouterLink, Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './auth.component.html',
})
export class AuthComponent implements OnInit {

  loginForm: FormGroup;
  registerForm: FormGroup;

  userInput = new FormControl('', [Validators.required]);
  username = new FormControl('', [Validators.required, Validators.minLength(3)]);
  loginPassword = new FormControl('', [Validators.required, Validators.minLength(8)]);
  registerPassword = new FormControl('', [Validators.required, Validators.minLength(8)]);
  confirmPassword = new FormControl('', [Validators.required, Validators.minLength(8)]);
  email = new FormControl('', [Validators.required, Validators.email]);

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.loginForm = new FormGroup({
      userInput: this.userInput,
      password: this.loginPassword,
    });

    this.registerForm = new FormGroup({
      username: this.username,
      email: this.email,
      password: this.registerPassword,
      confirmPassword: this.confirmPassword,
    });

  }

  public userNotFound: boolean = false;
  public wrongPassword: boolean = false;
  public mode: 'login' | 'register' = 'login';
  public registerErrors: { username?: string, email?: string, password?: string } = {};


  login(): void {

    const userInput = this.loginForm.value.userInput;
    const password = this.loginForm.value.password;

    this.userNotFound = false;
    this.wrongPassword = false;

    const result = this.authService.login(userInput, password);

    if (result === 'OK') {
      this.router.navigateByUrl('/');
    } else if (result === 'USER_ERROR') {
      this.userNotFound = true;
    } else if (result === 'PASS_ERROR') {
      this.wrongPassword = true;
    }
  }

  private validateRegister(): boolean {
    const { username, email, password, confirmPassword } = this.registerForm.value;

    this.registerErrors = {};

    if (this.authService.users.some(user => user.username === username)) {
      this.registerErrors.username = 'Username is already taken.';
    }

    if (this.authService.users.some(user => user.email === email)) {
      this.registerErrors.email = 'Email is already registered.';
    }

    if (password !== confirmPassword) {
      this.registerErrors.password = 'Passwords do not match.';
    }

    return Object.keys(this.registerErrors).length === 0;
  }


  register(): void {
    const { username, email, password } = this.registerForm.value;
    if (this.registerForm.valid && this.validateRegister()) {
      this.authService.registerUser({ username, email, password });
      Swal.fire({
        icon: 'success',
        title: 'Registration successful!',
        text: 'You can now log in.',
        iconColor: '#c490ff',
        confirmButtonText: 'OK',
        confirmButtonColor: '#c490ff',
      })
      this.router.navigateByUrl('/login');
    }
  }

  submit(): void {
    this.userNotFound = false;
    this.wrongPassword = false;

    if (this.mode === 'login') {
      this.login();
    } else {
      this.register();
    }
  }

  get form(): FormGroup {
    return this.mode === 'login' ? this.loginForm : this.registerForm;
  }

  isTouched(control: FormControl): boolean {
    return control.dirty || control.touched;
  }


  ngOnInit(): void {
    this.route.data.subscribe(data => {
      if (data['mode']) this.mode = data['mode'];
    });
  }

}
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

  public authForm = signal<FormGroup>(
    new FormGroup({
      userInput: new FormControl('', [Validators.required, Validators.minLength(3)]),
      password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(20), Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,20}$/)]),
      username: new FormControl('', [Validators.required, Validators.minLength(3)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      confirmPassword: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(20), Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,20}$/)]),
    }
    ));

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  public userNotFound: boolean = false;
  public wrongPassword: boolean = false;
  public mode: 'login' | 'register' = 'login';
  public registerErrors: { username?: string, email?: string, password?: string } = {};


  login(): void {

    const userInput = this.authForm().value.userInput;
    const password = this.authForm().value.password;

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

  register(): void {
    const { username, email, password, confirmPassword } = this.authForm().value;
    this.registerErrors.password = '';
    this.registerErrors.username = '';
    this.registerErrors.email = '';

    const usernameExists = this.authService.users.some(user => user.username === username);
    const emailExists = this.authService.users.some(user => user.email === email);

    if (password !== confirmPassword) {
      this.registerErrors.password = 'Passwords do not match.';
    }
    if (password.length < 8 || password.length > 20 || !password.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,20}$/)) {
      this.registerErrors.password += ' Password must be 8–20 characters long, and include at least one lowercase letter, one uppercase letter, one number, and one special character.';
    }

    if (usernameExists) {
      this.registerErrors.username = 'Username is already taken.';
    }
    if (username.length < 3) {
      this.registerErrors.username = 'Username must be at least 3 characters long.';
    }

    if (emailExists) {
      this.registerErrors.email = 'Email is already registered.';
    }
    if (!this.authForm().value.email.match(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)) {
      this.registerErrors.email += ' Invalid email format.';
    }

    this.authForm().get('userInput')?.setValue(username);
    if (this.authForm().valid && !usernameExists && !emailExists && password === confirmPassword) {
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

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      if (data['mode']) this.mode = data['mode'];
    });
  }

}
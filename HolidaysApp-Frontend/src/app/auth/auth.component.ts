import { Component, OnInit, signal } from '@angular/core';
import { FormGroup, Validators, ReactiveFormsModule, FormControl } from '@angular/forms';
import { RouterLink, Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './auth.component.html',
})
export class AuthComponent implements OnInit {

  public authForm = signal<FormGroup>(
    new FormGroup({
      userInput: new FormControl('', [Validators.required, Validators.minLength(3)]),
      password: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(20), Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,20}$/)]),
      username: new FormControl('', [Validators.required, Validators.minLength(3)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      confirmPassword: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(20), Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,20}$/)]),
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
    if (password.length < 6 || password.length > 20) {
      this.registerErrors.password += ' Password must be between 6 and 20 characters.';
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

    if (this.authForm().valid && !usernameExists && !emailExists && password === confirmPassword) {
      this.authService.registerUser({ username, email, password });
      alert('User registered successfully!');
      this.router.navigateByUrl('/login');
    }

  }

  submit(): void {
    // Resetea los mensajes de error
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
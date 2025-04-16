import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  public loginForm!: FormGroup;
  public userNotFound: boolean = false;
  public wrongPassword: boolean = false;



  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  login(): void {
    const username = this.loginForm.value.username;
    const password = this.loginForm.value.password;
  
    this.userNotFound = false;
    this.wrongPassword = false;
  
    const result = this.authService.login(username, password);
  
    if (result === 'OK') {
      this.router.navigateByUrl('/');
    } else if (result === 'USER_ERROR') {
      this.userNotFound = true;
    } else if (result === 'PASS_ERROR') {
      this.wrongPassword = true;
    }
  }
  

}
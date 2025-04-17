import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { RouterLink, Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './auth.component.html',
})
export class AuthComponent implements OnInit {

  public authForm!: FormGroup;
  public userNotFound: boolean = false;
  public wrongPassword: boolean = false;
  public mode: 'login' | 'register' = 'login';

  constructor(
    private formBuilder: FormBuilder,
     private authService: AuthService,
      private router: Router,
      private route: ActivatedRoute
    ) {
    this.authForm = this.formBuilder.group({
      userInput: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
      // Para el registro podrías agregar más campos, ejemplo:
      // confirmPassword: ['']
    });
  }

  login(): void {
    const userInput = this.authForm.value.userInput;
    const password = this.authForm.value.password;

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
    // Aquí se definirá la lógica de registro, validaciones extras, etc.
    console.log('Registro de usuario:', this.authForm.value);
    
    //Para cuando sea exitoso.
    this.router.navigateByUrl('/login');
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
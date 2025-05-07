import { Component, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ReactiveFormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'HolidaysApp-Frontend';
  isInvalidEmail:boolean = false;
  isInvalidPassword:boolean = false;
  formLogin = signal<FormGroup>(
    new FormGroup({
      // Define your form controls here
      // Example: name: new FormControl('')
      usernameOrEmail: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(25), Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(25)]),
    })
  );

  onSubmit() {
    const form = this.formLogin();
  
    if (form.invalid) {
      // Marcar todos los controles como tocados
      Object.entries(form.controls).forEach(([formControlName,control]) => {
        if(formControlName === 'usernameOrEmail' && control.errors) {
          this.isInvalidEmail = true;
        }
        if(formControlName === 'password' && control.errors) {
          this.isInvalidPassword = true;
        }
        control.markAsTouched(); // Marca el control como tocado
      });
  
      console.log('Formulario inválido');
    } else {
      console.log('Formulario válido', form.value);
    }
  }

  ngOnInit() {
    // Para ver el estado del formulario completo
    console.log(this.formLogin().status); // VALID o INVALID
    console.log(this.formLogin().value);

    // Para ver errores de un campo específico
    const usernameControl = this.formLogin().get('usernameOrEmail');
    const passwordControl = this.formLogin().get('password');

    console.log('Username errors:', usernameControl?.errors);
    console.log('Password errors:', passwordControl?.errors);
  }
}

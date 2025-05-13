import { Component, signal } from '@angular/core';
import { NavbarComponent } from "../../components/navbar/navbar.component";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { repeat } from 'rxjs';
import { UserService } from '../../services/user.service';
import { Router, RouterLink } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  imports: [NavbarComponent, ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  formValidationErrors: string[] = [];
  formRegister = signal<FormGroup>(
    new FormGroup(
      {
        username: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
        email: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(50), Validators.email]),
        password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(25)]),
        repeatPassword: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(25)]),
      })
  );

  constructor(private userService: UserService, private router: Router) {
    // Aquí puedes inicializar cualquier cosa que necesites
  }

  async onSubmit() {
    const form = this.formRegister();

    if (form.invalid) {
      this.formValidationErrors = this.getFormValidationErrors();
      console.log(this.formValidationErrors);
    } else {
      if (await this.userService.register(this.formRegister().value.username, this.formRegister().value.email, this.formRegister().value.password)) {
        console.log('true');
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Register successful",
          showConfirmButton: false,
          timer: 1500
        });
        this.router.navigate(['/login']);
      } else {
        console.log('false');
        Swal.fire({
          icon: "error",
          title: "Users already exists with tihs credentials",
          text: "Username or email already exists",
          //text: "Password or email is incorrect",
          //footer: '<a href="#">Why do I have this issue?</a>'
        });
        // Aquí puedes manejar el error de registro, como mostrar un mensaje al usuario
      }
    }
  }

  getFormValidationErrors(): string[] {
    const errors: string[] = [];
    const form = this.formRegister();

    for (const controlName in form.controls) {
      console.log(controlName);
    }

    Object.keys(form.controls).forEach(controlName => {
      const controlErrors = form.get(controlName)?.errors;
      if (controlErrors) {
        Object.keys(controlErrors).forEach(errorKey => {
          console.log('se mete en el for');
          if (errorKey === 'required') {
            errors.push(`${controlName} es obligatorio.`);
          } else if (errorKey === 'email') {
            errors.push(`${controlName} no tiene formato de email válido.`);
          } else if (errorKey === 'minlength') {
            console.log('se mete en el min');
            errors.push(`${controlName} debe tener al menos ${controlErrors['minlength'].requiredLength} caracteres.`);
          } else if (errorKey === 'maxlength') {
            errors.push(`${controlName} no puede tener más de ${controlErrors['maxlength'].requiredLength} caracteres.`);
          }
          // Puedes seguir añadiendo más condiciones aquí
        });
      }
    });
    if (form.get('password')?.value !== form.get('repeatPassword')?.value) {
      errors.push(`Las contraseñas no coinciden.`);
    }
    console.log('errors:', errors);
    return errors;
  }
}

import { Component, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { NavbarComponent } from '../components/navbar/navbar.component';
import { UserService } from '../services/user.service';
@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, NavbarComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  isInvalidEmail: boolean = false;
  isInvalidPassword: boolean = false;
  formLogin = signal<FormGroup>(
    new FormGroup({
      // Define your form controls here
      // Example: name: new FormControl('')
      usernameOrEmail: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(25)]),
    })
  );

  constructor(private userService: UserService) {

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

  async onSubmit() {
    //this.userService.getUsers(); // Llama al servicio para obtener los usuarios
     const form = this.formLogin();

     if (form.invalid) {
       // Marcar todos los controles como tocados
       Object.entries(form.controls).forEach(([formControlName, control]) => {
         if (formControlName === 'usernameOrEmail' && control.errors) {
           this.isInvalidEmail = true;
        }
         if (formControlName === 'password' && control.errors) {
           this.isInvalidPassword = true;
         }
         control.markAsTouched(); // Marca el control como tocado
       });

       console.log('Formulario inválido');
     } else {
       if (await this.userService.login(form.value.usernameOrEmail, form.value.password)) {
        console.log('true')
         Swal.fire({
           position: "center",
           icon: "success",
           title: "Your work has been saved",
           showConfirmButton: false,
           timer: 1500
         });
       } else {
        console.log('false')
         Swal.fire({
           icon: "error",
           title: "Oops...",
           text: "Something went wrong!",
           footer: '<a href="#">Why do I have this issue?</a>'
         });
       }
     }
  }
}

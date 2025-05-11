import { Component, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import Swal from 'sweetalert2';
import { LoginComponent } from "./pages/login.component";
import { HomeComponent } from "./pages/home/home.component";


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ReactiveFormsModule, LoginComponent, HomeComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'HolidaysApp-Frontend';
}
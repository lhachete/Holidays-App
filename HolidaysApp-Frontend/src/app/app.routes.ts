import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login.component';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';

export const routes: Routes = [
    {path: '', redirectTo: 'login', pathMatch: 'full'},
    { path: 'login', component: LoginComponent },
    {path: 'home', component: HomeComponent}, // Asegúrate de importar HomeComponent en el archivo correspondiente
    {path: 'register', component: RegisterComponent},
];

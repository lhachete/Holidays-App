import { Routes } from '@angular/router';
import { AuthComponent } from './auth/auth.component';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
    { path: "", component: HomeComponent },
    { path: "login", component: AuthComponent, data: { mode: 'login' } },
    { path: "register", component: AuthComponent, data: { mode: 'register' } },
];

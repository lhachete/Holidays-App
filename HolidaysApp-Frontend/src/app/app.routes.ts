import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { vacationChildRoutes } from "./components/vacation/vacation.routes";
import { authGuard } from './guards/auth.guard';
import { loadForbiddenComponent, loadNotFoundComponent, loadAuthComponent, loadVacationComponent } from './shared/loaders/lazy-loaders';

export const routes: Routes = [
    { path: "", component: HomeComponent, pathMatch: 'full'  },
    { path: "login", loadComponent: () => loadAuthComponent(), data: { mode: 'login' } },
    { path: "register", loadComponent: () => loadAuthComponent(), data: { mode: 'register' } },

    { path: "vacation", loadComponent: () => loadVacationComponent(), canActivate: [authGuard], children: vacationChildRoutes},
    
    { path: "forbidden", loadComponent: () => loadForbiddenComponent() },
    { path: "**", loadComponent: () => loadNotFoundComponent() }

];

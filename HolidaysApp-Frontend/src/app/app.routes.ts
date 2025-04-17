import { Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component';
import { HomeComponent } from './components/home/home.component';
import { VacationComponent } from './components/vacation/vacation.component';
import { BookVacationComponent } from './components/vacation/book-vacation/book-vacation.component';
import { EditVacationComponent } from './components/vacation/edit-vacation/edit-vacation.component';
import { DeleteVacationComponent } from './components/vacation/delete-vacation/delete-vacation.component';
import { ShowVacationComponent } from './components/vacation/show-vacation/show-vacation.component';


export const routes: Routes = [
    { path: "", component: HomeComponent },
    { path: "login", component: AuthComponent, data: { mode: 'login' } },
    { path: "register", component: AuthComponent, data: { mode: 'register' } },
    { path: "vacation", component: VacationComponent, children: [
        { path: "show", component: ShowVacationComponent},
        { path: "book", component: BookVacationComponent },
        { path: "edit", component: EditVacationComponent },
        { path: "delete", component: DeleteVacationComponent}
    ]}
];

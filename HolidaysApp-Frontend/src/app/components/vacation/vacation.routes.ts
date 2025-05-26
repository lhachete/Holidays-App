import { Routes } from '@angular/router';

export const vacationChildRoutes: Routes = [
    {
        path: "show",
        loadComponent: () => import('./show-vacation/show-vacation.component').then(m => m.ShowVacationComponent)
    },
    {
        path: "book",
        loadComponent: () => import('./book-vacation/book-vacation.component').then(m => m.BookVacationComponent)
    },
    {
        path: "edit",
        loadComponent: () => import('./edit-vacation/edit-vacation.component').then(m => m.EditVacationComponent)
    },
    {
        path: "delete",
        loadComponent: () => import('./delete-vacation/delete-vacation.component').then(m => m.DeleteVacationComponent)
    }
];

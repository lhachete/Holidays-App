import { HttpClient } from '@angular/common/http';
import { Component, Injectable } from '@angular/core';
import { catchError, map, Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  users: any[] = [];

  constructor(private httpClient: HttpClient) {

   }

   login(usernameOrEmail: string, password: string): boolean {
    let isLoggedIn:boolean = false;
    console.log('users', this.httpClient.get<any[]>('https://dummyjson.com/users'))
    return true;
    // this.httpClient.get<any[]>('https://dummyjson.com/users')
    // .subscribe(response => {
    //   this.users = response;

    //   const userFound = this.users.find(user =>
    //     (user.username === usernameOrEmail || user.email === usernameOrEmail) &&
    //     user.password === password
    //   );

    //   if (userFound) {
    //     isLoggedIn = true;
    //     console.log('✅ Login exitoso');
    //     // continuar lógica aquí
    //   } else {
    //     console.log('❌ Usuario no encontrado');
    //   }
    // });
    // return isLoggedIn;    
  }
}

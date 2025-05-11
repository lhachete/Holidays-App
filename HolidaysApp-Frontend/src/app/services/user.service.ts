import { HttpClient } from '@angular/common/http';
import { Component, Injectable } from '@angular/core';
import { catchError, firstValueFrom, map, Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })

export class UserService {

  users: any[] = [];

  constructor(private httpClient: HttpClient) {

  }

  async login(usernameOrEmail: string, password: string): Promise<boolean> {

    let correctLogin: boolean = false;

    try {
      const response = await firstValueFrom(this.httpClient.get<any>('https://dummyjson.com/users'));
      console.log('respuesta:');
      console.log(response);
      console.log('users de la respuesta:');
      console.log(response.users);
      const users = response.users; // Aquí ya tienes el array
  
      users.forEach((user: any) => {
        if ((user.username === usernameOrEmail || user.email === usernameOrEmail) && user.password === password) {
          console.log('Login successful!');
          correctLogin = true;
        }
      });
  
      console.log(usernameOrEmail, password);
      return Promise.resolve(correctLogin);
  
    } catch (error) {
      console.error('Error during login:', error);
      return Promise.resolve(false);
    }
  }

  getUsers(): void {
    this.httpClient.get<any>('https://dummyjson.com/users').subscribe(user => {
      this.users.push(user);
    });
    console.log(this.users);
  }
}

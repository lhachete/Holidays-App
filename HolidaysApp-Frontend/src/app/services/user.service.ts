import { HttpClient } from '@angular/common/http';
import { Component, Injectable } from '@angular/core';
import { catchError, firstValueFrom, map, Observable, of, tap } from 'rxjs';
import User from '../models/User';

@Injectable({ providedIn: 'root' })

export class UserService {

  users: any[] = [];
  username: string = '';

  constructor(private httpClient: HttpClient) {

  }

  /*
    las funciones son asincronas cuando se sabe que dentro se va a hacer una peticion 
    y no se sabe cuanto va a tardar en hacerse 
    y a lo mejor las siguientes funciones que tiene el programa depende de estas 
    y sino la esperan dara error porque la funcion no habra devuelto nada
  */
  // La funcion es asincrona porque hace una peticion http y se sabe que puede tardar un tiempo en completarse
  login2(usernameOrEmail: string, password: string): Observable<User> { 
    try {
      const loginRequest = {
        username: usernameOrEmail,
        password: password
      }
      console.log('loginRequest:', loginRequest);
      let user: Observable<User> = this.httpClient.post<User>('http://localhost:8080/api-rest/users/login', loginRequest); // esto devuelve un objeto que dentro
      //console.log('respuesta:', user);
      //this.username = loginRequest.username; // Guardar el nombre de usuario en la variable de clase
      user.subscribe({
        next: (user: User) => {
          this.username = user.username; // Guardar el nombre de usuario en la variable de clase
        }, error: (error) => {
          console.error('Error during login:', error);
        }
      });
      console.log('username of service:', this.username);
      return user;
    } catch (error) {
      console.error('Error during login:', error);
      throw error; // Lanza el error para que pueda ser manejado por el llamador
    }
  } // El promise es como el future de Flutter, es una promesa que se resuelve en el futuro y
  
  async login(usernameOrEmail: string, password: string): Promise<boolean> { // El promise es como el future de Flutter, es una promesa que se resuelve en el futuro y 
    // hasta entonces no tiene valor

    let correctLogin: boolean = false;

    /*
      firstValueFrom() es una función de RxJS que convierte un Observable en una Promise. En Angular, 
      cuando haces una petición HTTP con HttpClient.get(), lo que obtienes no es una Promise directamente, sino un Observable.
      Como await solo funciona con Promises, necesitas convertir ese Observable en una Promise. Para eso se usa firstValueFrom.
    */

    try {
      const response = await firstValueFrom(this.httpClient.get<any>('https://dummyjson.com/users')); // esto devuelve un objeto que dentro 
      // tiene el atributo users que es un array
      console.log('respuesta:');
      console.log(response);
      console.log('users de la respuesta:');
      console.log(response.users);
      const users = response.users; // Aquí ya tienes el array

      users.forEach((user: any) => {
        if ((user.username === usernameOrEmail || user.email === usernameOrEmail) && user.password === password) {
          console.log('Login successful!');
          this.username = user.username; // Guardar el nombre de usuario en la variable de clase
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

  async register(username: string, email: string, password: string): Promise<boolean> {
    let correctRegister: boolean = true;

    try {
      const response = await firstValueFrom(this.httpClient.get<any>('https://dummyjson.com/users')); // esto devuelve un objeto que dentro 
      // tiene el atributo users que es un array
      console.log('respuesta:');
      console.log(response);
      console.log('users de la respuesta:');
      console.log(response.users);
      const users = response.users; // Aquí ya tienes el array

      users.forEach((user: any) => {
        if ((user.username === username || user.email === email)) {
          console.log('User already exists!');
          correctRegister = false; // Cambia a false si el usuario ya existe
          // Guardar el nombre de usuario en la variable de clase
        }
      });
      if(correctRegister) {
        console.log('User registered successfully!');
        this.username = username; // Guardar el nombre de usuario en la variable de clase
      }
      //console.log(usernameOrEmail, password);
      return Promise.resolve(correctRegister);

    } catch (error) {
      console.error('Error during login:', error);
      return Promise.resolve(false);
    }
  }
}

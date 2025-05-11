import { HttpClient } from '@angular/common/http';
import { Component, Injectable } from '@angular/core';
import { catchError, firstValueFrom, map, Observable, of } from 'rxjs';

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
}

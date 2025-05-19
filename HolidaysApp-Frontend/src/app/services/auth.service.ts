import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from './user.service';
import User from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public user: User | null = null;
  public isAuthenticated = false;

  constructor(
    private router: Router,
    private userService: UserService
  ) {
    const session = localStorage.getItem('userSession');
    if (session) {
      this.user = JSON.parse(session);
      this.isAuthenticated = true;
    }
  }

  async login(userInput: string, password: string): Promise<'OK' | 'USER_ERROR' | 'PASS_ERROR'> {
    try {

      const foundUser = await this.userService.login(userInput, password);
      console.log('foundUser', foundUser);
      if (!foundUser) {
        console.error('Backend devolvió null o user no encontrado');
        return 'USER_ERROR';
      }

      this.user = {
        id: foundUser.id,
        username: foundUser.username,
        /* email: foundUser.email, */
        role: foundUser.role
      };

      
      this.isAuthenticated = true;
      localStorage.setItem('userSession', JSON.stringify(this.user));
      return 'OK';

    } catch (err: any) {
      const code = err.status;
      console.error('Error logging in', err);
      if (code === 404) return 'USER_ERROR';
      if (code === 401) return 'PASS_ERROR';
      return 'USER_ERROR';
    }
  }

  //Registra un nuevo usuario en la API (in-memory para pruebas) email: string; < eliminado
  async registerUser(user: { username: string; password: string; }): Promise<User> {
    const newUser = await this.userService.addUser({
      ...user
    } as User);
    return newUser;
  }


  logout(): void {
    this.isAuthenticated = false;
    this.user = null;
    localStorage.removeItem('userSession');
    this.router.navigateByUrl('/login');
  }

  get isLoggedIn(): boolean {
    return this.isAuthenticated;
  }

  //Compruebo si el usuario tiene el rol indicado
  hasRole(role: string): boolean {
  return this.user?.role?.name === role;
}

  //Comprueba si ya existe un usuario (para pruebas con mock)
  async isUsernameTaken(username: string): Promise<boolean> {
    const users = await this.userService.getAllUsers();
    return users.some(u => u.username === username);
  }


  //Comprueba si ya existe un email (para pruebas con mock) 
   
  /* async isEmailTaken(email: string): Promise<boolean> {
    const users = await this.userService.getAllUsers();
    return users.some(u => u.email === email);
  } */


  // Valida todos los datos de registro y devuelve posibles errores
  async validateRegistration(data: {
    username: string;
    /* email: string; */
    password: string;
    confirmPassword: string;
  }): Promise<{ valid: boolean; errors: Record<string, string> }> {
    const errs: Record<string, string> = {};

    if (await this.isUsernameTaken(data.username)) {
      errs['username'] = 'Username is already taken.';
    }
/*     if (await this.isEmailTaken(data.email)) {
      errs['email'] = 'Email is already registered.';
    } */
    if (data.password !== data.confirmPassword) {
      errs['password'] = 'Passwords do not match.';
    }

    return { valid: Object.keys(errs).length === 0, errors: errs };
  }
}

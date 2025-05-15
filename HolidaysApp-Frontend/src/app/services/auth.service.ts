// auth.service.ts
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

  //utilizado antes userInput aqui
  async login(username: string, password: string): Promise<'OK' | 'USER_ERROR' | 'PASS_ERROR'> {
    try {
      console.log("enter login"); // entra en el login
      console.log('username', username);
      console.log('password', password);

      const foundUser = await this.userService.login(username, password);
      console.log('foundUser', foundUser);
      if (!foundUser) {
        console.error('Backend devolvió null o user no encontrado');
        return 'USER_ERROR';
      }
      this.user = {
        id: foundUser.id,
        username: foundUser.username,
        email: foundUser.email,
        roles: foundUser.roles
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

  //Registra un nuevo usuario en la API (in-memory para pruebas)

  async registerUser(user: { username: string; email: string; password: string; }): Promise<User> {
    const newUser = await this.userService.addUser({
      ...user,
      roles: ['USUARIO']
    } as User);
    return newUser;
  }

  //Cierra sesión del usuario

  logout(): void {
    this.isAuthenticated = false;
    this.user = null;
    localStorage.removeItem('userSession');
    this.router.navigateByUrl('/login');
  }

  /**
   * Indica si el usuario está logueado
   */
  get isLoggedIn(): boolean {
    return this.isAuthenticated;
  }

  /**
   * Comprueba si el usuario tiene el rol indicado
   */
  hasRole(role: string): boolean {
    return this.user?.roles.includes(role) ?? false;
  }

  /**
   * Comprueba si ya existe un username
   */
  async isUsernameTaken(username: string): Promise<boolean> {
    const users = await this.userService.getAllUsers();
    return users.some(u => u.username === username);
  }

  /**
   * Comprueba si ya existe un email
   */
  async isEmailTaken(email: string): Promise<boolean> {
    const users = await this.userService.getAllUsers();
    return users.some(u => u.email === email);
  }

  /**
   * Valida todos los datos de registro y devuelve posibles errores
   */
  async validateRegistration(data: {
    username: string;
    email: string;
    password: string;
    confirmPassword: string;
  }): Promise<{ valid: boolean; errors: Record<string, string> }> {
    const errs: Record<string, string> = {};

    if (await this.isUsernameTaken(data.username)) {
      errs['username'] = 'Username is already taken.';
    }
    if (await this.isEmailTaken(data.email)) {
      errs['email'] = 'Email is already registered.';
    }
    if (data.password !== data.confirmPassword) {
      errs['password'] = 'Passwords do not match.';
    }

    return { valid: Object.keys(errs).length === 0, errors: errs };
  }
}

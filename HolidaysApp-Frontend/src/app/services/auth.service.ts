import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from './user.service';
import User from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public user: User | null = null;
  public isAuthenticated: boolean = false;

  constructor(
    private router: Router,
    private userService: UserService
  ) {
    const userSession = localStorage.getItem('userSession');
    if (userSession) {
      this.user = JSON.parse(userSession);
      this.isAuthenticated = true;
    }
  }

  /**
   * Intenta autenticar al usuario contra la API (in-memory para pruebas)
   */
  async login( userInput: string, password: string): Promise<'OK' | 'USER_ERROR' | 'PASS_ERROR'> {
    try {
      const users: User[] = await this.userService.getAllUsers();
      console.log('Users:', users);
      const foundUser = users.find(u =>
        u.username === userInput || u.email === userInput
      );

      if (!foundUser) {
        return 'USER_ERROR';
      }
      if (foundUser.password !== password) {
        return 'PASS_ERROR';
      }

      this.user = {
        username: foundUser.username,
        email: foundUser.email,
        roles: foundUser.roles
      };
      this.isAuthenticated = true;
      localStorage.setItem('userSession', JSON.stringify(this.user));

      return 'OK';
    } catch (error) {
      console.error('Login error:', error);
      return 'USER_ERROR';
    }
  }

  /**
   * Registra un nuevo usuario en la API (in-memory para pruebas)
   */
  async registerUser( user: { username: string; email: string; password: string }): Promise<User> {
    const newUser = await this.userService.addUser({
      ...user,
      roles: ['USUARIO']
    } as any);
    return newUser;
  }

  /**
   * Cierra sesión del usuario
   */
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

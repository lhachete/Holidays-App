import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from './user.service';
import Swal from 'sweetalert2';
import User from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public user: User | null = null;
  public isAuthenticated = false;
  private sessionTimeoutId?: number;

  constructor(
    private router: Router,
    private userService: UserService
  ) {
    const session = localStorage.getItem('userSession');
    if (session) {
      this.user = JSON.parse(session);
      this.isAuthenticated = true;
      this.scheduleSessionExpiration();
    }
  }

  async login(userInput: string, password: string): Promise<'OK' | 'USER_ERROR' | 'SERVER_ERROR'> {
    try {
      const foundUser = await this.userService.login(userInput, password);
      if (!foundUser) return 'USER_ERROR';

      const token = foundUser.token!;
      localStorage.setItem('authToken', token);

      this.user = {
        userId: foundUser.userId,
        username: foundUser.username,
        email: foundUser.email,
        name: foundUser.name,
        lastName: foundUser.lastName,
        codeColor: foundUser.codeColor,
        rol: foundUser.rol
      };

      this.isAuthenticated = true;
      localStorage.setItem('userSession', JSON.stringify(this.user));

      this.scheduleSessionExpiration();
      return 'OK';

    } catch (err: any) {
      if (err.status === 0 || err.status === 500) {
        return 'SERVER_ERROR';
      } else {
        return 'USER_ERROR';
      }
    }
  }

  //Registra un nuevo usuario en la API (in-memory para pruebas)
  async registerUser(user: { username: string; password: string; repeatPassword: string; email: string; name: string, lastName: string, codeColor: string }): Promise<User> {
    console.log('Registrando usuario:', user);
    try {
      const newUser = await this.userService.addUser(user as User);
      return newUser;
    } catch (err: any) {

      if (err.error && err.error.message) {
        console.error('Error al registrar el usuario:', err.error.message);
        throw err.error.message; // envio el mensaje de error
      } else {
        throw 'Unknown error occurred';
      }
    }
  }


  private getTokenExpirationTime = (): number | null => {
    const token = localStorage.getItem('authToken');
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.exp * 1000; // - (590 * 1000)
      } catch {
        return null;
      }
    }
    return null;
  };

  private scheduleSessionExpiration = (): void => {
    this.clearSessionTimeout();

    const expTime = this.getTokenExpirationTime();
    if (expTime) {

      const now = Date.now();
      let timeout = expTime - now;

      if (timeout <= 0) {
        this.sessionExpired();
        return;
      }

      // Programar la expiración de la sesión
      this.sessionTimeoutId = window.setTimeout(() => {
        this.sessionExpired();
      }, timeout);
    }
  };

  private clearSessionTimeout = (): void => {
    if (this.sessionTimeoutId) {
      clearTimeout(this.sessionTimeoutId);
      this.sessionTimeoutId = undefined;
    }
  };

  private sessionExpired = (): void => {
    Swal.fire({
      icon: 'info',
      title: 'Sesión expirada',
      text: 'Tu sesión ha caducado. Por favor, vuelve a iniciar sesión.',
      confirmButtonText: 'Aceptar',
      iconColor: '#213E7F',
      confirmButtonColor: '#213E7F'
    }).then(() => this.logout());
  };


  logout(): void {
    this.isAuthenticated = false;
    this.user = null;
    localStorage.removeItem('userSession');
    localStorage.removeItem('authToken');
    this.router.navigateByUrl('/login');
  }

  get isLoggedIn(): boolean {
    return this.isAuthenticated;
  }

  //Compruebo si el usuario tiene el rol indicado
  hasRole = (rol: string): boolean =>
    this.user?.rol?.name === rol;


  async validatePasswords(data: {
    password: string;
    repeatPassword: string;
  }): Promise<{ valid: boolean; errors: Record<string, string> }> {

    const errs: Record<string, string> = {};

    if (data.password !== data.repeatPassword) {
      errs['password'] = 'Las contraseñas no coinciden.';
    }

    return { valid: Object.keys(errs).length === 0, errors: errs };
  }
}

import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import User from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // Aquí deberían de estar los usuarios reales que se creen en este objeto
  public users: any[] = [
    { id: 1, username: 'admin', email: 'admin@example.com', password: '12345678', roles: ['USUARIO', 'ADMIN'] },
    { id: 2, username: 'user1', email: 'user1@example.com', password: '12345678', roles: ['USUARIO'] }
  ];

  public user: User | null = null;
  public isAuthenticated: boolean = false;

  constructor(private router: Router) {
    const userSession = localStorage.getItem('userSession');
    if (userSession) {
      this.user = JSON.parse(userSession);
      this.isAuthenticated = true;
    }
  }

  login(userInput: string, password: string): 'OK' | 'USER_ERROR' | 'PASS_ERROR' {
    const foundUser = this.users.find(user =>
      user.username === userInput || user.email === userInput
    );

    if (!foundUser) return 'USER_ERROR';
    if (foundUser.password !== password) return 'PASS_ERROR';


    this.user = { username: foundUser.username, email: foundUser.email, roles: foundUser.roles };
    this.isAuthenticated = true;

    localStorage.setItem('userSession', JSON.stringify(this.user));
    return 'OK';
  }

  public registerUser(user: { username: string, email: string, password: string }): void {
    this.users = [...this.users, { ...user, roles: ['USUARIO'] }];
  }


  logout() {
    this.isAuthenticated = false;
    this.user = null;
    localStorage.removeItem('userSession');
    this.router.navigateByUrl("/login");
  }

  get isLoggedIn(): boolean {
    return this.isAuthenticated;
  }

  hasRole(role: string): boolean {
    return this.user?.roles.includes(role) ?? false;
  }

}

import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // Aquí deberían de estar los usuarios reales que se creen en este objeto
  public users: any[] = [
    { username: 'admin', email: 'admin@example.com', password: '1234', roles: ['USUARIO', 'ADMIN'] },
    { username: 'user1', email: 'user1@example.com', password: '1234', roles: ['USUARIO'] }
  ];

  public user: { username: string; password: string; email: string; roles: string[] } | null = null;
  public isAuthenticated: boolean = false;

  constructor(private router: Router) { }

  login(userInput: string, password: string): 'OK' | 'USER_ERROR' | 'PASS_ERROR' {
    const foundUser = this.users.find(user =>
      user.username === userInput || user.email === userInput
    );

    if (!foundUser) return 'USER_ERROR';
    if (foundUser.password !== password) return 'PASS_ERROR';

    this.user = { username: foundUser.username, password: foundUser.password, email: foundUser.email, roles: foundUser.roles };
    this.isAuthenticated = true;
    console.log(this.user); //! <<<<< QUITAR, solo en development
    return 'OK';
  }

  public registerUser(user: { username: string, email: string, password: string }): void {
    this.users = [...this.users, { ...user, roles: ['USUARIO'] }];
  }


  logout() {
    this.isAuthenticated = false;
    this.user = null;
    this.router.navigateByUrl("/login");
  }

  get isLoggedIn(): boolean {
    return this.isAuthenticated;
  }



}

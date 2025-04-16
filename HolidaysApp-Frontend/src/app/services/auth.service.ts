import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

 // Aquí deberían de estar los usuarios reales que se creen en este objeto
  public users: any = { 
    admin: { password: '1234', roles: ['USUARIO', 'ADMIN'] },
    user1: { password: '1234', roles: ['USUARIO'] }
  };

  public username: any;
  public isAuthenticated: boolean = false;
  public roles: string[] = [];

  constructor(private router: Router) { }

  public login(username: string, password: string): 'OK' | 'USER_ERROR' | 'PASS_ERROR' {
    if (!this.users[username]) {
      return 'USER_ERROR';
    }
    if (this.users[username].password !== password) {
      return 'PASS_ERROR';
    }
  
    this.username = username;
    this.isAuthenticated = true;
    this.roles = this.users[username].roles;
    return 'OK';
  }

  logout() {
    this.isAuthenticated = false;
    this.roles = [];
    this.username = undefined;
    this.router.navigateByUrl("/login");
  }

  get isLoggedIn(): boolean {
    return this.isAuthenticated;
  }

  

}

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


  public userInput: any;
  public isAuthenticated: boolean = false;
  public roles: string[] = [];

  constructor(private router: Router) { }

  public login(userInput: string, password: string): 'OK' | 'USER_ERROR' | 'PASS_ERROR' {
    let user: any = null;
    
    if (userInput.includes("@")) {
      user = this.users.find(u => u.email === userInput);
    } else {
      user = this.users.find(u => u.username === userInput);
    }
  
    if (!user) {
      return 'USER_ERROR';
    }
    if (user.password !== password) {
      return 'PASS_ERROR';
    }
  
    this.userInput = user;
    this.isAuthenticated = true;
    this.roles = user.roles;
    return 'OK';
  }

  public registerUser(user: { username: string, email: string, password: string }): void {
    this.users = [...this.users, {...user, roles: ['USUARIO']}];
  }
  

  logout() {
    this.isAuthenticated = false;
    this.roles = [];
    this.userInput = undefined;
    this.router.navigateByUrl("/login");
  }

  get isLoggedIn(): boolean {
    return this.isAuthenticated;
  }

  

}

import { Injectable } from '@angular/core';
import User from '../models/User';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private usersUrl: string;
  private loginUrl: string;
  private registerUrl: string;

  constructor(private api: ApiService) {
    this.usersUrl  = `${this.api.getApiUrl()}/users`;
    this.loginUrl  = `${this.api.getApiUrl()}/users/login`;
    this.registerUrl  = `${this.api.getApiUrl()}/users/register`;
  }

  // === Login via POST /login ===
  login(username: string, password: string): Promise<User> {
    return this.api.post<User>(this.loginUrl, { username, password });
  }

  getAllUsers(): Promise<User[]> {
    return this.api.get<User[]>(this.usersUrl);
  }

  getUserById(id: number): Promise<User> {
    return this.api.get<User>(`${this.usersUrl}/${id}`);
  }

  addUser(user: User): Promise<User> {
    return this.api.post<User>(this.registerUrl, user);
  }

  updateUser(user: User): Promise<User> {
    return this.api.put<User>(`${this.usersUrl}/${user.id}`, user);
  }

  deleteUser(id: number): Promise<void> {
    return this.api.delete<void>(`${this.usersUrl}/${id}`);
  }
}

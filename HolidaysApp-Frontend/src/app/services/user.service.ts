import { Injectable } from '@angular/core';
import User from '../models/User';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  apiUrl: string;

  constructor(private api: ApiService) {
    this.apiUrl = `${this.api.getApiUrl()}/users`;
  }

  getAllUsers(): Promise<User[]> {
    return this.api.get<User[]>(`${this.apiUrl}`);
  }

  getUserById(id: number): Promise<User> {
    return this.api.get<User>(`${this.apiUrl}/${id}`);
  }

  addUser(user: User): Promise<User> {
    return this.api.post<User>(this.apiUrl, user);
  }

  updateUser(user: User): Promise<User> {
    return this.api.put<User>(`${this.apiUrl}/${user.id}`, user);
  }

  deleteUser(id: number): Promise<void> {
    return this.api.delete<void>(`${this.apiUrl}/${id}`);
  }
}

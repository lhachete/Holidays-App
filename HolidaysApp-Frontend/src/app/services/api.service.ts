import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders  } from "@angular/common/http";
import { firstValueFrom } from "rxjs";
import { environment } from "../../environments/environment";

@Injectable({ providedIn: 'root' })
export class ApiService {

    constructor(private http: HttpClient) { }

    private getAuthHeaders(): HttpHeaders {
        const token = localStorage.getItem('authToken');
        console.log('Token de autenticación:', token);
        return new HttpHeaders({
            'Content-Type': 'application/json',
            ...(token ? { 'Authorization': `Bearer ${token}` } : {})
        });
    }

    getApiUrl(): string {
        return environment.apiUrl;
    }

    get<T>(url: string): Promise<T> {
        return firstValueFrom(this.http.get<T>(url, { headers: this.getAuthHeaders() }));
    }

    post<T>(url: string, body: any): Promise<T> {
        return firstValueFrom(this.http.post<T>(url, body, { headers: this.getAuthHeaders() }));
    }

    put<T>(url: string, body: any): Promise<T> {
        return firstValueFrom(this.http.put<T>(url, body, { headers: this.getAuthHeaders() }));
    }

    delete<T>(url: string): Promise<T> {
        return firstValueFrom(this.http.delete<T>(url, { headers: this.getAuthHeaders() }));
    }

}
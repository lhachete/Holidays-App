import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders  } from "@angular/common/http";
import { firstValueFrom } from "rxjs";
import { environment } from "../../environments/environment";

@Injectable({ providedIn: 'root' })
export class ApiService {

    constructor(private http: HttpClient) { }

    private getAuthHeaders(): HttpHeaders {
        const token = localStorage.getItem('authToken');
        return new HttpHeaders({
            'Content-Type': 'application/json',
            ...(token ? { 'Authorization': `Bearer ${token}` } : {})
        });
    }

    //Luego añadir esto enm las peticiones -> , { headers: this.getAuthHeaders() }

    getApiUrl(): string {
        return environment.apiUrl;
    }

    get<T>(url: string): Promise<T> {
        return firstValueFrom(this.http.get<T>(url));
    }

    post<T>(url: string, body: any): Promise<T> {
        return firstValueFrom(this.http.post<T>(url, body));
    }

    put<T>(url: string, body: any): Promise<T> {
        return firstValueFrom(this.http.put<T>(url, body));
    }

    delete<T>(url: string): Promise<T> {
        return firstValueFrom(this.http.delete<T>(url));
    }

}
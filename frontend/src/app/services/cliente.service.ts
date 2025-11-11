import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';

export interface Cliente{
    id?:number,
    nombre:string,
    email:string,
    password:string,
    telefono?:string
}

@Injectable({providedIn: 'root'})
export class ClienteService{
    private apiUrl = `${environment.apiBaseUrl}/clientes`;

    constructor(private http: HttpClient){}

    register(cliente: Cliente): Observable<any>{
        return this.http.post(`${this.apiUrl}/register`, cliente);
    }

    login(email: string, password: string): Observable<any>{
        return this.http.post(`${this.apiUrl}/login`, {email, password});
    }
}


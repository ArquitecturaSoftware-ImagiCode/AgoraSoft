import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';
import { firstValueFrom } from 'rxjs';
import { environment } from '../../environments/environments';
import { Producto } from './producto';


@Injectable({
  providedIn: 'root'
})
export class ProveedorVentaService {
    private apiUrl = `${environment.apiBaseUrl}/productos`;
    

    constructor(
        private http: HttpClient,
        private authService: AuthService
    ) {}

    // Construye encabezados con token del usuario
    private async getAuthHeaders(): Promise<HttpHeaders> {
        const token = await this.authService.getToken();
        return new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
        });
    }

    // Listar todos los productos
    async listar(): Promise<Producto[]> {
        const headers = await this.getAuthHeaders();
        return firstValueFrom(this.http.get<Producto[]>(`${this.apiUrl}`, { headers }));
    }

    // Listar productos del proveedor autenticado
    async listarMisProductos(): Promise<Producto[]> {
        const headers = await this.getAuthHeaders();
        return firstValueFrom(this.http.get<Producto[]>(`${this.apiUrl}/mis-productos`, { headers }));
    }

    // Obtener producto por ID
    async obtener(id: number): Promise<Producto> {
        const headers = await this.getAuthHeaders();
        return firstValueFrom(this.http.get<Producto>(`${this.apiUrl}/${id}`, { headers }));
    }
}

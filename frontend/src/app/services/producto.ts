import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environments';
import { AuthService } from './auth.service';
import { firstValueFrom } from 'rxjs';
import { Producto } from '../models/ItemInventario';


// export interface Producto {
//   id?: number;
//   nombre: string;
//   descripcion?: string;
//   precio: number;
//   categoria?: string;
//   imagenUrl?: string;
//   proveedor: {
//     id: number;
//     nombre?: string;
//     email?: string;
//   };
// }

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private baseUrl = `${environment.apiBaseUrl}/productos`;

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

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
    return firstValueFrom(this.http.get<Producto[]>(this.baseUrl, { headers }));
  }

  // Obtener producto por ID
  async obtener(id: number): Promise<Producto> {
    const headers = await this.getAuthHeaders();
    return firstValueFrom(this.http.get<Producto>(`${this.baseUrl}/${id}`, { headers }));
  }

  // Crear producto
  async crear(p: Producto): Promise<Producto> {
    const headers = await this.getAuthHeaders();
    return firstValueFrom(this.http.post<Producto>(this.baseUrl, p, { headers }));
  }

  // Actualizar producto
  async actualizar(id: number, p: Producto): Promise<Producto> {
    const headers = await this.getAuthHeaders();
    return firstValueFrom(this.http.put<Producto>(`${this.baseUrl}/${id}`, p, { headers }));
  }

  // Eliminar producto
  async eliminar(id: number): Promise<void> {
    const headers = await this.getAuthHeaders();
    await firstValueFrom(this.http.delete<void>(`${this.baseUrl}/${id}`, { headers }));
  }
}

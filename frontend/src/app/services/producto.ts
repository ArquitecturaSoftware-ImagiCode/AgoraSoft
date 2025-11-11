import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environments';

export interface Producto {
  id?: number;
  nombre: string;
  descripcion?: string;
  precio: number;
  categoria?: string;
  imagenUrl?: string;
  proveedor: {
    id: number;
    nombre?: string;
    email?: string;
  };
}

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private baseUrl = `${environment.apiBaseUrl}/productos`;

  constructor(private http: HttpClient) {}

  async listar(): Promise<Producto[]> {
    return await this.http.get<Producto[]>(this.baseUrl).toPromise() as Producto[];
  }

  async obtener(id: number): Promise<Producto> {
    return await this.http.get<Producto>(`${this.baseUrl}/${id}`).toPromise() as Producto;
  }

  async crear(p: Producto): Promise<Producto> {
    return await this.http.post<Producto>(this.baseUrl, p).toPromise() as Producto;
  }

  async actualizar(id: number, p: Producto): Promise<Producto> {
    return await this.http.put<Producto>(`${this.baseUrl}/${id}`, p).toPromise() as Producto;
  }

  async eliminar(id: number): Promise<void> {
    await this.http.delete<void>(`${this.baseUrl}/${id}`).toPromise();
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environments';
import { Venta } from '../models/Venta';

@Injectable({
  providedIn: 'root'
})
export class VentaService {
  private apiUrl = `${environment.apiBaseUrl}/ventas`;

  constructor(private http: HttpClient) {}

  async listarVentas(): Promise<Venta[]> {
    return await this.http.get<Venta[]>(this.apiUrl).toPromise() as Venta[];
  }

  async listarVentasPorUsuario(usuarioId: string): Promise<Venta[]> {
    return await this.http.get<Venta[]>(`${this.apiUrl}/usuario/${usuarioId}`).toPromise() as Venta[];
  }

  async crearVenta(venta: Venta): Promise<Venta> {
    return await this.http.post<Venta>(this.apiUrl, venta).toPromise() as Venta;
  }

  async obtenerVenta(id: number): Promise<Venta> {
    return await this.http.get<Venta>(`${this.apiUrl}/${id}`).toPromise() as Venta;
  }
}


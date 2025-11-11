import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environments';
import { Compra } from '../models/Compra';

@Injectable({
  providedIn: 'root'
})
export class CompraService {
  private apiUrl = `${environment.apiBaseUrl}/compras`;

  constructor(private http: HttpClient) {}

  async listarCompras(): Promise<Compra[]> {
    return await this.http.get<Compra[]>(this.apiUrl).toPromise() as Compra[];
  }

  async listarComprasPorUsuario(usuarioId: string): Promise<Compra[]> {
    return await this.http.get<Compra[]>(`${this.apiUrl}/usuario/${usuarioId}`).toPromise() as Compra[];
  }

  async crearCompra(compra: Compra): Promise<Compra> {
    return await this.http.post<Compra>(this.apiUrl, compra).toPromise() as Compra;
  }

  async obtenerCompra(id: number): Promise<Compra> {
    return await this.http.get<Compra>(`${this.apiUrl}/${id}`).toPromise() as Compra;
  }
}

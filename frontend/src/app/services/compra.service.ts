import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';
import { firstValueFrom } from 'rxjs';
import { environment } from '../../environments/environments';
import { Compra } from '../models/Compra';

@Injectable({
  providedIn: 'root'
})
export class CompraService {
  private apiUrl = `${environment.apiBaseUrl}/compras`;

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

  async listarCompras(): Promise<Compra[]> {
    const headers = await this.getAuthHeaders();
    return firstValueFrom(this.http.get<Compra[]>(this.apiUrl, { headers }));
  }

  async listarComprasPorUsuario(usuarioId: string): Promise<Compra[]> {
    const headers = await this.getAuthHeaders();
    return firstValueFrom(this.http.get<Compra[]>(`${this.apiUrl}/usuario/${usuarioId}`, { headers }));
  }

  async crearCompra(compra: Compra): Promise<Compra> {
    const headers = await this.getAuthHeaders();
    return firstValueFrom(this.http.post<Compra>(this.apiUrl, compra, { headers }));
  }

  async obtenerCompra(id: number): Promise<Compra> {
    const headers = await this.getAuthHeaders();
    return firstValueFrom(this.http.get<Compra>(`${this.apiUrl}/${id}`, { headers }));
  }
}

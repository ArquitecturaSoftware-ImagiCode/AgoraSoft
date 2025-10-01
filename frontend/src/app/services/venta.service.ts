import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Venta } from '../models/Venta';

@Injectable({
  providedIn: 'root'
})
export class VentaService {
  private apiUrl = 'http://localhost:8080/ventas';  // Cambia la URL si es necesario

  constructor(private http: HttpClient) { }

  // Obtener todas las ventas
  getAllVentas(): Observable<Venta[]> {
    return this.http.get<Venta[]>(`${this.apiUrl}`);
  }

  // Obtener ventas de un local específico por localId
  getVentasByLocalId(localId: number): Observable<Venta[]> {
    return this.http.get<Venta[]>(`${this.apiUrl}/local/${localId}`);
  }

  eliminarVenta(id: number): Observable<void> {
  return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class InventarioService {
  private apiUrl = `${environment.apiBaseUrl}/inventarios`;

  constructor(private http: HttpClient) {}

  // Obtener todos los inventarios
  async getInventarios(): Promise<any[]> {
    return await this.http.get<any[]>(this.apiUrl).toPromise() as any[];
  }

  // Obtener inventario por usuario
  async getInventarioPorUsuario(usuarioId: string): Promise<any> {
    return await this.http.get<any>(`${this.apiUrl}/usuario/${usuarioId}`).toPromise() as any;
  }

  // Crear o actualizar inventario
  async crearInventario(inventarioData: any): Promise<any> {
    return await this.http.post<any>(this.apiUrl, inventarioData).toPromise() as any;
  }

  // Eliminar inventario (si deseas)
  async eliminarInventario(id: number): Promise<void> {
    await this.http.delete<void>(`${this.apiUrl}/${id}`).toPromise();
  }
}

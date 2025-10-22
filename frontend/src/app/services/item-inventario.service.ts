import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environments';
import { ItemInventario, Inventario } from '../models/ItemInventario';

@Injectable({
  providedIn: 'root'
})
export class ItemInventarioService {
  private baseUrl = `${environment.apiBaseUrl}/items-inventario`;
  private inventarioUrl = `${environment.apiBaseUrl}/inventarios`;

  constructor(private http: HttpClient) {}

  // Métodos para ItemInventario
  async listar(): Promise<ItemInventario[]> {
    return await this.http.get<ItemInventario[]>(this.baseUrl).toPromise() as ItemInventario[];
  }

  async listarPorInventario(inventarioId: string): Promise<ItemInventario[]> {
    return await this.http.get<ItemInventario[]>(`${this.baseUrl}/inventario/${inventarioId}`).toPromise() as ItemInventario[];
  }

  async obtener(id: number): Promise<ItemInventario> {
    return await this.http.get<ItemInventario>(`${this.baseUrl}/${id}`).toPromise() as ItemInventario;
  }

  async crear(itemInventario: ItemInventario): Promise<ItemInventario> {
    return await this.http.post<ItemInventario>(this.baseUrl, itemInventario).toPromise() as ItemInventario;
  }

  async actualizar(id: number, itemInventario: ItemInventario): Promise<ItemInventario> {
    return await this.http.put<ItemInventario>(`${this.baseUrl}/${id}`, itemInventario).toPromise() as ItemInventario;
  }

  async eliminar(id: number): Promise<void> {
    await this.http.delete<void>(`${this.baseUrl}/${id}`).toPromise();
  }

  async agregarProducto(inventarioId: number, productoId: number, cantidad: number): Promise<ItemInventario> {
    return await this.http.post<ItemInventario>(`${this.baseUrl}/agregar`, null, {
      params: {
        inventarioId: inventarioId.toString(),
        productoId: productoId.toString(),
        cantidad: cantidad.toString()
      }
    }).toPromise() as ItemInventario;
  }

  async actualizarCantidad(inventarioId: string, itemId: number, cantidad: number): Promise<ItemInventario> {
    return await this.http.put<ItemInventario>(
      `${this.baseUrl}/inventario/${inventarioId}/item/${itemId}`, 
      { cantidad: cantidad }
    ).toPromise() as ItemInventario;
  }

  // Métodos para Inventario
  async obtenerInventarioPorUsuario(usuarioId: number): Promise<Inventario> {
    return await this.http.get<Inventario>(`${this.inventarioUrl}/usuario/${usuarioId}`).toPromise() as Inventario;
  }

  async crearInventario(inventario: Inventario): Promise<Inventario> {
    return await this.http.post<Inventario>(this.inventarioUrl, inventario).toPromise() as Inventario;
  }

  async obtenerInventario(id: number): Promise<Inventario> {
    return await this.http.get<Inventario>(`${this.inventarioUrl}/${id}`).toPromise() as Inventario;
  }
}

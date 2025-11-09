import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';
import { AuthService } from './auth.service';
import { Usuario } from '../models/ItemInventario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = `${environment.apiBaseUrl}/usuarios`;
  
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

  // GET: traer todos los usuarios
  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.apiUrl);
  }

  // GET: traer usuario por id
  getUsuarioPorId(id: string): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/${id}`);
  }

  // POST: crear usuario
  crearUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.apiUrl, usuario);
  }

  // PUT: actualizar usuario por id
  actualizarUsuario(id: string, usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.apiUrl}/${id}`, usuario);
  }

  // DELETE: eliminar usuario por id
  eliminarUsuario(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  listarOperadores(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/operadores`);
  }
}

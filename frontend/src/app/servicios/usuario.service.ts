import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Usuario } from '../modelos/Usuario';
import { environment } from '../../environments/environments';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = `${environment.apiBaseUrl}/usuarios`;

  constructor(private http: HttpClient, @Inject(PLATFORM_ID) private plataformId :Object) {}

  // GET: traer todos los usuarios
  getUsuarios(): Observable<Usuario[]> {
    if (isPlatformBrowser(this.plataformId)) {
      return this.http.get<Usuario[]>(this.apiUrl);
    }
    return of([]); // SSR: no llames al backend
  }

  // GET: traer usuario por id
  getUsuarioPorId(id: number): Observable<Usuario> {
    if (isPlatformBrowser(this.plataformId)) {
      return this.http.get<Usuario>(`${this.apiUrl}/${id}`);
    }
    return of({} as Usuario);
  }

  // POST: crear usuario
  crearUsuario(usuario: Usuario): Observable<Usuario> {
    if (isPlatformBrowser(this.plataformId)) {
      return this.http.post<Usuario>(this.apiUrl, usuario);
    }
    return of({} as Usuario);
  }

  // PUT: actualizar usuario por id
  actualizarUsuario(id: number, usuario: Usuario): Observable<Usuario> {
    if (isPlatformBrowser(this.plataformId)) {
      return this.http.put<Usuario>(`${this.apiUrl}/${id}`, usuario);
    }
    return of({} as Usuario);
  }

  // DELETE: eliminar usuario por id
  eliminarUsuario(id: number): Observable<void> {
    if (isPlatformBrowser(this.plataformId)) {
      return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
    return of();
  }


}

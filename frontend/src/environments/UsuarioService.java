import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Usuario } from '../modelos/Usuario';
import { environment } from '../../environments/environment';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = ${environment.apiUrl}/usuarios;

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  getUsuarios(): Observable<Usuario[]> {
    if (isPlatformBrowser(this.platformId)) {
      return this.http.get<Usuario[]>(this.apiUrl);
    }
    return of([]); // SSR: no llames al backend
  }

  getUsuarioPorId(id: number): Observable<Usuario> {
    if (isPlatformBrowser(this.platformId)) {
      return this.http.get<Usuario>(${this.apiUrl}/${id});
    }
    return of({} as Usuario);
  }

  crearUsuario(usuario: Usuario): Observable<Usuario> {
    if (isPlatformBrowser(this.platformId)) {
      return this.http.post<Usuario>(this.apiUrl, usuario);
    }
    return of({} as Usuario);
  }

  actualizarUsuario(id: number, usuario: Usuario): Observable<Usuario> {
    if (isPlatformBrowser(this.platformId)) {
      return this.http.put<Usuario>(${this.apiUrl}/${id}, usuario);
    }
    return of({} as Usuario);
  }

  eliminarUsuario(id: number): Observable<void> {
    if (isPlatformBrowser(this.platformId)) {
      return this.http.delete<void>(${this.apiUrl}/${id});
    }
    return of();
  }
}

import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { CommonModule, JsonPipe } from '@angular/common';
import {environment} from '../../../../environments/environments';

@Component({
  selector: 'app-operador-dashboard',
  imports: [JsonPipe, CommonModule],
  templateUrl: './operador-dashboard.html',
  styleUrl: './operador-dashboard.css'
})
export class OperadorDashboard implements OnInit {
  token: string = '';
  usuario: any = null;

  constructor(private authService: AuthService) {}

  async ngOnInit() {
    // Espera a que Clerk esté inicializado y el usuario esté autenticado
    if (!this.authService.isSignedIn()) {
      console.warn('Usuario no autenticado. Redirigiendo a login...');
      // Aquí podrías redirigir al login si lo deseas
      return;
    }
    this.token = (await this.authService.getToken()) || '';
    console.log('Token obtenido:', this.token);
    if (!this.token) {
      console.error('No se obtuvo un token válido.');
      return;
    }
    try {
      const response = await fetch(`${environment.apiBaseUrl}/usuario`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${this.token.toString()}`,
        },
        credentials: 'include', // 🔹 si usas cookies en Clerk
      });
      if (!response.ok) {
        throw new Error('Error HTTP: ' + response.status);
      }
      this.usuario = await response.json();
    } catch (error) {
      console.error('Error al obtener usuario:', error);
    }
  }
}

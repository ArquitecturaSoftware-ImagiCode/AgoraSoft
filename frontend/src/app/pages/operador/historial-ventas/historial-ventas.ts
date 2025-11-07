import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { VentaService } from '../../../services/venta.service';
import { AuthService } from '../../../services/auth.service';
import { Venta } from '../../../models/Venta';

@Component({
  selector: 'app-historial-ventas',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './historial-ventas.html',
  styleUrl: './historial-ventas.css'
})
export class HistorialVentasComponent implements OnInit {
  ventas: Venta[] = [];
  ventaSeleccionada: Venta | null = null;
  cargando: boolean = false;

  constructor(
    private ventaService: VentaService,
    private authService: AuthService
  ) {}

  async ngOnInit() {
    await this.cargarVentas();
  }

  async cargarVentas() {
    try {
      this.cargando = true;
      
      // Obtener el ID del usuario logueado
      const usuarioId = await this.authService.getUserId();
      if (!usuarioId) {
        console.error('No se pudo obtener el ID del usuario');
        this.ventas = [];
        this.cargando = false;
        return;
      }

      this.ventas = await this.ventaService.listarVentasPorUsuario(usuarioId);
      console.log('Ventas cargadas:', this.ventas);
      this.cargando = false;
    } catch (error) {
      console.error('Error al cargar ventas:', error);
      this.ventas = [];
      this.cargando = false;
    }
  }

  verDetalle(venta: Venta) {
    this.ventaSeleccionada = venta;
  }

  cerrarDetalle() {
    this.ventaSeleccionada = null;
  }

  formatearFecha(fecha: string): string {
    return new Date(fecha).toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  getTotalVentas(): number {
    return this.ventas.reduce((total, venta) => total + venta.total, 0);
  }

  getTotalProductosVendidos(): number {
    return this.ventas.reduce((total, venta) => {
      return total + venta.detalles.reduce((sum, detalle) => sum + detalle.cantidad, 0);
    }, 0);
  }
}


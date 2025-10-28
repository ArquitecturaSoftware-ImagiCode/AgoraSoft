import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CompraService } from '../../../services/compra.service';
import { AuthService } from '../../../services/auth.service';
import { Compra } from '../../../models/Compra';

@Component({
  selector: 'app-historial-compras',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './historial-compras.html',
  styleUrl: './historial-compras.css'
})
export class HistorialComprasComponent implements OnInit {
  compras: Compra[] = [];
  compraSeleccionada: Compra | null = null;
  cargando: boolean = false;

  constructor(
    private compraService: CompraService,
    private authService: AuthService
  ) {}

  async ngOnInit() {
    await this.cargarCompras();
  }

  async cargarCompras() {
    try {
      this.cargando = true;
      
      // Obtener el ID del usuario logueado
      const usuarioId = await this.authService.getUserId();
      if (!usuarioId) {
        console.error('No se pudo obtener el ID del usuario');
        this.compras = [];
        this.cargando = false;
        return;
      }

      this.compras = await this.compraService.listarComprasPorUsuario(usuarioId);
      console.log('Compras cargadas:', this.compras);
      this.cargando = false;
    } catch (error) {
      console.error('Error al cargar compras:', error);
      this.compras = [];
      this.cargando = false;
    }
  }

  verDetalle(compra: Compra) {
    this.compraSeleccionada = compra;
  }

  cerrarDetalle() {
    this.compraSeleccionada = null;
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
}

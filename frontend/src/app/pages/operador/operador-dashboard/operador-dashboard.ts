import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { ItemInventarioService } from '../../../services/item-inventario.service';
import { CompraService } from '../../../services/compra.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-operador-dashboard',
  imports: [CommonModule, RouterLink],
  templateUrl: './operador-dashboard.html',
  styleUrl: './operador-dashboard.css'
})
export class OperadorDashboard implements OnInit {
  usuario: any = null;
  cargando = true;
  
  // Estadísticas
  totalProductosInventario = 0;
  totalUnidadesInventario = 0;
  valorTotalInventario = 0;
  totalComprasRealizadas = 0;
  comprasRecientes: any[] = [];
  productosRecientes: any[] = [];

  constructor(
    private authService: AuthService,
    private itemInventarioService: ItemInventarioService,
    private compraService: CompraService
  ) {}

  async ngOnInit() {
    await this.cargarDatosUsuario();
    await this.cargarEstadisticas();
  }

  async cargarDatosUsuario() {
    try {
      const clerkUser = await this.authService.getClerkInstance().user;
      if (clerkUser) {
        this.usuario = {
          nombre: clerkUser.firstName,
          apellido: clerkUser.lastName,
          email: clerkUser.emailAddresses?.[0]?.emailAddress,
          rol: clerkUser.unsafeMetadata?.['role'],
          organizacion: clerkUser.unsafeMetadata?.['plaza']
        };
      }
    } catch (error) {
      console.error('Error al cargar usuario:', error);
    }
  }

  async cargarEstadisticas() {
    try {
      const usuarioId = await this.authService.getUserId();
      if (!usuarioId) return;

      const inventarioId = `i_${usuarioId}`;
      
      // Cargar inventario
      const itemsInventario = await this.itemInventarioService.listarPorInventario(inventarioId);
      this.totalProductosInventario = itemsInventario.length;
      this.totalUnidadesInventario = itemsInventario.reduce((sum, item) => sum + item.cantidad, 0);
      this.valorTotalInventario = itemsInventario.reduce((sum, item) => 
        sum + (item.producto.precio * item.cantidad), 0
      );
      
      // Productos recientes (últimos 5)
      this.productosRecientes = itemsInventario.slice(0, 5);
      
      // Cargar compras
      const compras = await this.compraService.listarComprasPorUsuario(usuarioId);
      this.totalComprasRealizadas = compras.length;
      this.comprasRecientes = compras.slice(0, 3);
      
      this.cargando = false;
    } catch (error) {
      console.error('Error al cargar estadísticas:', error);
      this.cargando = false;
    }
  }

  get saludo(): string {
    const hora = new Date().getHours();
    if (hora < 12) return 'Buenos días';
    if (hora < 19) return 'Buenas tardes';
    return 'Buenas noches';
  }
}

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ItemInventarioService } from '../../../services/item-inventario.service';
import { VentaService } from '../../../services/venta.service';
import { AuthService } from '../../../services/auth.service';
import { Venta, DetalleVenta } from '../../../models/Venta';
import { ItemInventario } from '../../../models/ItemInventario';

export interface ProductoSeleccionado {
  itemInventario: ItemInventario;
  cantidad: number;
  stockDisponible: number;
}

@Component({
  selector: 'app-ventas',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './ventas.html',
  styleUrl: './ventas.css'
})
export class VentasComponent implements OnInit {
  itemsInventario: ItemInventario[] = [];
  productosSeleccionados: ProductoSeleccionado[] = [];
  cargando: boolean = false;
  procesandoVenta: boolean = false;
  inventarioId: string = "";
  clienteNombre: string = "";

  constructor(
    private itemInventarioService: ItemInventarioService,
    private ventaService: VentaService,
    private authService: AuthService,
    private router: Router
  ) {}

  async ngOnInit() {
    await this.inicializarInventario();
  }

  async inicializarInventario() {
    try {
      this.cargando = true;
      
      // Obtener el ID del usuario logueado
      const usuarioId = await this.authService.getUserId();
      if (!usuarioId) {
        alert('Error: No se pudo obtener el usuario logueado.');
        this.cargando = false;
        return;
      }

      // Construir el ID del inventario usando el patrón i_{usuarioId}
      this.inventarioId = `i_${usuarioId}`;
      
      // Cargar los items del inventario
      await this.cargarInventario();
      
      this.cargando = false;
    } catch (error) {
      console.error('Error al inicializar inventario:', error);
      this.cargando = false;
    }
  }

  async cargarInventario() {
    try {
      const items = await this.itemInventarioService.listarPorInventario(this.inventarioId);
      // Filtrar solo productos con stock disponible
      this.itemsInventario = items.filter(item => item.cantidad > 0);
      console.log('Items de inventario cargados:', this.itemsInventario);
    } catch (error) {
      console.error('Error al cargar items del inventario:', error);
      this.itemsInventario = [];
    }
  }

  agregarProducto(itemInventario: ItemInventario) {
    const existe = this.productosSeleccionados.find(p => p.itemInventario.id === itemInventario.id);
    if (existe) {
      // Validar que no exceda el stock disponible
      if (existe.cantidad < existe.stockDisponible) {
        existe.cantidad++;
      } else {
        alert(`Stock insuficiente. Disponible: ${existe.stockDisponible}`);
      }
    } else {
      this.productosSeleccionados.push({ 
        itemInventario, 
        cantidad: 1,
        stockDisponible: itemInventario.cantidad
      });
    }
  }

  quitarProducto(itemId: number) {
    this.productosSeleccionados = this.productosSeleccionados.filter(
      p => p.itemInventario.id !== itemId
    );
  }

  actualizarCantidad(itemId: number, cantidad: number) {
    const producto = this.productosSeleccionados.find(p => p.itemInventario.id === itemId);
    if (producto) {
      if (cantidad <= 0) {
        this.quitarProducto(itemId);
      } else if (cantidad > producto.stockDisponible) {
        alert(`Stock insuficiente. Disponible: ${producto.stockDisponible}`);
        producto.cantidad = producto.stockDisponible;
      } else {
        producto.cantidad = cantidad;
      }
    }
  }

  get totalVenta(): number {
    return this.productosSeleccionados.reduce((total, item) => {
      return total + (item.itemInventario.producto.precio! * item.cantidad);
    }, 0);
  }

  get cantidadTotalProductos(): number {
    return this.productosSeleccionados.reduce((total, item) => total + item.cantidad, 0);
  }

  limpiarSeleccion() {
    if (confirm('¿Estás seguro de que quieres limpiar toda la selección?')) {
      this.productosSeleccionados = [];
      this.clienteNombre = "";
    }
  }

  async procesarVenta() {
    if (this.productosSeleccionados.length === 0) {
      alert('No has seleccionado ningún producto.');
      return;
    }

    // Validar stock antes de procesar
    for (const item of this.productosSeleccionados) {
      if (item.cantidad > item.stockDisponible) {
        alert(`Stock insuficiente para ${item.itemInventario.producto.nombre}. Disponible: ${item.stockDisponible}`);
        return;
      }
    }

    if (!confirm(`¿Confirmar venta por $${this.totalVenta.toLocaleString('es-CO')}?`)) {
      return;
    }

    try {
      this.procesandoVenta = true;

      // Obtener el ID del usuario logueado
      const usuarioId = await this.authService.getUserId();
      if (!usuarioId) {
        alert('Error: No se pudo obtener el usuario logueado.');
        this.procesandoVenta = false;
        return;
      }

      const venta: Venta = {
        usuario: { id: usuarioId },
        clienteNombre: this.clienteNombre || undefined,
        fechaVenta: new Date().toISOString(),
        total: this.totalVenta,
        detalles: this.productosSeleccionados.map(item => ({
          producto: {
            id: item.itemInventario.producto.id!,
            nombre: item.itemInventario.producto.nombre!,
            precio: item.itemInventario.producto.precio!
          },
          cantidad: item.cantidad,
          precioUnitario: item.itemInventario.producto.precio!,
          subtotal: item.itemInventario.producto.precio! * item.cantidad
        }))
      };

      const ventaGuardada = await this.ventaService.crearVenta(venta);
      console.log('Venta registrada:', ventaGuardada);

      this.procesandoVenta = false;

      alert(`¡Venta realizada exitosamente!\n\nProductos vendidos: ${this.cantidadTotalProductos}\nTotal: $${this.totalVenta.toLocaleString('es-CO')}`);

      // Limpiar selección
      this.productosSeleccionados = [];
      this.clienteNombre = "";

      // Recargar inventario para actualizar stocks
      await this.cargarInventario();

      // Navegar al historial de ventas
      this.router.navigate(['/operador/historial-ventas']);

    } catch (error: any) {
      this.procesandoVenta = false;
      console.error('Error al procesar la venta:', error);
      const mensaje = error.error?.message || error.message || 'Error al procesar la venta. Por favor, intenta de nuevo.';
      alert(mensaje);
    }
  }

  getStockDisponible(itemInventario: ItemInventario): number {
    const seleccionado = this.productosSeleccionados.find(p => p.itemInventario.id === itemInventario.id);
    if (seleccionado) {
      return seleccionado.stockDisponible - seleccionado.cantidad;
    }
    return itemInventario.cantidad;
  }
}


import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ProductoService} from '../../../services/producto';
import { CompraService } from '../../../services/compra.service';
import { AuthService } from '../../../services/auth.service';
import { ProveedorVentaService } from '../../../services/proveedor.venta.service';
import { Producto, Usuario} from '../../../models/ItemInventario';
import { UsuarioService } from '../../../services/usuario.service';
import { Compra } from '../../../models/Compra';
import { firstValueFrom } from 'rxjs';

export interface ProductoSeleccionado {
  producto: Producto;
  cantidad: number;
}

@Component({
  selector: 'app-prov-ventas',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule],
  templateUrl: './prov-ventas.html',
  styleUrl: './prov-ventas.css'
})

export class ProvVentas implements OnInit{productos: Producto[] = [];
  productosSeleccionados: ProductoSeleccionado[] = [];
  cargando: boolean = false;
  procesandoCompra: boolean = false;
  operadores: Usuario[] = [];
  productosFiltrados: any[] = []; // nueva lista filtrada
  operadorSeleccionado: string = '';
  terminoBusqueda: string = '';


  constructor(
    private proveedorService: ProveedorVentaService,
    private usuarioService: UsuarioService,
    private productoService: ProductoService,
    private compraService: CompraService,
    private authService: AuthService,
    private router: Router
  ) {}

  async ngOnInit() {
      this.cargarOperadores();
    await this.cargarProductos();
  }


  cargarOperadores(): void {
    this.usuarioService.listarOperadores().subscribe({
      next: (data) => {
        this.operadores = data;
        console.log('Operadores cargados:', this.operadores);
      },
      error: (error) => {
        console.error('Error al cargar operadores:', error);
        alert('No se pudieron cargar los operadores.');
      }
    });
  }

  buscarProducto() {
    const termino = this.terminoBusqueda.toLowerCase();
    this.productosFiltrados = this.productos.filter(
      p => p.nombre.toLowerCase().includes(termino) || p.categoria?.toLowerCase().includes(termino)
    );
  }



  async cargarProductos() {
    this.cargando = true;
    try {
      // Intentar obtener productos reales del backend
      this.productos = await this.proveedorService.listarMisProductos();
      console.log('Productos cargados desde backend:', this.productos);

      // Si el backend devolvió un arreglo vacío, usa los mock
      if (!this.productos || this.productos.length === 0) {
        console.warn('Backend vacío, usando productos de prueba...');
      }

    } catch (error) {
      // Si falla la conexión, usar mock
      console.error('Error al cargar productos:', error);
      console.warn('Usando productos de prueba (mock).');
    } finally {
      this.cargando = false;
    }
  }


  agregarProducto(producto: Producto) {
    const existe = this.productosSeleccionados.find(p => p.producto.id === producto.id);
    if (existe) {
      existe.cantidad++;
    } else {
      this.productosSeleccionados.push({ producto, cantidad: 1 });
    }
  }

  quitarProducto(productoId: number) {
    this.productosSeleccionados = this.productosSeleccionados.filter(
      p => p.producto.id !== productoId
    );
  }

  actualizarCantidad(productoId: number, cantidad: number) {
    const producto = this.productosSeleccionados.find(p => p.producto.id === productoId);
    if (producto) {
      if (cantidad <= 0) {
        this.quitarProducto(productoId);
      } else {
        producto.cantidad = cantidad;
      }
    }
  }

  get totalCompra(): number {
    return this.productosSeleccionados.reduce((total, item) => {
      return total + (item.producto.precio * item.cantidad);
    }, 0);
  }

  get cantidadTotalProductos(): number {
    return this.productosSeleccionados.reduce((total, item) => total + item.cantidad, 0);
  }

  limpiarSeleccion() {
    if (confirm('¿Estás seguro de que quieres limpiar toda la selección?')) {
      this.productosSeleccionados = [];
    }
  }

  async procesarCompra() {
    if (this.productosSeleccionados.length === 0) {
      alert('No has seleccionado ningún producto.');
      return;
    }

    if (!this.operadorSeleccionado) {
      alert('Por favor selecciona un operador antes de procesar la venta.');
      return;
    }

    if (!confirm(`¿Confirmar venta por $${this.totalCompra.toLocaleString('es-CO')} al operador seleccionado?`)) {
      return;
    }

    try {
      this.procesandoCompra = true;

      // Obtener el usuario logueado (proveedor actual)
      const proveedor = await this.authService.getUser();
      if (!proveedor) {
        alert('Error: No se pudo obtener el proveedor logueado.');
        this.procesandoCompra = false;
        return;
      }
      
      // Obtener el operador seleccionado desde la API
      const operador = await firstValueFrom(
        this.usuarioService.getUsuarioPorId(this.operadorSeleccionado)
      );

      
      if (!operador) {
        alert('Operador no válido.');
        this.procesandoCompra = false;
        return;
      }

      // Crear la venta (que en la BD se registra como una compra del operador)
      const total = this.productosSeleccionados.reduce(
        (sum, item) => sum + item.producto.precio * item.cantidad,
        0
      );

      const venta: Compra = {
        usuario: operador, // ← el comprador es el operador seleccionado
        proveedor: proveedor, // ← el proveedor es el usuario logueado
        fechaCompra: new Date().toISOString(),
        total: total,
        detalles: this.productosSeleccionados.map(item => ({
          producto: {
            id: item.producto.id!,
            nombre: item.producto.nombre,
            precio: item.producto.precio
          },
          cantidad: item.cantidad,
          precioUnitario: item.producto.precio,
          subtotal: item.producto.precio * item.cantidad
        }))
      };

      const ventaGuardada = await this.compraService.crearCompra(venta);
      console.log('Venta registrada exitosamente:', ventaGuardada);

      this.procesandoCompra = false;

      alert(`✅ ¡Venta registrada exitosamente!\n\nProductos vendidos: ${this.cantidadTotalProductos}\nTotal: $${this.totalCompra.toLocaleString('es-CO')}`);

      // Limpiar la selección
      this.productosSeleccionados = [];
      this.operadorSeleccionado = '';

    } catch (error) {
      this.procesandoCompra = false;
      console.error('Error al registrar la venta:', error);
      alert('Error al registrar la venta. Por favor, intenta de nuevo.');
    }
  }

}
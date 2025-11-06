import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ProductoService, Producto } from '../../../services/producto';
import { CompraService } from '../../../services/compra.service';
import { AuthService } from '../../../services/auth.service';
import { Compra } from '../../../models/Compra';

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


  constructor(
    private productoService: ProductoService,
    private compraService: CompraService,
    private authService: AuthService,
    private router: Router
  ) {}

  async ngOnInit() {
    await this.cargarProductos();
  }
/*
  async cargarProductos() {
    this.cargando = true;
    try {
      this.productos = await this.productoService.listar();
      console.log('Productos cargados:', this.productos);
    } catch (error) {
      console.error('Error al cargar productos:', error);
      alert('Error al cargar productos. Verifica que el backend esté corriendo.');
    } finally {
      this.cargando = false;
    }
  }
*/

  async cargarProductos() {
  this.cargando = true;
  try {
    // 🔹 Intentar obtener productos reales del backend
    this.productos = await this.productoService.listar();
    console.log('Productos cargados desde backend:', this.productos);

    // Si el backend devolvió un arreglo vacío, usa los mock
    if (!this.productos || this.productos.length === 0) {
      console.warn('Backend vacío, usando productos de prueba...');
      this.productos = this.getProductosMock();
    }

  } catch (error) {
    // 🔹 Si falla la conexión, usar mock
    console.error('Error al cargar productos:', error);
    console.warn('Usando productos de prueba (mock).');
    this.productos = this.getProductosMock();
  } finally {
    this.cargando = false;
  }
  }


  // 🔹 Productos temporales para pruebas (mock)
getProductosMock(): Producto[] {
  return [
    {
      id: 1,
      nombre: 'Café Molido Premium',
      descripcion: 'Café orgánico tostado medio 500g',
      precio: 18500,
      categoria: 'Alimentos',
      imagenUrl: 'https://placehold.co/100x100',
      proveedor: { id: 101, nombre: 'Distribuidora El Cafetal' }
    },
    {
      id: 2,
      nombre: 'Pan Integral Artesanal',
      descripcion: 'Pan fresco sin conservantes',
      precio: 5500,
      categoria: 'Panadería',
      imagenUrl: 'https://placehold.co/100x100',
      proveedor: { id: 102, nombre: 'Panadería Don Pan' }
    },
    {
      id: 3,
      nombre: 'Leche Deslactosada',
      descripcion: 'Leche deslactosada 1L',
      precio: 4800,
      categoria: 'Lácteos',
      imagenUrl: 'https://placehold.co/100x100',
      proveedor: { id: 103, nombre: 'Lácteos del Valle' }
    },
    {
      id: 4,
      nombre: 'Queso Campesino',
      descripcion: 'Queso fresco artesanal',
      precio: 12500,
      categoria: 'Lácteos',
      imagenUrl: 'https://placehold.co/100x100',
      proveedor: { id: 103, nombre: 'Lácteos del Valle' }
    }
  ];
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

    if (!confirm(`¿Confirmar compra por $${this.totalCompra.toLocaleString('es-CO')}?`)) {
      return;
    }

    try {
      this.procesandoCompra = true;

      // Obtener el ID del usuario logueado
      const usuarioId = await this.authService.getUserId();
      if (!usuarioId) {
        alert('Error: No se pudo obtener el usuario logueado.');
        this.procesandoCompra = false;
        return;
      }

      // Agrupar productos por proveedor
      const productosPorProveedor = new Map<number, ProductoSeleccionado[]>();
      this.productosSeleccionados.forEach(item => {
        const proveedorId = item.producto.proveedor.id;
        if (!productosPorProveedor.has(proveedorId)) {
          productosPorProveedor.set(proveedorId, []);
        }
        productosPorProveedor.get(proveedorId)!.push(item);
      });

      // Crear una compra por cada proveedor
      const comprasCreadas: Compra[] = [];
      for (const [proveedorId, items] of productosPorProveedor) {
        const proveedor = items[0].producto.proveedor;
        const total = items.reduce((sum, item) => sum + (item.producto.precio * item.cantidad), 0);

        const compra: Compra = {
          usuario: { id: usuarioId },
          proveedor: {
            id: proveedorId,
            nombre: proveedor.nombre || 'Proveedor'
          },
          fechaCompra: new Date().toISOString(),
          total: total,
          detalles: items.map(item => ({
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

        const compraGuardada = await this.compraService.crearCompra(compra);
        comprasCreadas.push(compraGuardada);
        console.log('Compra registrada:', compraGuardada);
      }

      this.procesandoCompra = false;

      alert(`¡Compra realizada exitosamente!\n\n${comprasCreadas.length} ${comprasCreadas.length === 1 ? 'orden creada' : 'órdenes creadas'}\nProductos agregados al inventario: ${this.cantidadTotalProductos}\nTotal: $${this.totalCompra.toLocaleString('es-CO')}`);

      // Limpiar selección
      this.productosSeleccionados = [];

      // Navegar al inventario
      this.router.navigate(['/operador/inventario']);

    } catch (error) {
      this.procesandoCompra = false;
      console.error('Error al procesar la compra:', error);
      alert('Error al procesar la compra. Por favor, intenta de nuevo.');
    }
  }
}
import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductoService, Producto } from '../../services/producto';
import { CompraService } from '../../services/compra.service';
import { AuthService } from '../../services/auth.service';
import { Compra } from '../../models/Compra';

export interface ProductoCompra {
  producto: Producto;
  cantidad: number;
  seleccionado: boolean;
}

@Component({
  selector: 'app-comprar-productos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <!-- Modal -->
    <div class="modal fade show d-block" *ngIf="mostrar" tabindex="-1" style="background-color: rgba(0,0,0,0.5);">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">
              <i class="fas fa-shopping-cart"></i> Comprar Productos
            </h5>
            <button type="button" class="btn-close" (click)="cerrar()"></button>
          </div>
          
          <div class="modal-body">
            <!-- Filtro de búsqueda -->
            <div class="mb-3">
              <input 
                type="text" 
                class="form-control" 
                placeholder="Buscar productos..." 
                [(ngModel)]="filtroBusqueda"
                (input)="filtrarProductos()"
              >
            </div>

            <!-- Mensaje de carga -->
            <div *ngIf="cargando" class="text-center py-4">
              <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">Cargando...</span>
              </div>
              <p class="mt-2 text-muted">Cargando productos desde la base de datos...</p>
            </div>

            <!-- Lista de productos -->
            <div class="row" *ngIf="!cargando">
              <div class="col-md-6" *ngFor="let item of productosFiltrados">
                <div class="card mb-3" [class.border-primary]="item.seleccionado">
                  <div class="card-body">
                    <div class="form-check mb-2">
                      <input 
                        class="form-check-input" 
                        type="checkbox" 
                        [(ngModel)]="item.seleccionado"
                        [id]="'producto-' + item.producto.id"
                      >
                      <label class="form-check-label fw-bold" [for]="'producto-' + item.producto.id">
                        {{ item.producto.nombre }}
                      </label>
                    </div>
                    
                    <p class="text-muted small mb-2">{{ item.producto.descripcion || 'Sin descripción' }}</p>
                    <p class="mb-2">
                      <span class="badge bg-secondary me-2">{{ item.producto.categoria || 'Sin categoría' }}</span>
                      <span class="text-success fw-bold">{{ item.producto.precio | currency }}</span>
                    </p>
                    
                    <div *ngIf="item.seleccionado" class="d-flex align-items-center">
                      <label class="form-label me-2 mb-0">Cantidad:</label>
                      <input 
                        type="number" 
                        class="form-control form-control-sm" 
                        [(ngModel)]="item.cantidad"
                        min="1"
                        style="width: 100px;"
                      >
                      <span class="ms-2 text-muted">Total: {{ (item.producto.precio * item.cantidad) | currency }}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Mensaje si no hay productos -->
            <div *ngIf="!cargando && productosFiltrados.length === 0" class="text-center py-4">
              <p class="text-muted">
                <i class="fas fa-exclamation-triangle"></i>
                No se encontraron productos en la base de datos.
              </p>
              <small class="text-muted">
                Asegúrate de que el backend esté corriendo y la base de datos esté poblada.
              </small>
            </div>
          </div>
          
          <div class="modal-footer">
            <div class="me-auto">
              <span class="text-muted">
                Productos seleccionados: {{ getProductosSeleccionados().length }}
                | Total: {{ getTotalCompra() | currency }}
              </span>
            </div>
            <button type="button" class="btn btn-secondary" (click)="cerrar()">
              Cancelar
            </button>
            <button 
              type="button" 
              class="btn btn-success" 
              (click)="procesarCompra()"
              [disabled]="getProductosSeleccionados().length === 0"
            >
              <i class="fas fa-check"></i> Comprar ({{ getProductosSeleccionados().length }})
            </button>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .modal.show {
      display: block !important;
    }
    .card.border-primary {
      border-color: #0d6efd !important;
      box-shadow: 0 0 0 0.2rem rgba(13, 110, 253, 0.25);
    }
  `]
})
export class ComprarProductosComponent implements OnInit {
  @Input() mostrar: boolean = false;
  @Input() inventarioId: number = 0;
  @Output() cerrarModal = new EventEmitter<void>();
  @Output() compraRealizada = new EventEmitter<void>();

  productos: ProductoCompra[] = [];
  productosFiltrados: ProductoCompra[] = [];
  filtroBusqueda: string = '';
  cargando: boolean = false;

  constructor(
    private productoService: ProductoService,
    private compraService: CompraService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    if (this.mostrar) {
      this.cargarProductos();
    }
  }

  ngOnChanges() {
    if (this.mostrar) {
      this.cargarProductos();
    }
  }

  async cargarProductos() {
    this.cargando = true;
    
    try {
      // Cargar productos reales desde la base de datos
      const productos = await this.productoService.listar();
      console.log('Productos cargados desde BD:', productos);
      this.productos = productos.map(producto => ({
        producto,
        cantidad: 1,
        seleccionado: false
      }));
      this.productosFiltrados = [...this.productos];
      this.cargando = false;
    } catch (error) {
      console.error('Error al cargar productos desde la base de datos:', error);
      alert('Error al cargar productos. Verifica que el backend esté corriendo y la base de datos esté poblada.');
      this.cargando = false;
    }
  }


  filtrarProductos() {
    if (!this.filtroBusqueda.trim()) {
      this.productosFiltrados = [...this.productos];
    } else {
      this.productosFiltrados = this.productos.filter(item =>
        item.producto.nombre.toLowerCase().includes(this.filtroBusqueda.toLowerCase()) ||
        item.producto.descripcion?.toLowerCase().includes(this.filtroBusqueda.toLowerCase()) ||
        item.producto.categoria?.toLowerCase().includes(this.filtroBusqueda.toLowerCase())
      );
    }
  }

  getProductosSeleccionados(): ProductoCompra[] {
    return this.productosFiltrados.filter(item => item.seleccionado);
  }

  getTotalCompra(): number {
    return this.getProductosSeleccionados().reduce((total, item) => {
      return total + (item.producto.precio * item.cantidad);
    }, 0);
  }

  async procesarCompra() {
    const productosSeleccionados = this.getProductosSeleccionados();
    
    if (productosSeleccionados.length === 0) {
      alert('Por favor selecciona al menos un producto.');
      return;
    }

    try {
      this.cargando = true;

      // Obtener el ID del usuario logueado
      const usuarioId = await this.authService.getUserId();
      if (!usuarioId) {
        alert('Error: No se pudo obtener el usuario logueado.');
        this.cargando = false;
        return;
      }

      // Agrupar productos por proveedor
      const productosPorProveedor = new Map<number, typeof productosSeleccionados>();
      productosSeleccionados.forEach(item => {
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

        // Registrar la compra (el backend actualiza el inventario automáticamente)
        const compraGuardada = await this.compraService.crearCompra(compra);
        comprasCreadas.push(compraGuardada);
        console.log('Compra registrada:', compraGuardada);
      }

      this.cargando = false;

      // Mostrar resumen de la compra
      const totalProductos = productosSeleccionados.length;
      const totalCosto = this.getTotalCompra();
      
      let mensaje = `¡Compra realizada exitosamente!\n\n`;
      mensaje += `Se ${comprasCreadas.length === 1 ? 'creó 1 orden' : `crearon ${comprasCreadas.length} órdenes`} de compra\n`;
      mensaje += `Productos agregados al inventario: ${totalProductos}\n`;
      mensaje += `Total: ${totalCosto.toLocaleString('es-CO', { style: 'currency', currency: 'COP' })}\n\n`;
      mensaje += `Productos:\n`;
      
      productosSeleccionados.forEach(item => {
        mensaje += `• ${item.producto.nombre} - ${item.cantidad} unidades\n`;
      });
      
      alert(mensaje);
      
      // Emitir evento de compra realizada
      this.compraRealizada.emit();
      this.cerrar();

    } catch (error) {
      this.cargando = false;
      console.error('Error al procesar la compra:', error);
      alert('Error al procesar la compra. Por favor, intenta de nuevo.');
    }
  }

  cerrar() {
    this.filtroBusqueda = '';
    this.productos = [];
    this.productosFiltrados = [];
    this.cerrarModal.emit();
  }
}

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ItemInventarioService } from '../../../services/item-inventario.service';
import { ItemInventario, Inventario } from '../../../models/ItemInventario';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-operador-inventario',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './inventario.html',
  styleUrl: './inventario.css'
})
export class InventarioComponent implements OnInit {
  inventario: Inventario | null = null;
  itemsInventario: ItemInventario[] = [];
  editando: ItemInventario | null = null;
  nuevaCantidad: number = 0;
  inventarioId: string = "";
  cargando: boolean = false;

  constructor(
    private itemInventarioService: ItemInventarioService,
    private authService: AuthService
  ) { }

  async ngOnInit() {
    await this.inicializarInventario();
  }

  async inicializarInventario() {
    try {
      this.cargando = true;
      
      // Obtener el ID del usuario logueado
      const usuarioId = await this.authService.getUserId();
      if (!usuarioId) {
        console.error('No se pudo obtener el ID del usuario');
        alert('Error: No se pudo identificar el usuario. Por favor, inicia sesión nuevamente.');
        this.cargando = false;
        return;
      }

      // Construir el ID del inventario usando el patrón i_{usuarioId}
      this.inventarioId = `i_${usuarioId}`;
      console.log('ID del inventario:', this.inventarioId);

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
      this.itemsInventario = items;
      console.log('Items de inventario cargados del backend:', items);
    } catch (error) {
      console.error('Error al cargar items del inventario:', error);
      this.itemsInventario = [];
    }
  }


  comprarAProveedores() {
    const mensaje = `Funcionalidad de Compra a Proveedores
    
Esta funcionalidad te permitirá:
- Ver catálogo de productos disponibles
- Agregar productos al inventario
- Realizar pedidos a proveedores
- Gestionar compras y entregas
    
Próximamente disponible!`;
    alert(mensaje);
  }

  editarCantidad(item: ItemInventario) {
    this.editando = { ...item };
    this.nuevaCantidad = item.cantidad;
  }

  async guardarCantidad() {
    if (this.editando) {
      try {
        const itemActualizado = await this.itemInventarioService.actualizarCantidad(
          this.inventarioId,
          this.editando.id!,
          this.nuevaCantidad
        );
        await this.cargarInventario();
        this.cancelar();
      } catch (error) {
        console.error('Error al actualizar cantidad:', error);
      }
    }
  }

  async eliminar(item: ItemInventario) {
    if (confirm(`¿Eliminar ${item.producto.nombre} del inventario?`)) {
      if (item.id) {
        try {
          await this.itemInventarioService.eliminar(item.id);
          await this.cargarInventario(); // Recargar la lista completa
        } catch (error) {
          console.error('Error al eliminar item:', error);
        }
      }
    }
  }

  cancelar() {
    this.editando = null;
    this.nuevaCantidad = 0;
  }

  getTotalUnidades(): number {
    return this.itemsInventario.reduce((total, item) => total + item.cantidad, 0);
  }

  getValorTotalInventario(): number {
    return this.itemsInventario.reduce((total, item) => {
      return total + (item.producto.precio * item.cantidad);
    }, 0);
  }

  // Método para debug - verificar conexión con backend
  async verificarConexion() {
    console.log('Verificando conexión con backend...');
    try {
      const items = await this.itemInventarioService.listarPorInventario(this.inventarioId);
      console.log('Conexión exitosa:', items);
    } catch (error) {
      console.error('Error de conexión:', error);
      alert('Error de conexión con el servidor. Verifica que el backend esté corriendo en el puerto correcto.');
    }
  }
}

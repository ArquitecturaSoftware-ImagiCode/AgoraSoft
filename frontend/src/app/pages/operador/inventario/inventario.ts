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
  usuarioId: number = 0;

  constructor(
    private itemInventarioService: ItemInventarioService,
    private authService: AuthService
  ) {}

  async ngOnInit() {
    await this.obtenerUsuarioId();
    this.cargarInventario();
  }

  async obtenerUsuarioId() {
    // Usuario fijo para pruebas
    this.usuarioId = 200;
  }

  async cargarInventario() {
    try {
      const inventario = await this.itemInventarioService.obtenerInventarioPorUsuario(this.usuarioId);
      this.inventario = inventario;
      console.log('Inventario obtenido del backend:', inventario);
      await this.cargarItemsInventario();
    } catch (error) {
      console.error('Error al obtener inventario del usuario:', error);
      // Si no existe inventario, crear uno nuevo
      await this.crearInventarioNuevo();
    }
  }

  private async crearInventarioNuevo() {
    const nuevoInventario = {
      usuarioId: this.usuarioId,
      items: []
    };

    try {
      const inventario = await this.itemInventarioService.crearInventario(nuevoInventario);
      this.inventario = inventario;
      console.log('Nuevo inventario creado:', inventario);
      await this.cargarItemsInventario();
    } catch (error) {
      console.error('Error al crear nuevo inventario:', error);
      // Como fallback, usar inventario ficticio
      this.inventario = {
        id: 7, // ID del inventario que creamos
        usuarioId: this.usuarioId,
        items: []
      };
      await this.cargarItemsInventario();
    }
  }

  async cargarItemsInventario() {
    if (this.inventario?.id) {
      try {
        const items = await this.itemInventarioService.listarPorInventario(this.inventario.id);
        this.itemsInventario = items;
        console.log('Items de inventario cargados:', items);
      } catch (error) {
        console.error('Error al cargar items del inventario:', error);
        // Mantener array vacío si hay error
        this.itemsInventario = [];
      }
    } else {
      // Si no hay inventario, mantener array vacío
      this.itemsInventario = [];
    }
  }

  comprarAProveedores() {
    // Placeholder - por ahora solo muestra un mensaje
    alert('Funcionalidad de compra a proveedores próximamente disponible');
  }

  editarCantidad(item: ItemInventario) {
    this.editando = { ...item };
    this.nuevaCantidad = item.cantidad;
  }

  async guardarCantidad() {
    if (this.editando && this.inventario?.id) {
      try {
        await this.itemInventarioService.actualizarCantidad(
          this.inventario.id,
          this.editando.producto.id!,
          this.nuevaCantidad
        );
        await this.cargarItemsInventario();
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
          await this.cargarItemsInventario();
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
      const inventario = await this.itemInventarioService.obtenerInventarioPorUsuario(this.usuarioId);
      console.log('Conexión exitosa:', inventario);
    } catch (error) {
      console.error('Error de conexión:', error);
      alert('Error de conexión con el servidor. Verifica que el backend esté corriendo en el puerto correcto.');
    }
  }
}

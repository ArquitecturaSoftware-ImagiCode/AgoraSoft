import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Producto } from '../../models/Producto';
import { ProductoService } from '../../services/producto.service';

@Component({
  selector: 'app-producto-pagina',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './producto-pagina.html',
  styleUrls: ['./producto-pagina.css']
})
export class ProductoPagina implements OnInit {
  productos: Producto[] = [];
  productoEditando: Producto | null = null;

  constructor(private productoService: ProductoService) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.productoService.getProductos().subscribe({
      next: (data) => (this.productos = data),
      error: (err) => console.error('Error cargando productos', err),
    });
  }

  crearProducto(nombreProducto: string, seccion: string, cantiLibra: string, preciLibra: string): void {
    const nuevo: Producto = { nombreProducto, seccion, cantiLibra, preciLibra };
    this.productoService.crearProducto(nuevo).subscribe({
      next: (res) => {
        this.productos.push(res);
      },
      error: (err) => console.error('Error creando producto', err),
    });
  }

  eliminarProducto(id: string): void {
    this.productoService.eliminarProducto(id).subscribe({
      next: () => {
        this.productos = this.productos.filter((p) => p.id !== id);
      },
      error: (err) => console.error('Error eliminando producto', err),
    });
  }

  editarProducto(producto: Producto): void {
    this.productoEditando = { ...producto };
  }

  guardarEdicion(): void {
    if (!this.productoEditando || !this.productoEditando.id) return;

    this.productoService.actualizarProducto(this.productoEditando.id, this.productoEditando).subscribe({
      next: () => {
        this.cargarProductos();
        this.productoEditando = null;
      },
      error: (err) => console.error('Error actualizando producto', err),
    });
  }

  cancelarEdicion(): void {
    this.productoEditando = null;
  }
}

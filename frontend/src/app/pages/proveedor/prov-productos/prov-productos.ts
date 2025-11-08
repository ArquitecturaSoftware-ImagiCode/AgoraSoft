import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { environment } from '../../../../environments/environments';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-prov-productos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './prov-productos.html',
  styleUrls: ['./prov-productos.css'],
})
export class ProvProductos {
  nombre = '';
  descripcion = '';
  precio: number | null = null;
  categoria = '';
  imagenUrl = '';

  imagenPreview: string | null = null;

  submitting = false;
  submitted = false;
  message: string | null = null;
  error: string | null = null;

  constructor(private auth: AuthService) {}

  onImagenUrlChange(): void {
    if (!this.imagenUrl) {
      this.imagenPreview = null;
      return;
    }
    // show preview; invalid urls will be cleared by (error) handler in template image
    this.imagenPreview = this.imagenUrl;
  }

  resetForm(): void {
    this.nombre = '';
    this.descripcion = '';
    this.precio = null;
    this.categoria = '';
    this.imagenUrl = '';
    this.imagenPreview = null;
    this.message = null;
    this.error = null;
    this.submitted = false;
  }

  async onSubmit(): Promise<void> {
    this.submitted = true;
    this.message = null;
    this.error = null;

    if (!this.nombre || this.precio === null || !this.categoria) {
      this.error = 'Por favor completa los campos obligatorios (*)';
      return;
    }

    this.submitting = true;

    try {
      const token = await this.auth.getToken();
      const url = `${environment.apiBaseUrl}/productos`;
      const body = {
        nombre: this.nombre,
        descripcion: this.descripcion,
        precio: this.precio,
        categoria: this.categoria,
        imagenUrl: this.imagenUrl ? this.imagenUrl : null,
      };

      const headers: Record<string, string> = { 'Content-Type': 'application/json' };
      if (token) headers['Authorization'] = `Bearer ${token}`;

      const resp = await fetch(url, {
        method: 'POST',
        headers,
        body: JSON.stringify(body),
        credentials: 'include',
      });

      if (resp.ok) {
        const data = await resp.json();
        this.message = 'Producto creado correctamente.';
        // opcional: podríamos lanzar redirect a la vista de productos del proveedor
        this.resetForm();
      } else {
        const text = await resp.text();
        try {
          const j = JSON.parse(text);
          this.error = j.error || j.message || JSON.stringify(j);
        } catch {
          this.error = text || `Error ${resp.status}`;
        }
      }
    } catch (e: any) {
      this.error = e?.message || String(e);
    } finally {
      this.submitting = false;
    }
  }
}

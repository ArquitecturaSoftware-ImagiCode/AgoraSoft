import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Usuario } from '../../models/Usuario';
import { UsuarioService } from '../../services/usuario.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-usuario-pagina',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './usuario-pagina.html',
  styleUrls: ['./usuario-pagina.css']
})
export class UsuarioPagina implements OnInit {
  usuarios: Usuario[] = [];
  usuarioEditando: Usuario | null = null;

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  // GET
  cargarUsuarios(): void {
    this.usuarioService.getUsuarios().subscribe({
      next: (data) => (this.usuarios = data),
      error: (err) => console.error('Error cargando usuarios', err),
    });
  }

  // POST
  crearUsuario(nombre: string, correo: string): void {
    const nuevo = { nombre, correo };
    this.usuarioService.crearUsuario(nuevo).subscribe({
      next: (res) => this.usuarios.push(res),
      error: (err) => console.error('Error creando usuario', err),
    });
  }

  // DELETE
  eliminarUsuario(id: number): void {
    this.usuarioService.eliminarUsuario(id).subscribe({
      next: () => {
        this.usuarios = this.usuarios.filter((u) => u.id !== id);
      },
      error: (err) => console.error('Error eliminando usuario', err),
    });
  }

  // EDITAR
  editarUsuario(usuario: Usuario): void {
    this.usuarioEditando = { ...usuario }; // clonar para no afectar la lista hasta guardar
  }

  guardarEdicion(): void {
    if (!this.usuarioEditando) return;

    this.usuarioService.actualizarUsuario(this.usuarioEditando.id!, this.usuarioEditando).subscribe({
      next: () => {
        // refrescar lista
        this.cargarUsuarios();
        this.usuarioEditando = null;
      },
      error: (err) => console.error('Error actualizando usuario', err),
    });
  }

  cancelarEdicion(): void {
    this.usuarioEditando = null;
  }
}


import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Usuario } from '../../models/Usuario';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-usuario-pagina',
  imports: [CommonModule, FormsModule], //
  templateUrl: './usuario-pagina.html',
  styleUrls: ['./usuario-pagina.css']
})
export class UsuarioPagina implements OnInit {
  usuarios: Usuario[] = [];
  nuevoUsuario: Usuario = new Usuario(0, "", "");

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.usuarioService.getUsuarios().subscribe({
      next: (data) => this.usuarios = data,
      error: (err) => console.error('Error cargando usuarios', err)
    });
  }


}

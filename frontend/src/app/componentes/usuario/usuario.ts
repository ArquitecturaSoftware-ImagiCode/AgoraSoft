import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { isPlatformServer, isPlatformBrowser } from '@angular/common';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Usuario } from '../../modelos/Usuario';
import { UsuarioService } from '../../servicios/usuario.service';

@Component({
  selector: 'app-usuario',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuario.html',
  styleUrls: ['./usuario.css']
})
export class UsuarioComponente implements OnInit {

  nuevoUsuario: Usuario = new Usuario(0, '', '');
  usuarios: Usuario[] = [];

  cargando = true;
  error: string | null = null;

  constructor(
    private usuarioService: UsuarioService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    // Evitar petición HTTP en prerender (SSR build)
    if (isPlatformServer(this.platformId)) {
      this.cargando = false;
      return;
    }
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.cargando = true;
    this.error = null;
    this.usuarioService.getUsuarios().subscribe({
      next: data => {
        this.usuarios = data;
        this.cargando = false;
      },
      error: err => {
        console.error('Error cargando usuarios', err);
        this.error = 'No se pudieron cargar los usuarios.';
        this.cargando = false;
      }
    });
  }

  crearUsuario(): void {
    if (isPlatformServer(this.platformId)) return; // redundante pero seguro
    // Validaciones básicas
    if (!this.nuevoUsuario.nombre || !this.nuevoUsuario.correo) {
      return;
    }
    this.usuarioService.crearUsuario(this.nuevoUsuario).subscribe({
      next: _ => {
        this.nuevoUsuario = new Usuario(0, '', '');
        this.cargarUsuarios(); // refresca
      },
      error: err => console.error('Error creando usuario', err)
    });
  }
}

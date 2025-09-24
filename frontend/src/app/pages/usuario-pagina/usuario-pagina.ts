import {Component, OnInit} from '@angular/core';
import {Usuario} from '../../componentes/usuario/usuario';
import {UsuarioService} from '../../servicios/usuario.service';

@Component({
  selector: 'app-usuario-pagina',
  imports: [],
  templateUrl: './usuario-pagina.html',
  styleUrl: './usuario-pagina.css'
})
export class UsuarioPagina implements OnInit{
  usuarios :Usuario[] =  [];
  usuario: Usuario = new Usuario();
  constructor(private usuarioService: UsuarioService){}

  ngOnInit(): void {
    this.usuarioService.obtenerUsuarioPorId(3).subscribe((data: Usuario) => {
      this.usuario = new Usuario(data.id, data.nombre, data.correo);
    });
  }
}

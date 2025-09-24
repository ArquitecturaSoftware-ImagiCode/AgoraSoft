import { Component } from '@angular/core';

@Component({
  selector: 'app-usuario',
  imports: [],
  templateUrl: './usuario.html',
  styleUrl: './usuario.css'
})
export class Usuario {

  public id?: number;
  public nombre?: string;
  public correo?: string;

  constructor( id?: number, nombre?: string, correo?: string) {
    this.id = id;
    this.nombre = nombre;
    this.correo = correo;
  }
}

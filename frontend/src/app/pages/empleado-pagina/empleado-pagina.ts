import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Empleado } from '../../models/Empleado';
import { EmpleadoService } from '../../services/empleado.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-empleado-pagina',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './empleado-pagina.html',
  styleUrls: ['./empleado-pagina.css']
})
export class EmpleadoPagina implements OnInit {
  empleados: Empleado[] = [];
  empleadoEditando: Empleado | null = null;
  cargando = false;
  error = '';
  nuevoEmpleado: Partial<Empleado> = {
    nombre: '',
    apellido: '',
    correo: '',
    rol: ''
  };

  constructor(private empleadoService: EmpleadoService) {}

  ngOnInit(): void {
    this.cargarEmpleados();
  }

  // GET
  cargarEmpleados(): void {
    this.cargando = true;
    this.empleadoService.getEmpleados().subscribe({
      next: (data) => {
        this.empleados = data;
        this.cargando = false;
      },
      error: (err) => {
        this.error = 'Error cargando empleados';
        console.error(err);
        this.cargando = false;
      },
    });
  }

  // POST
  crearEmpleado(nombre: string, apellido: string, correo: string, rol: string): void {
  const nuevoEmpleado: Empleado = { nombre, apellido, correo, rol, activo: true };
  this.empleadoService.crearEmpleado(nuevoEmpleado).subscribe({
    next: (res) => {
      this.empleados.push(res);
      this.nuevoEmpleado = { nombre: '', apellido: '', correo: '', rol: '' }; // ✅ limpiar formulario
    },
    error: (err) => console.error('Error creando empleado', err),
  });
}


  // DELETE
  eliminarEmpleado(id: string): void {
    this.empleadoService.eliminarEmpleado(id).subscribe({
      next: () => {
        this.empleados = this.empleados.filter((e) => e.id !== id);
      },
      error: (err) => console.error('Error eliminando empleado', err),
    });
  }

  // EDITAR
  editarEmpleado(empleado: Empleado): void {
    this.empleadoEditando = { ...empleado }; // clonar para no afectar la lista hasta guardar
  }

  guardarEdicion(): void {
    if (!this.empleadoEditando) return;

    this.empleadoService.actualizarEmpleado(this.empleadoEditando.id!, this.empleadoEditando).subscribe({
      next: () => {
        this.cargarEmpleados();
        this.empleadoEditando = null;
      },
      error: (err) => console.error('Error actualizando empleado', err),
    });
  }

  cancelarEdicion(): void {
    this.empleadoEditando = null;
  }
}

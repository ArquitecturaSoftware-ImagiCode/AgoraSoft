export interface Empleado {
  id?: string;
  nombre: string;
  apellido: string;
  correo: string;
  rol: string;
  departamento?: string;
  telefono?: string;
  activo?: boolean;
  fechaCreacion?: string;
}

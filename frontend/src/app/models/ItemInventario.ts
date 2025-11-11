export interface Usuario {
  id?: string;       // Coincide con la PK del back-end
  nombre?: string;
  apellido?: string;
  correo?: string;
  rol?: string;
  organizacion?: string;
}

export interface Producto {
  id?: number;
  nombre: string;
  descripcion?: string;
  precio: number;
  unidadMedida?: string;
  categoria?: string;
  imagenUrl?: string;
  usuarioProveedor: Usuario;
}

export interface ItemInventario {
  id?: number;
  inventario: Inventario;
  producto: Producto;
  cantidad: number;
}

export interface Inventario {
  id?: string;
  usuarioId: string;
  items?: ItemInventario[];
}

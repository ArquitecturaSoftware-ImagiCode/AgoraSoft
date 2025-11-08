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

export interface Producto {
  id?: number;
  nombre: string;
  descripcion?: string;
  precio: number;
  categoria?: string;
  imagenUrl?: string;
  usuarioProveedorId?: string;
}

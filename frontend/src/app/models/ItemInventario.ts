export interface ItemInventario {
  id?: number;
  inventario: Inventario;
  producto: Producto;
  cantidad: number;
}

export interface Inventario {
  id?: number;
  usuarioId: number;
  items?: ItemInventario[];
}

export interface Producto {
  id?: number;
  nombre: string;
  descripcion?: string;
  precio: number;
  unidadMedida?: string;
  categoria?: string;
  imagenUrl?: string;
}

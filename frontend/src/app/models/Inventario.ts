export interface Producto {
  id: number;
  nombre?: string;
  precio?: number;
}

export interface ItemInventario {
  id?: number;
  producto: Producto;
  cantidad: number;
}

export interface Inventario {
  id?: number;
  usuarioId: string | number;
  items: ItemInventario[];
}

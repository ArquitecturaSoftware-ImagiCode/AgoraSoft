export interface Compra {
  id?: number;
  usuario: {
    id: string;
  };
  proveedor: {
    id: number;
    nombre: string;
  };
  fechaCompra: string;
  total: number;
  detalles: DetalleCompra[];
}

export interface DetalleCompra {
  id?: number;
  producto: {
    id: number;
    nombre: string;
    precio: number;
  };
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
}

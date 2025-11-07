export interface Venta {
  id?: number;
  usuario: {
    id: string;
  };
  clienteNombre?: string;
  fechaVenta: string;
  total: number;
  detalles: DetalleVenta[];
}

export interface DetalleVenta {
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


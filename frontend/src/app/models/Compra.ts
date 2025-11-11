import { Usuario } from "./ItemInventario";

export interface Compra {
  id?: number;
  usuario: Usuario
  proveedor: Usuario
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

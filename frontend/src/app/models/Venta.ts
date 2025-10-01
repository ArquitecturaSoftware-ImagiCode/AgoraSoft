export class Venta {
  id: number;
  localId: number;
  fecha: string;
  total: number;

  constructor(id: number, localId: number, fecha: string, total: number) {
    this.id = id;
    this.localId = localId;
    this.fecha = fecha;
    this.total = total;
  }
}
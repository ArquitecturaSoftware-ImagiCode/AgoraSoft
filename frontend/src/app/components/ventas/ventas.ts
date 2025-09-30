import { Component, OnInit } from '@angular/core';
import { Venta } from '../../models/Venta';
import { VentaService } from '../../services/venta.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
@Component({
  selector: 'app-ventas',
  imports: [CommonModule,  // Asegúrate de agregarlo aquí
    HttpClientModule],
  templateUrl: './ventas.html',
  styleUrl: './ventas.css'
})


export class VentasComponent implements OnInit {
  ventas: Venta[] = [];
  localId: number = 1; // Por ejemplo, para buscar ventas de un local específico

  constructor(private ventaService: VentaService) { }

  ngOnInit(): void {
    // Obtener todas las ventas
    this.getVentas();

    // Si quieres obtener ventas de un local específico, puedes usar:
    // this.getVentasByLocalId(this.localId);
  }

  // Obtener todas las ventas
  getVentas(): void {
    this.ventaService.getAllVentas().subscribe((data: Venta[]) => {
      this.ventas = data;
    });
  }

  // Obtener ventas por localId
  getVentasByLocalId(localId: number): void {
    this.ventaService.getVentasByLocalId(localId).subscribe((data: Venta[]) => {
      this.ventas = data;
    });
  }
}

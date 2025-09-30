import { Component, OnInit } from '@angular/core';
import { Venta } from '../../models/Venta';
import { VentaService } from '../../services/venta.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ventas',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule],
  templateUrl: './ventas.html',
  styleUrls: ['./ventas.css']
})
export class VentasComponent implements OnInit {
  ventas: Venta[] = [];
  idLocalFiltro: string = '';   // 👈 esta propiedad debe existir
                                // para [(ngModel)]="idLocalFiltro"

  constructor(private ventaService: VentaService) {}

  ngOnInit(): void {
    this.getVentas();
  }

  getVentas(): void {
    this.ventaService.getAllVentas().subscribe({
      next: (data: Venta[]) => (this.ventas = data),
      error: (err) => console.error('Error al obtener ventas', err)
    });
  }

  buscarPorLocal(): void {   // 👈 este método debe existir
    if (this.idLocalFiltro.trim() === '') {
      this.getVentas();
    } else {
      const id = Number(this.idLocalFiltro);
      if (!isNaN(id)) {
        this.ventaService.getVentasByLocalId(id).subscribe({
          next: (data: Venta[]) => (this.ventas = data),
          error: (err) => console.error('Error al buscar por localId', err)
        });
      }
    }
  }
}

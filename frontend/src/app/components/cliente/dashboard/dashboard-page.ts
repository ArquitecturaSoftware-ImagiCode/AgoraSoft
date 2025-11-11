import { Component } from '@angular/core';
import { ClienteDashboardComponent } from '../cliente-dashboard';

@Component({
  selector: 'app-cliente-dashboard-page',
  imports: [ClienteDashboardComponent],
  template: `
    <div class="page">
      <app-cliente-dashboard></app-cliente-dashboard>
    </div>
  `
})
export class ClienteDashboardPage {}

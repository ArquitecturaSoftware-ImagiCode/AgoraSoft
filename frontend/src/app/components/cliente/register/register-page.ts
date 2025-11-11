import { Component } from '@angular/core';
import { ClienteRegisterComponent } from '../cliente-register';

@Component({
  selector: 'app-cliente-register-page',
  imports: [ClienteRegisterComponent],
  template: `
    <div class="page">
      <app-cliente-register></app-cliente-register>
    </div>
  `
})
export class ClienteRegisterPage {}

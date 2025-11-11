import { Component } from '@angular/core';
import { ClienteLoginComponent } from '../cliente-login';

@Component({
  selector: 'app-cliente-login-page',
  imports: [ClienteLoginComponent],
  template: `
    <div class="page">
      <app-cliente-login></app-cliente-login>
    </div>
  `
})
export class ClienteLoginPage {}

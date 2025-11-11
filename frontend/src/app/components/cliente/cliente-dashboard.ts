import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import {LogOut} from 'lucide-angular';

@Component({
  selector: 'app-cliente-dashboard',
  imports: [CommonModule, RouterModule],
  templateUrl: './cliente-dashboard.html',
  styleUrl: './cliente-dashboard.css',
})
export class ClienteDashboardComponent {
  readonly LogoutIcon = LogOut;
  cliente: any = JSON.parse(localStorage.getItem('cliente') || 'null');

  constructor(private router: Router) {}

  logout() {
    localStorage.removeItem('cliente');
    this.router.navigate(['/cliente/login']);
  }
}
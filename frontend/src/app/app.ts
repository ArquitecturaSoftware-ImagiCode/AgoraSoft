import { Component, signal, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  protected readonly title = signal('frontend');
  loading = true;
  signedIn = false;

  constructor(private authService: AuthService) {}

  async ngOnInit() {
    // Espera a que Clerk termine de cargar
    await this.authService.getClerkInstance().load();
    this.signedIn = this.authService.isSignedIn();
    console.log('Clerk cargado, signedIn:', this.signedIn);
    this.loading = false;
  }
}

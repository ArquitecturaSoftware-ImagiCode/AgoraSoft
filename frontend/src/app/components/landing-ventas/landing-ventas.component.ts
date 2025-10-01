import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-landing-ventas',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule  // Necesario para routerLink en el HTML
  ],
  templateUrl: './landing-ventas.component.html',
  styleUrls: ['./landing-ventas.component.css']
})
export class LandingVentasComponent {
  
  // Estado del menú móvil
  isMobileMenuOpen = false;

  constructor() {}

  /**
   * Método para alternar el menú en dispositivos móviles
   */
  toggleMobileMenu(): void {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  /**
   * Método para cerrar el menú móvil cuando se hace clic en un enlace
   */
  closeMobileMenu(): void {
    this.isMobileMenuOpen = false;
  }

  /**
   * Scroll suave hacia una sección específica (opcional)
   * @param sectionId - ID de la sección a la que se desea hacer scroll
   */
  scrollToSection(sectionId: string): void {
    const element = document.getElementById(sectionId);
    if (element) {
      element.scrollIntoView({ 
        behavior: 'smooth', 
        block: 'start' 
      });
    }
  }
}
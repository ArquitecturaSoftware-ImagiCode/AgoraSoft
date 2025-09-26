import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClerkService } from '../../../services/clerk.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.html',
  styleUrls: ['./sign-up.css'],
  standalone: true,
  imports: [CommonModule]
})
export class SignUp implements OnInit, OnDestroy {
  @ViewChild('signUpContainer', { static: true }) signUpContainer!: ElementRef<HTMLDivElement>;
  
  isMounted = false;
  error: string | null = null;

  constructor(private clerkService: ClerkService) {}

  async ngOnInit() {
    try {
      // Inicializa Clerk
      await this.clerkService.initializeClerk();

      // Monta SignUp apenas se inicializa
      this.mountSignUp();
    } catch (err) {
      this.error = 'Error al inicializar Clerk: ' + err;
      console.error(err);
    }
  }

  ngOnDestroy() {
    this.unmountSignUp();
  }

  mountSignUp() {
    if (!this.signUpContainer?.nativeElement) return;
    try {
      this.clerkService.mountSignUp(this.signUpContainer.nativeElement, {
        appearance: {
          elements: { formButtonPrimary: 'bg-blue-500 hover:bg-blue-600 text-white' }
        },
        signInUrl: '/sign-in',
        redirectUrl: '/dashboard'
      });
      this.isMounted = true;
      this.error = null;
    } catch (err) {
      this.error = 'Error al montar SignUp: ' + err;
      console.error(err);
    }
  }

  unmountSignUp() {
    if (!this.signUpContainer?.nativeElement || !this.isMounted) return;
    try {
      this.clerkService.unmountSignUp(this.signUpContainer.nativeElement);
      this.isMounted = false;
      this.error = null;
    } catch (err) {
      this.error = 'Error al desmontar SignUp: ' + err;
      console.error(err);
    }
  }
}

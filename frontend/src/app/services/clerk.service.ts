import { Injectable } from '@angular/core';
import { Clerk } from '@clerk/clerk-js';
import { environment } from '../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class ClerkService {
  private clerk: Clerk | null = null;

  async initializeClerk(): Promise<void> {
    if (!this.clerk) {
      this.clerk = new Clerk(environment.clerkPublicKey);
      await this.clerk.load();
    }
  }

  mountSignUp(container: HTMLDivElement, options?: any): void {
    if (!this.clerk) throw new Error('Clerk no inicializado');
    this.clerk.mountSignUp(container, options);
  }

  unmountSignUp(container: HTMLDivElement): void {
    if (!this.clerk) throw new Error('Clerk no inicializado');
    this.clerk.unmountSignUp(container);
  }

  openSignUp(): void {
    this.clerk?.openSignUp();
  }

  closeSignUp(): void {
    this.clerk?.closeSignUp();
  }
}

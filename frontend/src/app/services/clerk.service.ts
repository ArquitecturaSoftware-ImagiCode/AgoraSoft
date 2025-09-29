import { Injectable } from '@angular/core';
import { Clerk } from '@clerk/clerk-js';
import { environment } from '../environments/environments';

@Injectable({
  providedIn: 'root',
})
export class ClerkService {
  private clerk: Clerk | null = null;

  getClerkInstance(): Clerk | null {
    return this.clerk;
  }

  async initializeClerk(): Promise<void> {
    if (!this.clerk) {
      this.clerk = new Clerk(environment.clerkPublicKey);
      await this.clerk.load();
    }
  }

  async signUp(email: string, password: string) {
    
    return this.clerk?.client?.signUp.create({ emailAddress: email, password });
  }

  mountSignUp(container: HTMLDivElement, options?: any): void {
    if (!this.clerk) throw new Error('Clerk no inicializado');
    this.clerk.mountSignUp(container, options);
  }

  

  unmountSignUp(container: HTMLDivElement): void {
    if (!this.clerk) throw new Error('Clerk no inicializado');
    this.clerk.unmountSignUp(container);
  }

  mountSignIn(container: HTMLDivElement, options?: any): void {
    if (!this.clerk) throw new Error('Clerk no inicializado');
    this.clerk.mountSignIn(container, options);
  }

  unmountSignIn(container: HTMLDivElement): void {
    if (!this.clerk) throw new Error('Clerk no inicializado');
    this.clerk.unmountSignIn(container);
  }
}

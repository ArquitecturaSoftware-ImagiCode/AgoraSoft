import { Injectable } from '@angular/core';
import {
  CanActivate,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  async canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Promise<boolean> {
    // Espera a que Clerk termine de cargar antes de consultar el estado
    await this.authService.getClerkInstance().load();
    const signedIn = this.authService.isSignedIn();

    if (!signedIn) {
      this.router.navigate(['/sign-in'], {
        queryParams: { returnUrl: state.url },
      });
      return false;
    }

    // Verificar rol (si la ruta lo requiere)
    const requiredRole = route.data['role'] as string | undefined;
    if (requiredRole) {
      const userRole = await this.authService.getUserRole();
      if (userRole !== requiredRole) {
        this.router.navigate(['/unauthorized']);
        return false;
      }
    }

    return true;
  }
}

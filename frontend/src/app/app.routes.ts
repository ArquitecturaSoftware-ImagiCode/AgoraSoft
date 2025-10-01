import { Routes } from '@angular/router';
import { SignUpPage } from './pages/auth/sign-up-page/sign-up-page';
import { SignInPage } from './pages/auth/sign-in-page/sign-in-page';
import { ComercialLayout } from './layouts/comercial-layout/comercial-layout';
import { AuthGuard } from './services/auth.guard';
import { ComercialDashboard } from './pages/comercial/comercial-dashboard/comercial-dashboard';
import { ProveedorDashboard } from './pages/proveedor/proveedor-dashboard/proveedor-dashboard';
import { OperadorDashboard } from './pages/operador/operador-dashboard/operador-dashboard';
import { ProveedorLayout } from './layouts/proveedor-layout/proveedor-layout';
import { OperadorLayout } from './layouts/operador-layout/operador-layout';

export const routes: Routes = [
  { path: 'api/register', component: SignUpPage },
  { path: 'api/login', component: SignInPage },
  {
    path: 'api/comercial',
    component: ComercialLayout,
    canActivate: [AuthGuard],
    data: { role: 'api/comercial' },
    children: [
      {
        path: 'api/dashboard',
        component: ComercialDashboard,
      },
    ],
  },
  {
    path: 'api/proveedor',
    component: ProveedorLayout,
    canActivate: [AuthGuard],
    data: { role: 'api/proveedor' },
    children: [
      {
        path: 'api/dashboard',
        component: ProveedorDashboard,
      },
    ],
  },
  {
    path: 'api/operador',
    component: OperadorLayout,
    canActivate: [AuthGuard],
    data: { role: 'api/operador' },
    children: [
      {
        path: 'api/dashboard',
        component: OperadorDashboard,
      },
    ],
  },
];

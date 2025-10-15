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
  { path: 'register', component: SignUpPage },
  { path: '', component: SignInPage },
  {
    path: 'comercial',
    component: ComercialLayout,
    canActivate: [AuthGuard],
    data: { role: 'comercial' },
    children: [
      {
        path: 'dashboard',
        component: ComercialDashboard,
      },
    ],
  },
  {
    path: 'proveedor',
    component: ProveedorLayout,
    canActivate: [AuthGuard],
    data: { role: 'proveedor' },
    children: [
      {
        path: 'dashboard',
        component: ProveedorDashboard,
      },
    ],
  },
  {
    path: 'operador',
    component: OperadorLayout,
    canActivate: [AuthGuard],
    data: { role: 'operador' },
    children: [
      {
        path: 'dashboard',
        component: OperadorDashboard,
      },
    ],
  },
];

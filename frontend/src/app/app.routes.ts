import { Routes } from '@angular/router';
import { SignUpPage } from './pages/auth/sign-up-page/sign-up-page';
import { SignUp } from './components/auth/sign-up/sign-up';

export const routes: Routes = [
  {path: '', component: SignUpPage},
  {path: 'signup', component: SignUp},
];

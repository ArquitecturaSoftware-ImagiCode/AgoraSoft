import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { BriefcaseBusiness, ChartBarIcon, CircleDollarSign, Key, LayoutDashboard, LogOut, LucideAngularModule, NotebookText, User, Users } from 'lucide-angular';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-comercial-layout',
  imports: [RouterOutlet, LucideAngularModule],
  templateUrl: './comercial-layout.html',
  styleUrl: './comercial-layout.css'
})
export class ComercialLayout {
   readonly DashboardIcon = LayoutDashboard;
  readonly ProjectIcon = NotebookText;
  readonly ProfileIcon = User;
  readonly ApplicantsIcon = Users;
  readonly PasswordIcon = Key;
  readonly LogoutIcon = LogOut;
  readonly PlansIcon = CircleDollarSign;
  readonly ChatIcon = ChartBarIcon;
  readonly FreelancersIcon = BriefcaseBusiness;

  constructor(private authService: AuthService, private router: Router) {}


  async cerrarSesion() {
    await this.authService.signOut();
    this.router.navigate(['/login']);
  }
}

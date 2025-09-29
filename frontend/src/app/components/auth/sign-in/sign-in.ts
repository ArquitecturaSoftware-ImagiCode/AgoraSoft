import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-sign-in',
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './sign-in.html',
  styleUrl: './sign-in.css',
})
export class SignIn {
  signInForm: FormGroup;
  submitted = false;

  constructor(private fb: FormBuilder, private clerkService: AuthService, private router: Router) {
    this.signInForm = this.fb.group({
      correo: ['', [Validators.required, Validators.email]],
      contrasena: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  get f() {
    return this.signInForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.signInForm.valid) {
      console.log(this.signInForm.value);

      this.onSignIn();
    }
  }

  async onSignIn() {
    try {
      const result = await this.clerkService.signIn(
        this.signInForm.value.correo,
        this.signInForm.value.contrasena
      );

      console.log('Resultado de inicio de sesion:', result);

      if (result?.status === 'complete') {
        console.log(result.status);
        // Activa la sesión
        await this.clerkService.getClerkInstance()?.setActive({
          session: result.createdSessionId,
        });

        // Espera el rol correctamente
        const role = await this.clerkService.getUserRole();
        if (role) {
          this.router.navigate([`/${role}/dashboard`]);
        } else {
          this.router.navigate(['/dashboard']); // fallback
        }
      }
    } catch (err) {
      console.error('Error en registro:', err);
    }
  }
}

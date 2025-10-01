import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
  FormsModule,
} from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { Router, RouterModule } from '@angular/router';
import {environment} from '../../../../environments/environments';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.html',
  styleUrls: ['./sign-up.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterModule],
})
export class SignUp {
  signUpForm: FormGroup;
  roles = ['comercial', 'proveedor', 'operador'];
  organizaciones = ['Corabastos', 'La concordia', '7 de Agosto'];
  submitted = false;
  step: 'register' | 'verify' = 'register';
  code = '';
  signedIn: any;
  usuario: any;

  constructor(private fb: FormBuilder, private clerkService: AuthService, private router: Router) {
    this.signUpForm = this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      organizacion: ['', Validators.required],
      rol: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      contrasena: ['', [Validators.required, Validators.minLength(6)]],
    });
    this.signedIn = this.clerkService.isSignedIn();
    this.usuario = this.clerkService.getClerkInstance()?.user;
  }

  get f() {
    return this.signUpForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.signUpForm.valid) {
      console.log(this.signUpForm.value);

      this.onRegister();
    }
  }

  async onRegister() {
    try {
      await this.clerkService.signUp(
        this.signUpForm.value.nombre,
        this.signUpForm.value.apellido,
        this.signUpForm.value.correo,
        this.signUpForm.value.contrasena,
        this.signUpForm.value.rol,
        this.signUpForm.value.organizacion
      );
      await this.clerkService.sendVerification();
      this.step = 'verify';
    } catch (err) {
      console.error('Error en registro:', err);
    }
  }

  async onVerify() {
    try {
      const result = await this.clerkService.verifyEmail(this.code);
      if (result?.status === 'complete') {
        await this.clerkService.getClerkInstance()?.setActive({
          session: result.createdSessionId,
        });

        // Obtener usuario de Clerk
        const clerkUser = await this.clerkService.getClerkInstance().user;
        if (clerkUser) {
          // Construir el objeto para el backend
          const usuarioBackend = {
            id: clerkUser.id,
            nombre: clerkUser.firstName,
            apellido: clerkUser.lastName,
            correo: clerkUser.emailAddresses?.[0]?.emailAddress,
            rol: clerkUser.unsafeMetadata?.['role'],
            organizacion: clerkUser.unsafeMetadata?.['plaza']
          };

          
          // Enviar al backend
          await fetch(`${environment.apiBaseUrl}/usuarios`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(usuarioBackend)
          });
        }

        const role = await this.clerkService.getUserRole();
        if (role) {
          this.router.navigate([`/${role}/dashboard`]);
        } else {
          this.router.navigate(['/dashboard']); // fallback
        }
      }
      console.log("HI: " + result?.status)
    } catch (err) {
      console.error('Error en verificación:', err);
    }
  }
}

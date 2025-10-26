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
  
  // Estados de carga y errores
  isLoading = false;
  errorMessage = '';
  isVerifying = false;

  constructor(private fb: FormBuilder, private clerkService: AuthService, private router: Router) {
    this.signUpForm = this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      organizacion: ['', Validators.required],
      rol: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      contrasena: ['', [Validators.required, Validators.minLength(8)]],
    });
    this.signedIn = this.clerkService.isSignedIn();
    this.usuario = this.clerkService.getClerkInstance()?.user;
  }

  get f() {
    return this.signUpForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    this.errorMessage = '';
    
    if (this.signUpForm.valid) {
      this.onRegister();
    }
  }

  async onRegister() {
    this.isLoading = true;
    this.errorMessage = '';
    
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
      this.isLoading = false;
      
    } catch (err: any) {
      console.error('Error en registro:', err);
      this.isLoading = false;
      
      // Mostrar mensaje de error al usuario
      if (err?.errors?.[0]?.message) {
        this.errorMessage = err.errors[0].message;
      } else {
        this.errorMessage = 'Error al crear la cuenta. Verifica que el correo no esté registrado.';
      }
    }
  }

  async onVerify() {
    if (!this.code || this.code.length < 6) {
      this.errorMessage = 'Por favor ingresa el código de 6 dígitos';
      return;
    }
    
    this.isVerifying = true;
    this.errorMessage = '';
    
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
          try {
            await fetch(`${environment.apiBaseUrl}/usuarios`, {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify(usuarioBackend)
            });
          } catch (backendErr) {
            console.error('Error al guardar en backend:', backendErr);
          }
        }

        const role = await this.clerkService.getUserRole();
        if (role) {
          this.router.navigate([`/${role}/dashboard`]);
        } else {
          this.router.navigate(['/dashboard']);
        }
      } else {
        this.errorMessage = 'Código incorrecto. Verifica e intenta de nuevo.';
        this.isVerifying = false;
      }
      
    } catch (err: any) {
      console.error('Error en verificación:', err);
      this.isVerifying = false;
      
      if (err?.errors?.[0]?.message) {
        this.errorMessage = err.errors[0].message;
      } else {
        this.errorMessage = 'Código incorrecto o expirado.';
      }
    }
  }
}

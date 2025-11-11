import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ClienteService, Cliente } from '../../services/cliente.service';

@Component({
    selector:'app-cliente-register',
    imports: [CommonModule, FormsModule, RouterModule],
    templateUrl: './cliente-register.html',
    styleUrl: './cliente-register.css'
})

export class ClienteRegisterComponent{
    step:'register'|'verify' = 'register';
    cliente: Cliente ={nombre:'', email:'', password:'', telefono:''};
    code ='';
    isLoading = false;
    errorMessage='';
    isVerifying= false;

    constructor(private clienteService: ClienteService, private router: Router){}

    onSubmit(){
        this.clienteService.register(this.cliente).subscribe({
            next: () => {
                alert('Registro de cliente exitoso. Por favor Inicie Sesion');
                this.router.navigate(['/cliente/login'])
            },
            error: (err) =>{
                console.error('Error registro cliente', err);
                alert('Error al registrar cliente: ' + (err?.error?.error || err?.message || ''));
            }
        });
    }
}
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ClienteService } from '../../services/cliente.service';

@Component({
selector: 'app-cliente-login',
imports: [CommonModule,FormsModule,RouterModule],
templateUrl:'./cliente-login.html',
styleUrl:'./cliente-login.css'})

export class ClienteLoginComponent{
    email = '';
    password = '';

    constructor(private clienteService: ClienteService, private router: Router){}

    onLogin(){
        this.clienteService.login(this.email, this.password).subscribe({
            next: (res) =>{
                localStorage.setItem('cliente', JSON.stringify(res.cliente));
                this.router.navigate(['/cliente/dashboard']);
            },
            error: ()=>{
                alert('Credenciales incorrectas');
            }
        });
    }
}
import { Component } from '@angular/core';
import { Header } from '../../../../SHARED/header/header';
import { FormsModule } from '@angular/forms';
import {NgClass} from '@angular/common';
import {Footer} from '../../../../SHARED/footer/footer';
import {LoginDTO, RegistroDTO, UsuarioService} from '../../../../SERVICES/usuario-service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    Header,
    FormsModule,
    NgClass,

  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  isRegistroVisible: boolean = false;

  mostrarRegistro() {
    this.isRegistroVisible = true; this.limpiarMensajes();
  }

  mostrarLogin() {
    this.isRegistroVisible = false; this.limpiarMensajes();
  }

  limpiarMensajes() {this.mensajeAlerta = ''}

  loginData: LoginDTO = {username: '', password: ''};
  registroData: RegistroDTO = {username: '', password: '', nombre: '', apellidos: ''};

  emailRecuperacion: string = '';
  isModalOpen: boolean = false;
  mensajeRecuperacion: string = '';

  mensajeAlerta: string = '';
  tipoAlerta: 'error' | 'success' = 'error';

  constructor(private usuarioService: UsuarioService, private router: Router) {}

  // Login
  onLogin() {
    this.usuarioService.login(this.loginData).subscribe({
      next: (token) => {
        localStorage.setItem('token', token);
        this.router.navigate(['/']);
      },
      error: (error) => {
        if (error.error && error.error.include('verificar')) {
          this.mensajeAlerta = 'Debes verificar tu correo antes de iniciar sesión'
        } else {
          this.mensajeAlerta = 'Usuario o contraseña incorrectos'
        }
        this.tipoAlerta = 'error';
      }
    });
  }

  // Registro
  onRegistro() {
    this.usuarioService.registrar(this.registroData).subscribe({
      next: () => {
        this.tipoAlerta = 'success';
        this.mensajeAlerta = 'Registro exitoso. ¡Por favor, registra tu correo para verificar la cuenta!';
        this.registroData = {username: '', password: '', nombre: '', apellidos: ''};
      },
      error: (error) => {
        this.tipoAlerta = 'error';
        this.mensajeAlerta = 'Error al registrarse. El usuario podría ya existir.';
      }
    });
  }

  // Recuperar contraseña
  abrirModal() {this.isModalOpen = true; this.mensajeRecuperacion = '';}
  cerrarModal() {this.isModalOpen = false;}

  enviarCorreoRecuperacion() {
    if (!this.emailRecuperacion) return;

    this.usuarioService.solicitarRecuperacion(this.emailRecuperacion).subscribe({
      next: (res) => {
        this.mensajeRecuperacion = res;
      },
      error: (error) => {
        this.mensajeRecuperacion = 'El correo introducido no está registrado.';
      }
    });
  }
}

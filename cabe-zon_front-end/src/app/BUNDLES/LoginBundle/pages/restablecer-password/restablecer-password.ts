import {Component, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {UsuarioService} from '../../../../SERVICES/usuario-service';

@Component({
  selector: 'app-restablecer-password',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './restablecer-password.html',
  styleUrl: './restablecer-password.css',
})
export class RestablecerPassword implements OnInit {
  token: string = '';
  password1: string = '';
  password2: string = '';
  errorMsg: string = '';
  tokenValido: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private usuarioService: UsuarioService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
      this.tokenValido = !!this.token;
    });
  }

  cambiarPassword() {
    if (this.password1 !== this.password2) {
      this.errorMsg = 'Las contraseñas no coinciden';
      return;
    }

    // Llamada al backend
    this.usuarioService.cambiarPassword({ token: this.token, nuevaPassword: this.password1 }).subscribe({
      next: (res) => {
        alert('Contraseña actualizada correctamente. Ahora puedes iniciar sesión.');
        this.router.navigate(['/login']);
      },
      error: (error) => {
        this.errorMsg = 'Error al cambiar la contraseña: ' + error.error || 'Token inválido';
      }
    });
  }
}

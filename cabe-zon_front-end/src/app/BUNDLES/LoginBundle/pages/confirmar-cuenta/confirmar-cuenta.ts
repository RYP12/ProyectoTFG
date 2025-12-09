import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterModule} from '@angular/router';
import {UsuarioService} from '../../../../SERVICES/usuario-service';

@Component({
  selector: 'app-confirmar-cuenta',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './confirmar-cuenta.html',
  styleUrl: './confirmar-cuenta.css',
})
export class ConfirmarCuenta implements OnInit {
  loading = true;
  exito = false;
  mensaje = '';

  constructor(
    private route: ActivatedRoute,
    private usuarioService: UsuarioService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      const token = params['token'];
      if (token) {
        this.verificar(token);
      } else {
        this.loading = false;
        this.mensaje = 'No se encontró el token de verificación.';
      }
    });
  }

  verificar(token: string) {
    this.usuarioService.confirmarCuenta(token).subscribe({
      next: (res) => {
        this.loading = false;
        this.exito = true;
        this.mensaje = res;
      },
      error: (error) => {
        this.loading = false;
        this.exito = false;
        this.mensaje = error.error || 'Token inválido o expirado';
      }
    });
  }

  irLogin() {
    this.router.navigate(['/login']);
  }
}

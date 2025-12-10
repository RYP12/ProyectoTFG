import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { UsuarioService } from '../../SERVICES/usuario-service';

export const adminGuard: CanActivateFn = (route, state) => {
  const usuarioService = inject(UsuarioService);
  const router = inject(Router);

  const rol = usuarioService.obtenerRol();

  if (rol === 'ADMINISTRADOR') {
    return true;
  } else {
    router.navigate(['/']);
    return false;
  }
};

import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Pedido} from './pedido-service';

export enum Rol {
  CLIENTE = 'CLIENTE',
  ADMINISTRADOR = 'ADMINISTRADOR',
}

export interface Usuario {
  correo?: string;
  rol ?: Rol
}

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {

}

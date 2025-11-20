import { Injectable } from '@angular/core';
import {Cliente} from './cliente-service';
import {Estado} from './estado-service';

export interface Pedido {
  id?: number;
  fechaEstimada?: Date;
  fechaEntrega?: Date;
  estado?: Estado;
  fecha?: Date;
  precioTotal?: number;
  cliente?: Cliente;
}

@Injectable({
  providedIn: 'root',
})
export class PedidoService {

}

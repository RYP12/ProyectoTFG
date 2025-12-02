import { Component } from '@angular/core';
import {Producto, ProductoService} from '../../../../SERVICES/productoService';
import {Pedido, PedidoService} from '../../../../SERVICES/pedido-service';
import {ClienteService} from '../../../../SERVICES/cliente-service';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';

@Component({
  selector: 'app-edicion-limitada',
  imports: [
    Header,
    Footer
  ],
  templateUrl: './edicion-limitada.html',
  styleUrl: './edicion-limitada.css',
})
export class EdicionLimitada {

}

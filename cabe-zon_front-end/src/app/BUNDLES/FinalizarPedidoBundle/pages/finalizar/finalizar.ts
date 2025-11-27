import {Component, OnInit} from '@angular/core';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';
import {Producto} from '../../../../SERVICES/productoService';
import {CarritoService} from '../../../../SERVICES/carrito-service';
import {Pedido, PedidoService} from '../../../../SERVICES/pedido-service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-finalizar',
  imports: [
    Header,
    Footer
  ],
  templateUrl: './finalizar.html',
  styleUrl: './finalizar.css',
})
export class Finalizar  {

}

import {Component, OnInit} from '@angular/core';
import {RouterLink} from '@angular/router';
import { Pedido, PedidoService } from '../../../../SERVICES/pedido-service';

@Component({
  selector: 'app-owner-control-pedidos',
  imports: [
    RouterLink
  ],
  templateUrl: './owner-control-pedidos.html',
  styleUrl: './owner-control-pedidos.css',
})
export class OwnerControlPedidos implements OnInit {
  listaPedidos: Pedido[] = [];

    constructor(private pedidoService: PedidoService,) {}

    ngOnInit() {
      this.pedidoService.obtenerPedidos().subscribe({
        next: (datos) => {
          this.listaPedidos = datos;
        },
        error: (err) => {
          console.log(err);
        }
      })
    }
}

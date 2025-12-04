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

  // La lista que se ve en pantalla (se vacía y llena al buscar)
  listaPedidosFiltrada: Pedido[] = [];

  constructor(private pedidoService: PedidoService) {}

  ngOnInit() {
    this.pedidoService.obtenerPedidos().subscribe({
      next: (datos) => {
        this.listaPedidos = datos;

        // Al iniciar, la filtrada es igual a la completa
        this.listaPedidosFiltrada = datos;

        console.log(this.listaPedidos);
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  // Función del Buscador
  filtrar(event: any) {
    const texto = event.target.value.toLowerCase().trim();

    // Si borran, mostramos todo
    if (texto === '') {
      this.listaPedidosFiltrada = this.listaPedidos;
      return;
    }

    this.listaPedidosFiltrada = this.listaPedidos.filter(pedido => {
      // Buscamos por ID
      const id = pedido.id ? String(pedido.id) : '';

      // Buscamos por Estado (Traducimos lo técnico a lo visual)
      // Esto permite que si buscan "Recibido", encuentre "ENTREGADO"
      let nombreVisualEstado = '';
      const estadoInterno = pedido.estado ? String(pedido.estado) : '';

      if (estadoInterno === 'EN_PREPARACION') nombreVisualEstado = 'pendiente';
      else if (estadoInterno === 'ENVIADO') nombreVisualEstado = 'enviado';
      else if (estadoInterno === 'ENTREGADO') nombreVisualEstado = 'recibido';
      else if (estadoInterno === 'CANCELADO') nombreVisualEstado = 'cancelado';

      // También permitimos buscar por el código interno por si acaso
      const coincideEstado = nombreVisualEstado.includes(texto) || estadoInterno.toLowerCase().includes(texto);
      const coincideId = id.includes(texto);

      return coincideId || coincideEstado;
    });
  }
}

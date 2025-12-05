import {Component, OnInit, signal} from '@angular/core';
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

  pedidos = signal<Pedido[]>([]);
  paginaActual = signal<number>(1);
  totalPaginas = signal<number>(1);

  itemsPorPagina = 5;


  // La lista que se ve en pantalla (se vacía y llena al buscar)
  listaPedidosFiltrada: Pedido[] = [];

  constructor(private pedidoService: PedidoService) {}

  ngOnInit() {
    this.cargarPedidos();
  }

  cargarPedidos(){
    const pagina = this.paginaActual();

    this.pedidoService.obtenerPedidosAdmin(pagina - 1, this.itemsPorPagina).subscribe({
      next: (data: any) => {
        if (Array.isArray(data)) {
          const totalItems = data.length;
          const paginasCalculadas = Math.ceil(totalItems / this.itemsPorPagina);
          this.totalPaginas.set(paginasCalculadas || 1);

          const inicio = (pagina - 1) * this.itemsPorPagina;
          const fin = inicio + this.itemsPorPagina;
          const pedidosRecortados = data.slice(inicio, fin);

          this.pedidos.set(pedidosRecortados);
        }
        else if (data.content) {
          this.pedidos.set(data.content);
          this.totalPaginas.set(data.totalPages);
        }
      },
      error: (error) => console.log('Error al cargar productos Admin:', error)
    });
  }

  // Función del Buscador
  filtrar(event: any) {
    const texto = event.target.value.toLowerCase().trim();

    // Si borran, mostramos todo
    if (texto === '') {
      this.listaPedidosFiltrada = this.pedidos();
      return;
    }

    this.listaPedidosFiltrada = this.pedidos().filter(pedido => {
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

  paginaAnterior(){
    if (this.paginaActual() > 1) {
      this.paginaActual.set(this.paginaActual() - 1);
      this.cargarPedidos();
    }
  }

  paginaPosterior() {
    if (this.paginaActual() < this.totalPaginas()) {
      this.paginaActual.set(this.paginaActual() + 1);
      this.cargarPedidos();
    }
  }
}

import {Component, inject, OnInit, signal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {Cliente, ClienteService} from '../../../../SERVICES/cliente-service';
import {Pedido, PedidoService} from '../../../../SERVICES/pedido-service';

@Component({
  selector: 'app-owner-control-cliente-form',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './owner-control-cliente-form.html',
  styleUrl: './owner-control-cliente-form.css',
})
export class OwnerControlClienteForm implements OnInit {
  private clienteService = inject(ClienteService);
  private pedidoService = inject(PedidoService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  cliente = signal<Cliente | null>(null);
  pedidos = signal<Pedido[]>([]);
  clienteId = signal<number | null>(null);
  modoDetalle = signal<boolean>(false);

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');

      if (idParam) {
        // Modo Detalle/Edición
        const id = parseInt(idParam, 10);
        this.clienteId.set(id);
        this.modoDetalle.set(true);
        this.cargarCliente(id);
        this.cargarPedidosCliente(id);
      } else {
        // Modo Agregar (ID no presente en la URL)
        this.modoDetalle.set(false);
        // Aquí podrías iniciar un formulario vacío si fuera necesario
      }
    });
  }

  cargarCliente(id: number) {
    this.clienteService.obtenerClientesPorID(id).subscribe({
      next: (data) => {
        this.cliente.set(data);
        // Si los pedidos vinieran anidados en el objeto cliente:
        // this.pedidos.set(data.pedidos || []);
      },
      error: (err) => {
        console.error('Error al cargar el cliente:', err);
        this.router.navigate(['/admin/clientes']);
      }
    });
  }

  cargarPedidosCliente(clienteId: number) {
    this.pedidoService.obtenerPedidosPorClienteId(clienteId).subscribe({
      next: (data) => this.pedidos.set(data),
      error: (err) => console.error('Error al cargar pedidos del cliente:', err)
    });
  }

}

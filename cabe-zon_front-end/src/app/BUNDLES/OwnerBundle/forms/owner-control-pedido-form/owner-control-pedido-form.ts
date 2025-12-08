import {Component, inject, OnInit, signal} from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {ActivatedRoute} from '@angular/router';
import {Pedido, PedidoService, ProductoPedido} from '../../../../SERVICES/pedido-service';
import {CurrencyPipe, DatePipe} from '@angular/common';

@Component({
  selector: 'app-owner-control-pedido-form',
  imports: [MatCardModule, MatButtonModule, CurrencyPipe, DatePipe],
  templateUrl: './owner-control-pedido-form.html',
  styleUrl: './owner-control-pedido-form.css',
})
export class OwnerControlPedidoForm implements OnInit {

  private route = inject(ActivatedRoute);
  private pedidoService = inject(PedidoService);

  // Señal para almacenar el pedido cargado
  pedido = signal<Pedido | undefined>(undefined);

  // URL del placeholder (ajusta la ruta)
  private readonly PLACEHOLDER_IMG_URL: string = '/ASSETS/IMAGES/placeholder.png';

  ngOnInit() {
    // 1. Obtener el ID del parámetro de la URL
    this.route.paramMap.subscribe(params => {
      const pedidoIdString = params.get('id');
      if (pedidoIdString) {
        const pedidoId = +pedidoIdString; // El '+' convierte el string a number

        // 2. Cargar el pedido
        this.cargarDetallePedido(pedidoId);
      }
    });
  }

  cargarDetallePedido(id: number): void {
    this.pedidoService.obtenerProductoPorID(id).subscribe({
      next: (data: Pedido) => {
        this.pedido.set(data);
      },
      error: (err) => {
        console.error('Error al cargar el detalle del pedido:', err);
        // Manejar error (e.g., mostrar mensaje)
      }
    });
  }

  obtenerEstadoVisual(estado: string | undefined): { nombre: string, color: string } {
    if (!estado) return { nombre: 'Desconocido', color: '#999' };

    switch (estado) {
      case 'EN_PREPARACION': return { nombre: 'Pendiente', color: 'gold' };
      case 'ENVIADO': return { nombre: 'Enviado', color: '#4C97FF' };
      case 'ENTREGADO': return { nombre: 'Recibido', color: '#00DE16' };
      case 'CANCELADO': return { nombre: 'Cancelado', color: '#FF0004' };
      default: return { nombre: 'Desconocido', color: '#999' };
    }
  }

  obtenerImagenUrl(productoPedido: ProductoPedido): string {
    const imagenes = productoPedido.producto?.imagenes;

    if (imagenes && imagenes.length > 0) {
      if (imagenes[0] && imagenes[0].url) {
        return imagenes[0].url;
      }
    }
    return this.PLACEHOLDER_IMG_URL;
  }
}

import {Component, inject, OnInit, signal} from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {ActivatedRoute, Router} from '@angular/router';
import {Estado, Pedido, PedidoService, ProductoPedido} from '../../../../SERVICES/pedido-service';
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
  private router = inject(Router);

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

  cambiarEstado(nuevoEstado: Estado): void {
    const pedidoActual = this.pedido();
    if (!pedidoActual || !pedidoActual.id) {
      console.error('No se puede cambiar el estado: Pedido no cargado o sin ID.');
      return;
    }

    // 🚨 LOG 1: Confirma que la función se ejecuta con los datos correctos
    console.log(`[DEBUG] Intentando cambiar el pedido ${pedidoActual.id} a estado: ${nuevoEstado}`);


    // 🚨 LOG 2: Verifica que el método del servicio se está llamando
    this.pedidoService.actualizarEstadoPedido(pedidoActual.id, nuevoEstado.toString()).subscribe({
      next: () => {
        // La actualización fue exitosa
        console.log(`[DEBUG] API RESPONSE SUCCESS. Redirigiendo...`);

        // Redirigir al usuario a la lista de pedidos
        this.router.navigate(['/admin/pedidos']);
      },
      error: (err) => {
        // 🚨 LOG 3: Si hay un error, lo registramos.
        console.error('[ERROR] Error al actualizar el estado:', err);
      },
      // 🚨 LOG 4: Finaliza el observable (útil para ver si el observable se cierra)
      complete: () => {
        console.log('[DEBUG] Petición HTTP completada.');
      }
    });
  }

  procesarPedido(): void {
    this.cambiarEstado(Estado.ENVIADO); // De EN_PREPARACION a ENVIADO
  }

  marcarComoEntregado(): void {
    this.cambiarEstado(Estado.ENTREGADO); // De ENVIADO a ENTREGADO
  }

  cancelarPedido(): void {
    if (confirm('¿Estás seguro que deseas cancelar este pedido?')) {
      this.cambiarEstado(Estado.CANCELADO);
    }
  }

}

import {Component, inject, OnInit} from '@angular/core';
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
  private carritoService = inject(CarritoService);
  private router = inject(Router);

  // 1. Exponemos las señales del servicio (Read-only)
  items = this.carritoService.productosCarrito$;

  // Lógica para el botón de "Pagar" o "Confirmar"
  confirmarPedido() {
    // Aquí iría la lógica para enviar al Backend (PedidoService)
    console.log('Procesando pedido con:', this.items);
    alert('Funcionalidad de pago en construcción');
  }

  volverAlCatalogo() {
    this.router.navigate(['/catalogo']);
  }

  protected decrementarCantidad(funko: any) {
    
  }

  protected incrementarCantidad(funko: any) {
    
  }

  protected eliminarDelPedido(funko: any) {
    
  }
}

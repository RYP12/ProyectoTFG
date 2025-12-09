import {Component, inject, OnInit} from '@angular/core';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';
import {Producto} from '../../../../SERVICES/productoService';
import {CarritoService} from '../../../../SERVICES/carrito-service';
import {Router} from '@angular/router';
import {AsyncPipe, CurrencyPipe} from '@angular/common';

@Component({
  selector: 'app-finalizar',
  imports: [
    Header,
    Footer,
    AsyncPipe,
    CurrencyPipe
  ],
  templateUrl: './finalizar.html',
  styleUrl: './finalizar.css',
})
export class Finalizar {
  private carritoService = inject(CarritoService);
  private router = inject(Router);

  productosPedido$ = this.carritoService.productosCarrito$;

  subtotal: number = 0;
  envio: number = 3;
  descuento: number = 0;
  total: number = 0;

  private readonly PLACEHOLDER_IMG_URL: string = '/ASSETS/IMAGES/placeholder.png';

  constructor() {
    // Nos suscribimos al observable del carrito para calcular el resumen
    this.productosPedido$.subscribe(productos => {
      this.actualizarResumen(productos);
    });
  }

  // Lógica del Carrito (Reutilizando el CarritoService) ---

  protected decrementarCantidad(funko: Producto) {
    this.carritoService.disminuirCantidadProducto(funko);
  }

  protected incrementarCantidad(funko: Producto) {
    this.carritoService.aumentarCantidadProducto(funko);
  }

  protected eliminarDelPedido(funko: Producto) {
    this.carritoService.eliminarProducto(funko);
  }

  protected obtenerImagenUrl(funko: Producto): string {
    // Usamos el índice 0 para la imagen principal
    if (funko.imagenes && funko.imagenes.length > 0 && funko.imagenes[0].url) {
      return funko.imagenes[0].url;
    }
    return this.PLACEHOLDER_IMG_URL;
  }

  // Lógica del Resumen y Pago ---

  private actualizarResumen(productos: Producto[]) {
    // Calcula el subtotal basándose en el precio y la cantidad de cada producto
    this.subtotal = productos.reduce((acc, p) => acc + (p.precio! * (p.cantidad || 1)), 0);

    // Por simplicidad, el descuento es 0 aquí. Podrías añadir lógica de cupones.
    this.descuento = 0;

    this.total = this.subtotal + this.envio - this.descuento;
  }

  confirmarPedido() {
    if (this.total > 0) {
      console.log('Procesando pedido. Total:', this.total);
      alert('Funcionalidad de pago en construcción');
    } else {
      alert('No hay productos en el pedido.');
    }
  }

}

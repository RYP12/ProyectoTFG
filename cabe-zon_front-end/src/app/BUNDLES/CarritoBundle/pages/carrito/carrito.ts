// src/app/BUNDLES/CarritoBundle/pages/carrito/carrito.ts
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Producto } from '../../../../SERVICES/productoService';
import { CommonModule } from '@angular/common';
import {MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {CarritoService} from '../../../../SERVICES/carrito-service';
import {RouterLink} from '@angular/router'; // Necesario para usar pipes como 'async' o directivas como @for

@Component({
  selector: 'app-carrito',
  standalone: true,
  imports: [CommonModule,
    MatDialogModule, RouterLink], // Importante importar CommonModule
  templateUrl: './carrito.html',
  styleUrl: './carrito.css',
})
export class Carrito implements OnInit, OnDestroy {

  carrito: Producto[] = [];
  private carritoSubscription: Subscription | undefined;

  // URL del placeholder si no hay imagen (ajusta la ruta si es necesario)
  private readonly PLACEHOLDER_IMG_URL: string = '/ASSETS/IMAGES/placeholder.png';

  constructor(private carritoService: CarritoService) {}

  ngOnInit() {
    // Nos suscribimos al observable del servicio.
    // Cada vez que alguien haga .next() en el servicio, esto se ejecutará.
    this.carritoSubscription = this.carritoService.productosCarrito$.subscribe(
      (productos) => {
        this.carrito = productos;
      }
    );
  }

  // Best Practice: Siempre desuscribirse para evitar memory leaks
  ngOnDestroy() {
    if (this.carritoSubscription) {
      this.carritoSubscription.unsubscribe();
    }
  }

  protected sumarCantidad(producto: Producto) {
    this.carritoService.aumentarCantidadProducto(producto);
  }

  protected restarCantidad(producto: Producto) {
    this.carritoService.disminuirCantidadProducto(producto);
  }

  protected eliminarDelCarrito(producto: Producto) {
    this.carritoService.eliminarProducto(producto);
  }

  protected obtenerImagenUrl(funko: Producto): string {
    if (funko.imagenes && funko.imagenes.length > 0 && funko.imagenes[0].url) {
      return funko.imagenes[0].url;
    }
    return this.PLACEHOLDER_IMG_URL;
  }

}

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

  protected restarCantidad(producto: Producto) {

  }

  protected sumarCantidad(producto: Producto) {

  }

  protected eliminarDelCarrito(producto: Producto) {

  }
}

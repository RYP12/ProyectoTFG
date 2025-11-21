import {Component, OnInit} from '@angular/core';
import {ProductoService, Producto} from '../../../../SERVICES/productoService';
import {CarritoService} from '../../../../SERVICES/carrito-service';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-catalogo',
  imports: [
    RouterLink
  ],
  templateUrl: './catalogo.html',
  styleUrl: './catalogo.css',
})
export class Catalogo implements OnInit {
  listaProductos: Producto[] = [];

  constructor(private productoService: ProductoService,
              private carritoService: CarritoService,) {}

  ngOnInit() {
    this.productoService.obtenerProductos().subscribe({
      next: (datos) => {
        this.listaProductos = datos;
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  agregarAlCarrito(funko: Producto) {
    this.carritoService.agregarProducto(funko);
  }
}

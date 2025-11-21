import {Component, OnInit} from '@angular/core';
import {ProductoService, Producto} from '../../../../SERVICES/productoService';

@Component({
  selector: 'app-catalogo',
  imports: [],
  templateUrl: './catalogo.html',
  styleUrl: './catalogo.css',
})
export class Catalogo implements OnInit {
  listaProductos: Producto[] = [];

  constructor(private productoService: ProductoService) { }

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

  }
}

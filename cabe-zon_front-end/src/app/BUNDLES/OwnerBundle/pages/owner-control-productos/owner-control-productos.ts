import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';
import { Producto, ProductoService } from '../../../../SERVICES/productoService';

@Component({
  selector: 'app-owner-control-productos',
  imports: [
    RouterLink
  ],
  templateUrl: './owner-control-productos.html',
  styleUrl: './owner-control-productos.css',
})
export class OwnerControlProductos {
  listaProductos: Producto[] = [];
  
    constructor(private productoService: ProductoService) {}
  
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
}

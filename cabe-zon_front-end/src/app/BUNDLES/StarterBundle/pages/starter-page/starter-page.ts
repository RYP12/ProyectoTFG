import {Component, inject, OnInit} from '@angular/core';
import {Header} from '../../../../SHARED/header/header';
import {Footer} from '../../../../SHARED/footer/footer';
import {Producto, ProductoService} from '../../../../SERVICES/productoService';
import {CurrencyPipe} from '@angular/common';


@Component({
  selector: 'app-starter-page',
  imports: [
    Header,
    Footer,
    CurrencyPipe
  ],
  templateUrl: './starter-page.html',
  styleUrl: './starter-page.css',
})
export class StarterPage implements OnInit {

// Aqui lo que hacemos es crear una copia del ProductoService para usar aqui una funcion
  private productoService = inject(ProductoService);


  topProductos: Producto[] = [];


  ngOnInit(): void {
    // Llamamos a la peticion preparada en el productoService gracias a subscribe
    this.productoService.obtenerTopVentas().subscribe({
      // Si todo esta bien data coge los datos json de los productos
      next: (data) => {
        this.topProductos = data;
      },
      error: (err) => console.error(err)
    });
  }
}
